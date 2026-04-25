package com.bolivar.siab.callservice.integracion.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NotificacionResponseDTO {
    private boolean enviado;
    private String resultado;
}
