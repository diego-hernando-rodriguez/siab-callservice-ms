package com.bolivar.siab.callservice.serviciomanagement.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CitaDTO {
    private Long id;
    private Long llamadaNumero;
    private Long numeroAutorizacion;
    private String estado;
    private LocalDateTime fechaAsignacion;
    private String horaInicial;
    private String observaciones;
    private String direccion;
    private String telefono;
}

