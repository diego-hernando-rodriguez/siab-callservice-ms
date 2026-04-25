package com.bolivar.siab.callservice.casomanagement.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReclasificacionRequestDTO {
    private Long numeroLlamada;
    private Integer ramoNuevo;
    private Integer productoNuevo;
    private Long causaNueva;
    private String codRazon;
    private String descripcionRazon;
}
