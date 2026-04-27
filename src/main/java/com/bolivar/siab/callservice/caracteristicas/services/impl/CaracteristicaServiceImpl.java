package com.bolivar.siab.callservice.caracteristicas.services.impl;

import com.bolivar.siab.callservice.caracteristicas.dto.*;
import com.bolivar.siab.callservice.caracteristicas.mapper.CaracteristicaMapper;
import com.bolivar.siab.callservice.caracteristicas.models.*;
import com.bolivar.siab.callservice.caracteristicas.repository.*;
import com.bolivar.siab.callservice.caracteristicas.services.CaracteristicaService;
import com.bolivar.siab.callservice.casomanagement.models.LlamadaEntity;
import com.bolivar.siab.callservice.casomanagement.repository.LlamadaRepository;
import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaracteristicaServiceImpl implements CaracteristicaService {

    private final TipoDatoCaracteristicaRepository tipoDatoRepository;
    private final DatoCaracteristicaCausaRepository datoCaracteristicaRepository;
    private final CaracteristicaCausaLlamadaRepository caracteristicaRepository;
    private final LlamadaRepository llamadaRepository;
    private final StoredProcedureRepository storedProcedureRepository;
    private final CaracteristicaMapper caracteristicaMapper;

    private static final List<Integer> EXCLUDE_FIELDS = List.of(141, 142, 143);
    private static final List<Integer> EXCLUDE_COPY_FIELDS = List.of(386, 388, 527, 515);
    private static final int RAMO_LIBERTADOR_HOGAR = 120;

    @Override
    @Transactional(readOnly = true)
    public CaracteristicaCausaResponseDTO loadCharacteristics(Long llamadaNumero, Integer ramo, Integer producto, Long causa) {
        List<TipoDatoCaracteristicaEntity> tipos = tipoDatoRepository.findActiveCharacteristicsByCause(ramo, producto, causa);
        List<CaracteristicaCausaLlamadaEntity> existingChars = caracteristicaRepository.findByLlamadaNumero(llamadaNumero);
        Map<Integer, String> existingValues = existingChars.stream()
                .collect(Collectors.toMap(CaracteristicaCausaLlamadaEntity::getCodigoCampo,
                        c -> c.getValor() != null ? c.getValor() : "", (a, b) -> a));

        List<CaracteristicaCausaDTO> dtos = tipos.stream().map(t -> CaracteristicaCausaDTO.builder()
                .codigoCampo(t.getCodigoCampo())
                .dspCampo(t.getDescripcion())
                .dspTipoDato(t.getTipoDato())
                .listaValores(t.getListaValores())
                .campoNoModificable(t.getCampoNoModificable())
                .valor(existingValues.getOrDefault(t.getCodigoCampo(), ""))
                .build()).collect(Collectors.toList());

        return CaracteristicaCausaResponseDTO.builder()
                .llamadaNumero(llamadaNumero)
                .caracteristicas(dtos)
                .totalRegistros(dtos.size())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CaracteristicaCausaResponseDTO autoFillCharacteristics(Long llamadaNumero, Integer ramo, Integer producto,
            Long causa, String contNumero, String riesgoCodigo, Long locgeCodigo, String direccion) {
        log.info("Auto-filling characteristics for case: {}, ramo: {}", llamadaNumero, ramo);

        CaracteristicaCausaResponseDTO response = loadCharacteristics(llamadaNumero, ramo, producto, causa);
        List<CaracteristicaCausaDTO> chars = response.getCaracteristicas();

        // Find prior case for copying
        List<LlamadaEntity> priorCases = llamadaRepository.findPreviousCaseByContractAndRamo(
                contNumero, riesgoCodigo, String.valueOf(ramo), String.valueOf(producto), llamadaNumero != null ? llamadaNumero : 0L);
        Long priorCaseNumero = !priorCases.isEmpty() ? priorCases.get(0).getNumero() : null;
        Map<Integer, String> priorValues = new HashMap<>();
        if (priorCaseNumero != null) {
            List<CaracteristicaCausaLlamadaEntity> priorChars = caracteristicaRepository
                    .findByLlamadaNumeroAndCodigoCampoNotIn(priorCaseNumero, EXCLUDE_COPY_FIELDS);
            priorValues = priorChars.stream()
                    .collect(Collectors.toMap(CaracteristicaCausaLlamadaEntity::getCodigoCampo,
                            c -> c.getValor() != null ? c.getValor() : "", (a, b) -> a));
        }

        boolean isInex = contNumero != null && contNumero.startsWith("ASIST");

        for (CaracteristicaCausaDTO c : chars) {
            int campo = c.getCodigoCampo();
            String autoValue = null;

            // Auto-fill by field code
            switch (campo) {
                case 204: if (!isInex) autoValue = contNumero; break;
                case 16: autoValue = riesgoCodigo; break;
                case 57: autoValue = locgeCodigo != null ? locgeCodigo.toString() : null; break;
                case 49: case 41: case 69: autoValue = direccion; break;
                case 244: case 326: autoValue = direccion; break;
                case 46: // Model via F_RIESGOS_CARGUE pos 3
                    if (contNumero != null && riesgoCodigo != null) {
                        try { autoValue = storedProcedureRepository.getRiesgosCargue(contNumero, riesgoCodigo, 3); } catch (Exception e) { log.debug("F_RIESGOS_CARGUE pos 3 error: {}", e.getMessage()); }
                    } break;
                case 52: // Color via F_RIESGOS_CARGUE pos 4
                    if (contNumero != null && riesgoCodigo != null) {
                        try { autoValue = storedProcedureRepository.getRiesgosCargue(contNumero, riesgoCodigo, 4); } catch (Exception e) { log.debug("F_RIESGOS_CARGUE pos 4 error: {}", e.getMessage()); }
                    } break;
                default: break;
            }

            // Ramo 120 (El Libertador/Hogar) special handling
            if (ramo != null && ramo == RAMO_LIBERTADOR_HOGAR && contNumero != null && riesgoCodigo != null) {
                try {
                    switch (campo) {
                        case 314: autoValue = storedProcedureRepository.getValorRiesgo(contNumero, riesgoCodigo, 314); break;
                        case 117: autoValue = storedProcedureRepository.getValorRiesgo(contNumero, riesgoCodigo, 117); break;
                        case 112: autoValue = storedProcedureRepository.getValorRiesgo(contNumero, riesgoCodigo, 112); break;
                        case 316: autoValue = storedProcedureRepository.getValorRiesgo(contNumero, riesgoCodigo, 316); break;
                        case 317: autoValue = storedProcedureRepository.getValorRiesgo(contNumero, riesgoCodigo, 317); break;
                        default: break;
                    }
                } catch (Exception e) { log.debug("F_VALOR_RIESGO error for field {}: {}", campo, e.getMessage()); }
            }

            // Copy from prior case if no auto-value and prior exists
            if (autoValue == null && priorValues.containsKey(campo)) {
                autoValue = priorValues.get(campo);
            }

            if (autoValue != null && c.getValor().isEmpty()) {
                c.setValor(autoValue);
            }
        }

        return response;
    }

    @Override
    @Transactional
    public void saveCharacteristics(CaracteristicaCausaRequestDTO request) {
        for (CaracteristicaCausaDTO dto : request.getCaracteristicas()) {
            CaracteristicaCausaLlamadaEntity entity = CaracteristicaCausaLlamadaEntity.builder()
                    .llamadaNumero(request.getLlamadaNumero())
                    .codigoCampo(dto.getCodigoCampo())
                    .ramoCodigo(request.getRamoCodigo())
                    .productoCodigo(request.getProductoCodigo())
                    .causaCodigo(request.getCausaCodigo())
                    .valor(dto.getValor())
                    .codigoCampoPadre(dto.getCodigoCampoPadre())
                    .build();
            caracteristicaRepository.save(entity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDatoCaracteristicaDTO> getCharacteristicTypesForCause(Long causaCodigo, Integer ramo, Integer producto) {
        return tipoDatoRepository.findActiveCharacteristicsByCause(ramo, producto, causaCodigo).stream()
                .map(t -> TipoDatoCaracteristicaDTO.builder()
                        .codigoCampo(t.getCodigoCampo())
                        .descripcion(t.getDescripcion())
                        .tipoDato(t.getTipoDato())
                        .longitud(t.getLongitud())
                        .listaValores(t.getListaValores())
                        .campoNoModificable(t.getCampoNoModificable())
                        .build())
                .collect(Collectors.toList());
    }
}
