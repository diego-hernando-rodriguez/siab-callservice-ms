package com.bolivar.siab.callservice.configuracion.services;

import com.bolivar.siab.callservice.configuracion.dto.VariableGlobalDTO;
import java.util.List;

public interface VariablesGlobalesService {
    String getIndicadorSi();
    String getIndicadorNo();
    String getRetroNoEfectivaRetroLlamada();
    List<VariableGlobalDTO> getAllVariables();
}
