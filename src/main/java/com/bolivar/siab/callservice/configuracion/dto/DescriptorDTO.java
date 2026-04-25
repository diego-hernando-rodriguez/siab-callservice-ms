package com.bolivar.siab.callservice.configuracion.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DescriptorDTO {
    private String tipo;
    private String codigo;
    private String descripcion;
}
