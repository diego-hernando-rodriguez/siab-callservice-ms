package com.bolivar.siab.callservice.configuracion.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.configuracion.dto.VariableGlobalDTO;
import com.bolivar.siab.callservice.configuracion.services.VariablesGlobalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariablesGlobalesServiceImpl implements VariablesGlobalesService {

    private final StoredProcedureRepository storedProcedureRepository;

    @Override
    public String getIndicadorSi() {
        return storedProcedureRepository.getIndicadorSi();
    }

    @Override
    public String getIndicadorNo() {
        return storedProcedureRepository.getIndicadorNo();
    }

    @Override
    public String getRetroNoEfectivaRetroLlamada() {
        return storedProcedureRepository.getRetroNoEfectivaRetroLlamada();
    }

    @Override
    public List<VariableGlobalDTO> getAllVariables() {
        return List.of(
                VariableGlobalDTO.builder().nombre("INDICADOR_SI").valor(getIndicadorSi()).build(),
                VariableGlobalDTO.builder().nombre("INDICADOR_NO").valor(getIndicadorNo()).build(),
                VariableGlobalDTO.builder().nombre("RETRO_NO_EFECTIVA").valor(getRetroNoEfectivaRetroLlamada()).build()
        );
    }
}
