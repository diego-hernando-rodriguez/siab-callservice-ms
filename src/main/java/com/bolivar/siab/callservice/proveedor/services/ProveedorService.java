package com.bolivar.siab.callservice.proveedor.services;

import com.bolivar.siab.callservice.proveedor.dto.*;
import java.util.List;

public interface ProveedorService {
    List<ProveedorDTO> searchProviders(Integer servCodigo, Integer clservCodigo, Long locgeCodigo);
    ProveedorDTO assignProvider(Long consecutivoPunto, Long numeroAutorizacion, Long llamadaNumero);
    void rateProvider(CalificacionProveedorDTO calificacion);
}
