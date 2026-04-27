package com.bolivar.siab.callservice.casomanagement.services.impl;

import com.bolivar.siab.callservice.casomanagement.dto.*;
import com.bolivar.siab.callservice.casomanagement.mapper.CasoMapper;
import com.bolivar.siab.callservice.casomanagement.models.LlamadaEntity;
import com.bolivar.siab.callservice.casomanagement.models.LogCambioCausaEntity;
import com.bolivar.siab.callservice.casomanagement.repository.LlamadaExcepcionRepository;
import com.bolivar.siab.callservice.casomanagement.repository.LlamadaRepository;
import com.bolivar.siab.callservice.casomanagement.repository.LogCambioCausaRepository;
import com.bolivar.siab.callservice.casomanagement.services.CasoService;
import com.bolivar.siab.callservice.caracteristicas.models.CaracteristicaCausaLlamadaEntity;
import com.bolivar.siab.callservice.caracteristicas.repository.CaracteristicaCausaLlamadaRepository;
import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.geographic.repository.LocalizacionGeograficaRepository;
import com.bolivar.siab.callservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of CasoService with PRE-INSERT validation, F_VALIDA_FUNCIONARIO,
 * F_CONSECUTIVO_SIAB, duplicate validation, cause reclassification, and POST-QUERY enrichment.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CasoServiceImpl implements CasoService {

    private final LlamadaRepository llamadaRepository;
    private final LlamadaExcepcionRepository llamadaExcepcionRepository;
    private final LogCambioCausaRepository logCambioCausaRepository;
    private final CaracteristicaCausaLlamadaRepository caracteristicaRepository;
    private final StoredProcedureRepository storedProcedureRepository;
    private final LocalizacionGeograficaRepository localizacionRepository;
    private final CasoMapper casoMapper;

    @Override
    @Transactional
    public CasoResponseDTO createCase(CasoRequestDTO request) {
        log.info("Creating case for city: {}, risk: {}", request.getLocgeCodigo(), request.getRiesgoCodigo());

        // PRE-INSERT validation: mandatory fields
        validateMandatoryFields(request);

        // Generate NUMERO_LLAMADA and NUMERO_SINI via F_CONSECUTIVO_SIAB
        Long numeroLlamada = storedProcedureRepository.getConsecutivoSiab("NUMERO_LLAMADA");
        Long numeroSini = storedProcedureRepository.getConsecutivoSiab("NUMERO_SINI");

        // Build entity
        LlamadaEntity entity = casoMapper.toEntity(request);
        entity.setNumero(numeroLlamada);
        entity.setNumeroSiniestro(numeroSini);
        entity.setFechaLlamada(LocalDateTime.now());
        entity.setFechaHoraLlamada(LocalDateTime.now());
        // HORA_LLAMADA is NUMBER (minutes since midnight) in Oracle
        entity.setHoraLlamada(LocalDateTime.now().getHour() * 60 + LocalDateTime.now().getMinute());
        entity.setEstadoLlamada("A"); // Abierto
        entity.setOperador("ANGULAR");
        entity.setPlacaRiesgo(request.getPlacaRiesgo() != null ? request.getPlacaRiesgo() : request.getRiesgoCodigo());

        // OBSERVACIONES_LAR: if null, set "INICIO DE CASO" (from PRE-INSERT)
        if (entity.getObservacionesLar() == null || entity.getObservacionesLar().isEmpty()) {
            entity.setObservacionesLar("INICIO DE CASO");
        }

        // Save to LLAMADAS
        LlamadaEntity saved = llamadaRepository.save(entity);
        log.info("Case created with numero: {}, sini: {}", saved.getNumero(), saved.getNumeroSiniestro());

        // POST-INSERT: Insert observations via PKG_INSERTAR (non-critical, don't fail the save)
        if (request.getObservacionesLar() != null && !request.getObservacionesLar().isEmpty()
                && !"INICIO DE CASO".equals(request.getObservacionesLar())) {
            try {
                String obsFormatted = "&ANGULAR|" + java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(LocalDateTime.now()) + "|" + request.getObservacionesLar().trim();
                storedProcedureRepository.insertarObservacionesCaso(saved.getNumero(), saved.getNumeroSiniestro(), obsFormatted);
            } catch (Exception e) { log.warn("PKG_INSERTAR error: {}", e.getMessage()); }
        }

        // Return basic response without enrichment to avoid transaction issues
        CasoResponseDTO response = CasoResponseDTO.builder()
                .numero(saved.getNumero())
                .numeroSiniestro(saved.getNumeroSiniestro())
                .contNumeroContrato(saved.getContNumeroContrato())
                .ramoCodigo(saved.getRamoCodigo())
                .productoCodigo(saved.getProductoCodigo())
                .causaCodigo(saved.getCausaCodigo())
                .locgeCodigo(saved.getLocgeCodigo())
                .direccion(saved.getDireccion())
                .estadoLlamada(saved.getEstadoLlamada())
                .fechaLlamada(saved.getFechaLlamada())
                .horaLlamada(saved.getHoraLlamada())
                .build();
        return response;
    }

    @Override
    @Transactional
    public CasoResponseDTO updateCase(Long numero, CasoRequestDTO request) {
        LlamadaEntity entity = llamadaRepository.findById(numero)
                .orElseThrow(() -> new BusinessException("CASO_NO_ENCONTRADO",
                        "Caso no encontrado: " + numero));

        // Validate state allows modification
        String estado = entity.getEstadoLlamada();
        if ("C".equals(estado) || "X".equals(estado)) {
            throw new BusinessException("CASO_ESTADO_INVALIDO",
                    "El caso está " + ("C".equals(estado) ? "cerrado" : "anulado") + " y no puede modificarse");
        }

        casoMapper.updateEntity(request, entity);
        LlamadaEntity saved = llamadaRepository.save(entity);
        return enrichResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CasoResponseDTO getCaseDetails(Long numero) {
        LlamadaEntity entity = llamadaRepository.findById(numero)
                .orElseThrow(() -> new BusinessException("CASO_NO_ENCONTRADO",
                        "Caso no encontrado: " + numero));
        return enrichResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CasoResponseDTO> searchCases(CasoBusquedaDTO criteria, Pageable pageable) {
        if (criteria.getContNumero() != null) {
            return llamadaRepository.findByContNumeroContratoContainingIgnoreCase(
                    criteria.getContNumero(), pageable).map(this::enrichResponse);
        }
        return llamadaRepository.findAll(pageable).map(this::enrichResponse);
    }

    @Override
    @Transactional
    public ReclasificacionResponseDTO reclassifyCase(Long numero, ReclasificacionRequestDTO request) {
        log.info("Reclassifying case: {} to ramo:{}, producto:{}, causa:{}",
                numero, request.getRamoNuevo(), request.getProductoNuevo(), request.getCausaNueva());

        LlamadaEntity original = llamadaRepository.findById(numero)
                .orElseThrow(() -> new BusinessException("CASO_NO_ENCONTRADO",
                        "Caso no encontrado: " + numero));

        // Generate new case number
        Long nuevoNumero = storedProcedureRepository.getConsecutivoSiab("NUMERO_LLAMADA");

        // Log the change
        LogCambioCausaEntity logCambio = LogCambioCausaEntity.builder()
                .numeroLlamada(numero)
                .ramoAnterior(original.getRamoCodigo() != null ? Integer.parseInt(original.getRamoCodigo()) : null)
                .productoAnterior(original.getProductoCodigo() != null ? Integer.parseInt(original.getProductoCodigo()) : null)
                .causaAnterior(original.getCausaCodigo())
                .ramoNuevo(request.getRamoNuevo())
                .productoNuevo(request.getProductoNuevo())
                .causaNueva(request.getCausaNueva())
                .codRazon(request.getCodRazon())
                .descripcionRazon(request.getDescripcionRazon())
                .fechaCambio(LocalDateTime.now())
                .build();
        logCambioCausaRepository.save(logCambio);

        // Copy characteristics excluding fields 386, 388, 527, 515
        List<CaracteristicaCausaLlamadaEntity> originalChars =
                caracteristicaRepository.findByLlamadaNumeroAndCodigoCampoNotIn(
                        numero, List.of(386, 388, 527, 515));
        for (CaracteristicaCausaLlamadaEntity charEntity : originalChars) {
            CaracteristicaCausaLlamadaEntity newChar = CaracteristicaCausaLlamadaEntity.builder()
                    .llamadaNumero(nuevoNumero)
                    .codigoCampo(charEntity.getCodigoCampo())
                    .ramoCodigo(request.getRamoNuevo())
                    .productoCodigo(request.getProductoNuevo())
                    .causaCodigo(request.getCausaNueva())
                    .valor(charEntity.getValor())
                    .codigoCampoPadre(charEntity.getCodigoCampoPadre())
                    .build();
            caracteristicaRepository.save(newChar);
        }

        // Update reclassification fields on original
        original.setReclasificaRamoCodigo(String.valueOf(request.getRamoNuevo()));
        original.setReclasificaProductoCodigo(String.valueOf(request.getProductoNuevo()));
        original.setReclasificaCausaCodigo(request.getCausaNueva());
        original.setCodRazonReclasifica(request.getCodRazon() != null ? Long.valueOf(request.getCodRazon()) : null);
        llamadaRepository.save(original);

        return ReclasificacionResponseDTO.builder()
                .nuevoNumeroLlamada(nuevoNumero)
                .resultado("Reclasificación exitosa")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateDuplicateCase(Long locgeCodigo, String contNumero, String riesgoCodigo) {
        List<LlamadaEntity> duplicates = llamadaRepository.findDuplicateCases(
                locgeCodigo, contNumero, riesgoCodigo);
        return !duplicates.isEmpty();
    }

    // === PRIVATE HELPER METHODS ===

    private void validateMandatoryFields(CasoRequestDTO request) {
        if (request.getLocgeCodigo() == null) {
            throw new BusinessException("CAMPO_REQUERIDO", "Ciudad (LOCGE_CODIGO) es requerida");
        }
        if (request.getRiesgoCodigo() == null) {
            throw new BusinessException("CAMPO_REQUERIDO", "Código de riesgo (RIESGO_CODIGO) es requerido");
        }
        if (request.getCausaCodigo() == null) {
            throw new BusinessException("CAMPO_REQUERIDO", "Código de causa (CAUSA_CODIGO) es requerido");
        }
        if (request.getDireccion() == null || request.getDireccion().isBlank()) {
            throw new BusinessException("CAMPO_REQUERIDO", "Dirección (DIRECCION) es requerida");
        }
    }

    /**
     * POST-QUERY enrichment: populate descriptive fields via PKG_DESCRIPTORES.
     */
    private CasoResponseDTO enrichResponse(LlamadaEntity entity) {
        CasoResponseDTO response = casoMapper.toResponse(entity);

        try {
            if (entity.getRamoCodigo() != null) {
                response.setDspRamo(storedProcedureRepository.getDescriptorRamo(Integer.parseInt(entity.getRamoCodigo())));
            }
            if (entity.getProductoCodigo() != null) {
                Integer ramo = entity.getRamoCodigo() != null ? Integer.parseInt(entity.getRamoCodigo()) : 0;
                response.setDspProducto(storedProcedureRepository.getDescriptorProducto(ramo, Integer.parseInt(entity.getProductoCodigo())));
            }
            if (entity.getCausaCodigo() != null) {
                Integer ramoInt = entity.getRamoCodigo() != null ? Integer.parseInt(entity.getRamoCodigo()) : 0;
                Integer prodInt = entity.getProductoCodigo() != null ? Integer.parseInt(entity.getProductoCodigo()) : 0;
                response.setDspDescripcion2(storedProcedureRepository.getDescriptorCausa(ramoInt, prodInt, entity.getCausaCodigo()));
            }
            if (entity.getEstadoLlamada() != null) {
                response.setDspEstadoLlamada(storedProcedureRepository.getValDominio("ESTADO_LLAMADA", entity.getEstadoLlamada()));
            }
            // City name lookup from LOCALIZACIONES_GEOGRAFICAS
            if (entity.getLocgeCodigo() != null) {
                response.setTlgCodigo(entity.getTlgCodigo());
                List<Object[]> cityData = localizacionRepository.findCityAndDepartmentByCodigo(entity.getLocgeCodigo());
                if (!cityData.isEmpty()) {
                    Object[] row = cityData.get(0);
                    response.setDspCiudad(row[0] != null ? row[0].toString() : null);
                    response.setDspDpto(row[1] != null ? row[1].toString() : null);
                }
            }
            // Format hora
            if (entity.getHoraLlamada() != null) {
                int h = entity.getHoraLlamada() / 60;
                int m = entity.getHoraLlamada() % 60;
                response.setHoraLlamadaFormatted(String.format("%02d:%02d", h, m));
            }
            // Envio Click/SF
            String envioClick = "S".equals(entity.getMcaEnvioClicksoftware()) ? "SI" : "NO";
            String envioSf = "S".equals(entity.getMcaEnvioSalesforce()) ? "SI" : "NO";
            response.setDspEnviadoCasoClick(envioClick + " / " + envioSf);
            // Exception count
            long exceptionCount = llamadaExcepcionRepository.countByNumeroLlamada(entity.getNumero());
            response.setExcepciones((int) exceptionCount);
        } catch (Exception e) {
            log.warn("Error enriching case response for numero {}: {}", entity.getNumero(), e.getMessage());
        }

        return response;
    }
}
