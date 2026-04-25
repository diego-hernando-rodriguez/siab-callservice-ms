package com.bolivar.siab.callservice.tarifa.services;

import com.bolivar.siab.callservice.tarifa.dto.*;
import java.util.List;

public interface TarifaService {
    TarifaDTO calculateTariff(Long llamadaNumero, Long numeroAutorizacion, Integer servCodigo, Integer clservCodigo);
    List<TarifaDTO> getTariffsForService(Long llamadaNumero, Long numeroAutorizacion);
    List<RutaIntermediaDTO> getRoutes(Long llamadaNumero, Long numeroAutorizacion);
}
