package com.bolivar.siab.callservice.casomanagement.mapper;

import com.bolivar.siab.callservice.casomanagement.dto.CasoRequestDTO;
import com.bolivar.siab.callservice.casomanagement.dto.CasoResponseDTO;
import com.bolivar.siab.callservice.casomanagement.models.LlamadaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CasoMapper {
    CasoResponseDTO toResponse(LlamadaEntity entity);

    @Mapping(target = "numero", ignore = true)
    @Mapping(target = "fechaLlamada", ignore = true)
    @Mapping(target = "numeroSiniestro", ignore = true)
    LlamadaEntity toEntity(CasoRequestDTO request);

    void updateEntity(CasoRequestDTO request, @MappingTarget LlamadaEntity entity);
}
