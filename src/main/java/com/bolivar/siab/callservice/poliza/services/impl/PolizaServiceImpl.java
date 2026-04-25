package com.bolivar.siab.callservice.poliza.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.poliza.dto.*;
import com.bolivar.siab.callservice.poliza.models.*;
import com.bolivar.siab.callservice.poliza.repository.*;
import com.bolivar.siab.callservice.poliza.services.PolizaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolizaServiceImpl implements PolizaService {

    private final RiesgoAseguradoRepository riesgoRepository;
    private final PersonaContratoRepository personaContratoRepository;
    private final StoredProcedureRepository storedProcedureRepository;

    @Override
    @Transactional(readOnly = true)
    public RiesgoBusquedaResponseDTO searchRisks(String valor) {
        log.info("Searching risks by valor: {}", valor);

        // Native query returns Object[] rows to avoid Hibernate composite PK mapping issues
        List<Object[]> rows = riesgoRepository.findByValorSinCeros(valor);
        boolean encontrado = !rows.isEmpty();

        List<RiesgoAseguradoDTO> riesgos = new ArrayList<>();
        for (Object[] row : rows) {
            riesgos.add(RiesgoAseguradoDTO.builder()
                .riesgoCodigo(row[0] != null ? row[0].toString() : null)
                .valor(row[1] != null ? row[1].toString() : null)
                .codigoCampo(row[2] != null ? Integer.parseInt(row[2].toString()) : null)
                .ramoCodigo(row[3] != null ? Integer.parseInt(row[3].toString()) : null)
                .productoCodigo(row[4] != null ? Integer.parseInt(row[4].toString()) : null)
                .contNumero(row[5] != null ? row[5].toString() : null)
                .pecoNumeroOrden(row[7] != null ? Long.parseLong(row[7].toString()) : null)
                .tipcontCodigo(row[8] != null ? Integer.parseInt(row[8].toString()) : null)
                .valorSinCeros(row[9] != null ? row[9].toString() : null)
                .build());
        }

        RiesgoBusquedaResponseDTO response = RiesgoBusquedaResponseDTO.builder()
                .riesgos(riesgos)
                .encontrado(encontrado)
                .build();

        // Enrich first result with cargo data
        if (encontrado) {
            RiesgoAseguradoDTO first = riesgos.get(0);
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
        return RiesgoAseguradoDTO.builder()
                .contNumero(contNumero)
                .riesgoCodigo(riesgoCodigo)
                .codigoCampo(codigoCampo)
                .valor(valor)
                .numeroOrden(numeroOrden)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PolizaValidacionDTO validatePolicy(String contrato) {
        List<RiesgoAseguradoEntity> riesgos = riesgoRepository.findByContNumeroContrato(contrato);
        if (riesgos.isEmpty()) {
            return PolizaValidacionDTO.builder()
                    .contNumero("ASISTBOL")
                    .polizaValida(false)
                    .esInexistente(true)
                    .mensaje("Póliza no encontrada, asignando contrato inexistente ASISTBOL")
                    .build();
        }
        RiesgoAseguradoEntity first = riesgos.get(0);
        return PolizaValidacionDTO.builder()
                .contNumero(first.getContNumeroContrato())
                .polizaValida(true)
                .esInexistente(false)
                .ramoCodigo(first.getRamoCodigo() != null ? Integer.parseInt(first.getRamoCodigo()) : null)
                .productoCodigo(first.getProductoCodigo() != null ? Integer.parseInt(first.getProductoCodigo()) : null)
                .estadoPoliza("A")
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
