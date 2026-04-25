package com.bolivar.siab.callservice.casomanagement.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CasoResponseDTO {
    private Long numero;
    private String contNumeroContrato;
    private String riesgoCodigo;
    private Long locgeCodigo;
    private Long causaCodigo;
    private String direccion;
    private String observacionesLar;
    private String numeroSiniestro;
    private String mcaEnvioClicksoftware;
    private String mcaEnvioSalesforce;
    private BigDecimal valorAtencion;
    private LocalDateTime fechaLlamada;
    private String horaLlamada;
    private String pais;
    private String usuNumeroDocumento;
    private String usuTipoDocumento;
    private Integer codigoCampo;
    private Integer ramoCodigo;
    private Integer productoCodigo;
    private String estadoLlamada;
    private String estadoPoliza;
    private String direccionComplemento;
    private String direccionDestino;
    private String direccionGeoReferencia;
    private String telefonoLlamada;
    private String severidad;
    private String lineaNegocio;
    private String alertaPyp;
    private String cobertura360;
    private String preferencial;
    // Descriptive fields from PKG_DESCRIPTORES
    private String dspRamo;
    private String dspProducto;
    private String dspNombre;
    private String dspTomador;
    private String dspRiesgo;
    private String dspEstadoLlamada;
    private String dspEstadoServ;
    private String dspDpto;
    private String dspFuncionarioBolivar;
    private String dspMechoqueHacedias;
    private String dspEnviadoCasoClick;
    private String dspTipoAsistencia;
    private String dspOpcionCobertura;
    private Integer excepciones;
    private LocalDate fechaInicioVig;
    private LocalDate fechaFinVig;
}
