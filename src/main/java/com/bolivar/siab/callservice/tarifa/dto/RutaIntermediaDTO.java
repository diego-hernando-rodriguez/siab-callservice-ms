package com.bolivar.siab.callservice.tarifa.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RutaIntermediaDTO {
    private Long id;
    private Integer secuencia;
    private String direccion;
    private String latitud;
    private String longitud;
    private BigDecimal distanciaKm;
    private String descripcion;
}
