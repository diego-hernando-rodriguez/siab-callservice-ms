package com.bolivar.siab.callservice.integracion.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FieldServiceResponseDTO {
    private boolean enviado;
    private String resultado;
    private String tipo;
}
