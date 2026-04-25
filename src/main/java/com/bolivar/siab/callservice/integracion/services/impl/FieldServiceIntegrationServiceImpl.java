package com.bolivar.siab.callservice.integracion.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.integracion.dto.FieldServiceResponseDTO;
import com.bolivar.siab.callservice.integracion.services.FieldServiceIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FieldServiceIntegrationServiceImpl implements FieldServiceIntegrationService {

    private final StoredProcedureRepository storedProcedureRepository;

    @Override
    public boolean isFieldServiceEnabled(Integer ramoCodigo, Long locgeCodigo) {
        String result = storedProcedureRepository.isHabilitadoClick(ramoCodigo, locgeCodigo);
        return "S".equals(result);
    }

    @Override
    public FieldServiceResponseDTO sendToFieldService(Long llamadaNumero) {
        log.info("Sending case {} to FieldService (ClickSoftware)", llamadaNumero);
        String result = storedProcedureRepository.enviaClicksoftware(llamadaNumero);
        return FieldServiceResponseDTO.builder()
                .enviado("S".equals(result))
                .resultado(result)
                .tipo("CLICKSOFTWARE")
                .build();
    }

    @Override
    public void createFieldServiceCase(Long llamadaNumero) {
        log.info("Creating FieldService case for llamada: {}", llamadaNumero);
        storedProcedureRepository.creacionLlamadas(llamadaNumero);
    }

    @Override
    public String checkCityRamoClick(Long locgeCodigo, Integer ramoCodigo) {
        return storedProcedureRepository.getCiudadRamoClick(locgeCodigo, ramoCodigo);
    }
}
