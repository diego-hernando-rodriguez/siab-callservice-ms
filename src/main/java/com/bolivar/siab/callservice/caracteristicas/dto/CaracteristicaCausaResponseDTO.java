package com.bolivar.siab.callservice.caracteristicas.dto;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CaracteristicaCausaResponseDTO {
    private Long llamadaNumero;
    private List<CaracteristicaCausaDTO> caracteristicas;
    private Integer totalRegistros;
}
