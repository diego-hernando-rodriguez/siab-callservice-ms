package com.bolivar.siab.callservice.poliza.services;

import com.bolivar.siab.callservice.poliza.dto.*;

public interface PolizaService {
    RiesgoBusquedaResponseDTO searchRisks(String valor);
    RiesgoAseguradoDTO createRisk(String contNumero, String riesgoCodigo, Integer codigoCampo, String valor);
    PolizaValidacionDTO validatePolicy(String contrato);
    String evaluateBeneficiaryId(String contNumero, Long pecoNumeroOrden);
    String getRiesgosCargue(String contNumero, String riesgoCodigo, Integer posicion);
    java.util.List<java.util.Map<String, Object>> getProductosConsulta(String ramo, String producto, Long pais, String valor, Integer codigoCampo);
    java.util.List<java.util.Map<String, Object>> getRiesgosCedula(String ramo, String producto, String ramo2, String producto2, String valor, Long pais);
}
