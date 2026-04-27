package com.bolivar.siab.callservice.casomanagement.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AcuerdoDTO {
    private Long id;
    private String descripcion;
    private String estado;
}
