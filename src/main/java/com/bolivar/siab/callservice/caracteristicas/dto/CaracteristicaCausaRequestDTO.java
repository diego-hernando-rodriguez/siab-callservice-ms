package com.bolivar.siab.callservice.caracteristicas.dto;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CaracteristicaCausaRequestDTO {
    private Long llamadaNumero;
    private Integer ramoCodigo;
    private Integer productoCodigo;
    private Long causaCodigo;
    private List<CaracteristicaCausaDTO> caracteristicas;
}
