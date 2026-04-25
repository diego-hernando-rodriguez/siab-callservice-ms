package com.bolivar.siab.callservice.configuracion.services;

import com.bolivar.siab.callservice.configuracion.dto.DescriptorDTO;

public interface DescriptorService {
    String getDescriptorRamo(Integer ramoCodigo);
    String getDescriptorProducto(Integer productoCodigo);
    String getDescriptorCausa(Long causaCodigo);
    String getDescriptorCaracteristicas(Integer codigoCampo);
    String getDescriptorEntidad(Long locgeCodigo);
}
