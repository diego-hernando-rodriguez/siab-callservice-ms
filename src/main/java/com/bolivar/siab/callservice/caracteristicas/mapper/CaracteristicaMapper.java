package com.bolivar.siab.callservice.caracteristicas.mapper;

import com.bolivar.siab.callservice.caracteristicas.dto.CaracteristicaCausaDTO;
import com.bolivar.siab.callservice.caracteristicas.models.CaracteristicaCausaLlamadaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CaracteristicaMapper {
    CaracteristicaCausaDTO toDTO(CaracteristicaCausaLlamadaEntity entity);
    List<CaracteristicaCausaDTO> toDTOList(List<CaracteristicaCausaLlamadaEntity> entities);
}
