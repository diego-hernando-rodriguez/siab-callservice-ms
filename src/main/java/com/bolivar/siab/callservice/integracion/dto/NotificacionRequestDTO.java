package com.bolivar.siab.callservice.integracion.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NotificacionRequestDTO {
    private String destinatario;
    private String asunto;
    private String cuerpo;
    private String tipo;
    private String telefono;
    private String mensaje;
}
