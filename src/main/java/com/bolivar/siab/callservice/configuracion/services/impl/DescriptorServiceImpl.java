package com.bolivar.siab.callservice.configuracion.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.configuracion.services.DescriptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DescriptorServiceImpl implements DescriptorService {

    private final StoredProcedureRepository storedProcedureRepository;

    @Override
    public String getDescriptorRamo(Integer ramoCodigo) {
        return storedProcedureRepository.getDescriptorRamo(ramoCodigo);
    }

    @Override
    public String getDescriptorProducto(Integer productoCodigo) {
        return storedProcedureRepository.getDescriptorProducto(productoCodigo);
    }

    @Override
    public String getDescriptorCausa(Long causaCodigo) {
        return storedProcedureRepository.getDescriptorCausa(causaCodigo);
    }

    @Override
    public String getDescriptorCaracteristicas(Integer codigoCampo) {
        return storedProcedureRepository.getDescriptorCaracteristicas(codigoCampo);
    }

    @Override
    public String getDescriptorEntidad(Long locgeCodigo) {
        return storedProcedureRepository.getDescriptorEntidad(locgeCodigo);
    }
}
