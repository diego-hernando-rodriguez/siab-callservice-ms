package com.bolivar.siab.callservice.configuracion.services;

import com.bolivar.siab.callservice.configuracion.dto.DominioDTO;
import java.util.List;

public interface ConfiguracionService {
    String getValDominio(String dominio, String referencia);
    List<DominioDTO> getDomainValues(String domain);
}
