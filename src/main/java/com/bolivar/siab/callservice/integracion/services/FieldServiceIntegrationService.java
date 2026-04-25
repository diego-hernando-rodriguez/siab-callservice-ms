package com.bolivar.siab.callservice.integracion.services;

import com.bolivar.siab.callservice.integracion.dto.FieldServiceResponseDTO;

public interface FieldServiceIntegrationService {
    boolean isFieldServiceEnabled(Integer ramoCodigo, Long locgeCodigo);
    FieldServiceResponseDTO sendToFieldService(Long llamadaNumero);
    void createFieldServiceCase(Long llamadaNumero);
    String checkCityRamoClick(Long locgeCodigo, Integer ramoCodigo);
}
