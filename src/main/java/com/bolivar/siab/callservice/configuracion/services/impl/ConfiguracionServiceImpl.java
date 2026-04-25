package com.bolivar.siab.callservice.configuracion.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.configuracion.dto.DominioDTO;
import com.bolivar.siab.callservice.configuracion.models.CgRefCodesEntity;
import com.bolivar.siab.callservice.configuracion.repository.CgRefCodesRepository;
import com.bolivar.siab.callservice.configuracion.services.ConfiguracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfiguracionServiceImpl implements ConfiguracionService {

    private final StoredProcedureRepository storedProcedureRepository;
    private final CgRefCodesRepository cgRefCodesRepository;

    @Override
    public String getValDominio(String dominio, String referencia) {
        return storedProcedureRepository.getValDominio(dominio, referencia);
    }

    @Override
    public List<DominioDTO> getDomainValues(String domain) {
        return cgRefCodesRepository.findByRvDomain(domain).stream()
                .map(e -> DominioDTO.builder()
                        .rvDomain(e.getRvDomain())
                        .rvLowValue(e.getRvLowValue())
                        .rvHighValue(e.getRvHighValue())
                        .rvAbbreviation(e.getRvAbbreviation())
                        .rvMeaning(e.getRvMeaning())
                        .build())
                .collect(Collectors.toList());
    }
}
