package com.bolivar.siab.callservice.configuracion.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class VariableGlobalDTO {
    private String nombre;
    private String valor;
}
