package com.bolivar.siab.callservice.serviciomanagement.mapper;

import com.bolivar.siab.callservice.serviciomanagement.dto.ServicioPrestadoResponseDTO;
import com.bolivar.siab.callservice.serviciomanagement.models.ServicioPrestadoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServicioMapper {
    ServicioPrestadoResponseDTO toResponse(ServicioPrestadoEntity entity);
}
