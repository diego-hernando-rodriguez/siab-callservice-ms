package com.bolivar.siab.callservice.caracteristicas.services;

import com.bolivar.siab.callservice.caracteristicas.dto.*;
import java.util.List;

public interface CaracteristicaService {
    CaracteristicaCausaResponseDTO loadCharacteristics(Long llamadaNumero, Integer ramo, Integer producto, Long causa);
    CaracteristicaCausaResponseDTO autoFillCharacteristics(Long llamadaNumero, Integer ramo, Integer producto, Long causa, String contNumero, String riesgoCodigo, Long locgeCodigo, String direccion);
    void saveCharacteristics(CaracteristicaCausaRequestDTO request);
    List<TipoDatoCaracteristicaDTO> getCharacteristicTypesForCause(Long causaCodigo, Integer ramo, Integer producto);
}
