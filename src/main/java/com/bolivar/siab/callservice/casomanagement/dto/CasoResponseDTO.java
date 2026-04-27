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
    private Long numeroSiniestro;
    private String mcaEnvioClicksoftware;
    private String mcaEnvioSalesforce;
    private BigDecimal valorAtencion;
    private LocalDateTime fechaLlamada;
    private Integer horaLlamada;
    private String horaLlamadaFormatted;
    private String pais;
    private String usuNumeroDocumento;
    private String usuTipoDocumento;
    private Integer codigoCampo;
    private String ramoCodigo;
    private String productoCodigo;
    private String estadoLlamada;
    private String estadoPoliza;
    private String direccionComplemento;
    private String direccionDestino;
    private String direccionGeoReferencia;
    private String telefonoLlamada;
    private String severidad;
    private String lineaNegocio;
    private String placaRiesgo;
    private String altoValor;
    private String acuerdoCliente;
    private String origen;
    private String preferencial;
    private LocalDate contFechaInicioVigencia;
    private LocalDate contFechaFinVigencia;
    private Integer tipcontCodigo;
    private Long pecoNumeroOrden;
    // Descriptive fields (enriched from PKG_DESCRIPTORES)
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
    private String dspCoberturaVehiculo;
    private Integer excepciones;
}
