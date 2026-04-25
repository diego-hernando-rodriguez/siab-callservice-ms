package com.bolivar.siab.callservice.serviciomanagement.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class IngresoRequestDTO {
    private Long numeroAutorizacion;
    private String formaPago;
    private BigDecimal valor;
    private String tipoTarjeta;
    private String personaTarjetahabiente;
    private Integer numeroCuotasTarjeta;
}

