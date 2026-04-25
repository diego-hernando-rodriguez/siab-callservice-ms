package com.bolivar.siab.callservice.casomanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CasoRequestDTO {
    @NotNull(message = "Ciudad es requerida")
    private Long locgeCodigo;
    @NotNull(message = "Código de riesgo es requerido")
    private String riesgoCodigo;
    @NotNull(message = "Código de causa es requerido")
    private Long causaCodigo;
    @NotNull(message = "Dirección es requerida")
    private String direccion;
    @NotNull(message = "Número de documento del usuario es requerido")
    private String usuNumeroDocumento;
    private String usuTipoDocumento;
    private Integer codigoCampo;
    private String contNumeroContrato;
    private Integer ramoCodigo;
    private Integer productoCodigo;
    private String observacionesLar;
    private String direccionComplemento;
    private String direccionDestino;
    private String direccionGeoReferencia;
    private String telefonoLlamada;
    private String severidad;
    private String lineaNegocio;
    private String pais;
    private Integer tlgCodigo;
}
