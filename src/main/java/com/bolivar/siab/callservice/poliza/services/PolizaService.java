package com.bolivar.siab.callservice.poliza.services;

import com.bolivar.siab.callservice.poliza.dto.*;

public interface PolizaService {
    RiesgoBusquedaResponseDTO searchRisks(String valor);
    RiesgoAseguradoDTO createRisk(String contNumero, String riesgoCodigo, Integer codigoCampo, String valor);
    PolizaValidacionDTO validatePolicy(String contrato);
    String evaluateBeneficiaryId(String contNumero, Long pecoNumeroOrden);
    String getRiesgosCargue(String contNumero, String riesgoCodigo, Integer posicion);
}
