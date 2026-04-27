package com.bolivar.siab.callservice.configuracion.services;

import com.bolivar.siab.callservice.configuracion.dto.DescriptorDTO;

public interface DescriptorService {
    String getDescriptorRamo(Integer ramoCodigo);
    String getDescriptorProducto(Integer ramoCodigo, Integer productoCodigo);
    String getDescriptorCausa(Integer ramoCodigo, Integer productoCodigo, Long causaCodigo);
    String getDescriptorCaracteristicas(Integer codigoCampo);
    String getDescriptorEntidad(Long locgeCodigo);
}
