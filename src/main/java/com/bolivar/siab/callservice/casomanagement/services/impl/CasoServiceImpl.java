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
    private final CasoMapper casoMapper;

    @Override
    @Transactional
    public CasoResponseDTO createCase(CasoRequestDTO request) {
        log.info("Creating case for city: {}, risk: {}", request.getLocgeCodigo(), request.getRiesgoCodigo());

        // PRE-INSERT validation: mandatory fields
        validateMandatoryFields(request);

        // F_VALIDA_FUNCIONARIO: prevent Bolivar employee self-cases
        String esFuncionario = storedProcedureRepository.validaFuncionario(
                request.getUsuNumeroDocumento(), request.getUsuTipoDocumento());
        if ("S".equals(esFuncionario)) {
            throw new BusinessException("FUNCIONARIO_BOLIVAR",
                    "No se permite crear casos para funcionarios de Bolívar");
        }

        // Generate NUMERO_LLAMADA and NUMERO_SINI via F_CONSECUTIVO_SIAB
        Long numeroLlamada = storedProcedureRepository.getConsecutivoSiab("NUMERO_LLAMADA");
        String numeroSini = String.valueOf(storedProcedureRepository.getConsecutivoSiab("NUMERO_SINI"));

        // Build entity
        LlamadaEntity entity = casoMapper.toEntity(request);
        entity.setNumero(numeroLlamada);
        entity.setNumeroSiniestro(numeroSini);
        entity.setFechaLlamada(LocalDateTime.now());
        entity.setHoraLlamada(String.format("%02d:%02d",
                LocalDateTime.now().getHour(), LocalDateTime.now().getMinute()));
        entity.setEstadoLlamada("AB"); // Open

        // Save to LLAMADAS
        LlamadaEntity saved = llamadaRepository.save(entity);
        log.info("Case created with numero: {}, sini: {}", saved.getNumero(), saved.getNumeroSiniestro());

        return enrichResponse(saved);
    }

    @Override
    @Transactional
    public CasoResponseDTO updateCase(Long numero, CasoRequestDTO request) {
        LlamadaEntity entity = llamadaRepository.findById(numero)
                .orElseThrow(() -> new BusinessException("CASO_NO_ENCONTRADO",
                        "Caso no encontrado: " + numero));
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
        if (criteria.getUsuNumeroDocumento() != null) {
            return llamadaRepository.findByUsuarioDocumento(
                    criteria.getUsuNumeroDocumento(), pageable).map(this::enrichResponse);
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
                .ramoAnterior(original.getRamoCodigo())
                .productoAnterior(original.getProductoCodigo())
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
        original.setReclasificaRamoCodigo(request.getRamoNuevo());
        original.setReclasificaProductoCodigo(request.getProductoNuevo());
        original.setReclasificaCausaCodigo(request.getCausaNueva());
        original.setCodRazonReclasifica(request.getCodRazon());
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
        if (request.getUsuNumeroDocumento() == null || request.getUsuNumeroDocumento().isBlank()) {
            throw new BusinessException("CAMPO_REQUERIDO", "Documento del usuario (USU_NUMERO_DOCUMENTO) es requerido");
        }
    }

    /**
     * POST-QUERY enrichment: populate descriptive fields via PKG_DESCRIPTORES.
     */
    private CasoResponseDTO enrichResponse(LlamadaEntity entity) {
        CasoResponseDTO response = casoMapper.toResponse(entity);

        try {
            if (entity.getRamoCodigo() != null) {
                response.setDspRamo(storedProcedureRepository.getDescriptorRamo(entity.getRamoCodigo()));
            }
            if (entity.getProductoCodigo() != null) {
                response.setDspProducto(storedProcedureRepository.getDescriptorProducto(entity.getProductoCodigo()));
            }
            if (entity.getCausaCodigo() != null) {
                response.setDspEstadoLlamada(entity.getEstadoLlamada());
            }
            // Exception count
            long exceptionCount = llamadaExcepcionRepository.countByNumeroLlamada(entity.getNumero());
            response.setExcepciones((int) exceptionCount);
        } catch (Exception e) {
            log.warn("Error enriching case response for numero {}: {}", entity.getNumero(), e.getMessage());
        }

        return response;
    }
}
