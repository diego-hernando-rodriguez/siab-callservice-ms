package com.bolivar.siab.callservice.casomanagement.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CartaDTO {
    private Long id;
    private Long llamadaNumero;
    private Long numeroAutorizacion;
    private String tipoCarta;
    private String contenido;
    private String destinatario;
    private String estado;
    private LocalDateTime fechaCreacion;
    private String usuarioCreacion;
}
