package com.bolivar.siab.callservice.serviciomanagement.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ServicioPrestadoRequestDTO {
    private Long llamadaNumero;
    private Integer servCodigo;
    private Integer clservCodigo;
    private String moneda;
    private String direccionOrigen;
    private String direccionDestino;
    private Long locgeCodigoOrigen;
    private Long locgeCodigoDestino;
    private String observaciones;
    private String tipoDespacho;
}
