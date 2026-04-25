package com.bolivar.siab.callservice.poliza.mapper;

import com.bolivar.siab.callservice.poliza.dto.RiesgoAseguradoDTO;
import com.bolivar.siab.callservice.poliza.models.RiesgoAseguradoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PolizaMapper {
    RiesgoAseguradoDTO toDTO(RiesgoAseguradoEntity entity);
    List<RiesgoAseguradoDTO> toDTOList(List<RiesgoAseguradoEntity> entities);
}
