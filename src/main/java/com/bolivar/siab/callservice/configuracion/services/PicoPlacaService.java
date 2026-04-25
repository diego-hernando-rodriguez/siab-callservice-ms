package com.bolivar.siab.callservice.configuracion.services;

public interface PicoPlacaService {
    String getTipoRestriccion(Long locgeCodigo);
    String getPicoPlaca(String placa, Long locgeCodigo);
    String getRestriccion(String placa, Long locgeCodigo);
    String evaluateAlertaPyp(String placa, Long locgeCodigo);
}
