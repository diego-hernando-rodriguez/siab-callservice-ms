package com.bolivar.siab.callservice.poliza.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.poliza.dto.*;
import com.bolivar.siab.callservice.poliza.mapper.PolizaMapper;
import com.bolivar.siab.callservice.poliza.models.*;
import com.bolivar.siab.callservice.poliza.repository.*;
import com.bolivar.siab.callservice.poliza.services.PolizaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolizaServiceImpl implements PolizaService {

    private final RiesgoAseguradoRepository riesgoRepository;
    private final PersonaContratoRepository personaContratoRepository;
    private final StoredProcedureRepository storedProcedureRepository;
    private final PolizaMapper polizaMapper;

    @Override
    @Transactional(readOnly = true)
    public RiesgoBusquedaResponseDTO searchRisks(String valor) {
        // SQL-01: Search by VALOR_SIN_CEROS (LTRIM logic), excluding CODIGO_CAMPO IN (48, 4, 81)
        List<RiesgoAseguradoEntity> riesgos = riesgoRepository.findByValorSinCeros(valor);
        boolean encontrado = !riesgos.isEmpty();
        RiesgoBusquedaResponseDTO response = RiesgoBusquedaResponseDTO.builder()
                .riesgos(polizaMapper.toDTOList(riesgos))
                .encontrado(encontrado)
                .build();

        if (encontrado) {
            RiesgoAseguradoEntity first = riesgos.get(0);
            try {
                response.setModelo(storedProcedureRepository.getRiesgosCargue(first.getContNumero(), first.getRiesgoCodigo(), 3));
                response.setColor(storedProcedureRepository.getRiesgosCargue(first.getContNumero(), first.getRiesgoCodigo(), 4));
                response.setTipoAsistencia(storedProcedureRepository.getRiesgosCargue(first.getContNumero(), first.getRiesgoCodigo(), 5));
                response.setOpcionCobertura(storedProcedureRepository.getRiesgosCargue(first.getContNumero(), first.getRiesgoCodigo(), 6));
            } catch (Exception e) {
                log.warn("Error loading risk cargo data: {}", e.getMessage());
            }
        }
        return response;
    }

    @Override
    @Transactional
    public RiesgoAseguradoDTO createRisk(String contNumero, String riesgoCodigo, Integer codigoCampo, String valor) {
        Long numeroOrden = storedProcedureRepository.getConsecutivoSiab("NUMERO_ORDEN");

        PersonaContratoEntity persona = PersonaContratoEntity.builder()
                .numeroOrden(numeroOrden)
                .contNumero(contNumero)
                .build();
        personaContratoRepository.save(persona);

        RiesgoAseguradoEntity riesgo = RiesgoAseguradoEntity.builder()
                .contNumero(contNumero)
                .riesgoCodigo(riesgoCodigo)
                .codigoCampo(codigoCampo)
                .valor(valor)
                .valorSinCeros(valor != null ? valor.replaceFirst("^0+", "") : null)
                .numeroOrden(numeroOrden)
                .build();
        riesgoRepository.save(riesgo);
        return polizaMapper.toDTO(riesgo);
    }

    @Override
    @Transactional(readOnly = true)
    public PolizaValidacionDTO validatePolicy(String contrato) {
        List<RiesgoAseguradoEntity> riesgos = riesgoRepository.findByContNumero(contrato);
        if (riesgos.isEmpty()) {
            // Assign wildcard ASISTBOL policy
            return PolizaValidacionDTO.builder()
                    .contNumero("ASISTBOL")
                    .polizaValida(false)
                    .esInexistente(true)
                    .mensaje("Póliza no encontrada, asignando contrato inexistente ASISTBOL")
                    .build();
        }
        RiesgoAseguradoEntity first = riesgos.get(0);
        return PolizaValidacionDTO.builder()
                .contNumero(first.getContNumero())
                .polizaValida(true)
                .esInexistente(false)
                .ramoCodigo(first.getRamoCodigo())
                .productoCodigo(first.getProductoCodigo())
                .estadoPoliza(first.getEstado())
                .build();
    }

    @Override
    public String evaluateBeneficiaryId(String contNumero, Long pecoNumeroOrden) {
        return storedProcedureRepository.evaluaPideIdTitular(contNumero, pecoNumeroOrden);
    }

    @Override
    public String getRiesgosCargue(String contNumero, String riesgoCodigo, Integer posicion) {
        return storedProcedureRepository.getRiesgosCargue(contNumero, riesgoCodigo, posicion);
    }
}
