package com.bolivar.siab.callservice.serviciomanagement.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ServicioPrestadoResponseDTO {
    private Long numeroAutorizacion;
    private Long llamadaNumero;
    private Integer servCodigo;
    private Integer clservCodigo;
    private String estadoServicio;
    private String moneda;
    private Long consecutivoPunto;
    private String personaNombre;
    private BigDecimal valorServicio;
    private BigDecimal valorTotal;
    private BigDecimal totalAdicionales;
    private BigDecimal porcentajeImpuesto;
    private String direccionOrigen;
    private String direccionDestino;
    private LocalDateTime fechaServicio;
    private String horaServicio;
    private String mcaEnvioClicksoftware;
    private String tipoDespacho;
    private String dspServCodigo;
    private String dspClaseServicio;
    private String dspEstadoServ;
    private String dspPersonaNombre;
    private String observaciones;
    private String zona;
    private String elite;
}
