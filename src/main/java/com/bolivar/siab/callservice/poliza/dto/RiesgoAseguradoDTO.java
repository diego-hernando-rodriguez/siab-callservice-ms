package com.bolivar.siab.callservice.poliza.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RiesgoAseguradoDTO {
    private String contNumero;
    private String riesgoCodigo;
    private Integer codigoCampo;
    private String valor;
    private String valorSinCeros;
    private Integer ramoCodigo;
    private Integer productoCodigo;
    private Integer tipcontCodigo;
    private Long pecoNumeroOrden;
    private String estado;
    private Long numeroOrden;
}
