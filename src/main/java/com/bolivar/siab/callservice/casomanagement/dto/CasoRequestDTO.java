package com.bolivar.siab.callservice.casomanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CasoRequestDTO {
    @NotNull(message = "Ciudad es requerida")
    private Long locgeCodigo;
    @NotNull(message = "Codigo de riesgo es requerido")
    private String riesgoCodigo;
    @NotNull(message = "Codigo de causa es requerido")
    private Long causaCodigo;
    @NotNull(message = "Direccion es requerida")
    private String direccion;
    private String usuNumeroDocumento;
    private String usuTipoDocumento;
    private Integer codigoCampo;
    private String contNumeroContrato;
    private String ramoCodigo;
    private String productoCodigo;
    private String observacionesLar;
    private String direccionComplemento;
    private String direccionGeoReferencia;
    private String telefonoLlamada;
    private String severidad;
    private String lineaNegocio;
    private String pais;
    private Integer tlgCodigo;
    private Integer tipcontCodigo;
    private Long pecoNumeroOrden;
    private String contFechaInicioVigencia;
    private String placaRiesgo;
}
