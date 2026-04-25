package com.bolivar.siab.callservice.serviciomanagement.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class InformacionServicioDTO {
    private Long numeroAutorizacion;
    private Integer codigoCampo;
    private String valor;
    private String requerido;
    private String dspCampo;
    private Integer clservCodigo;
    private Integer servCodigo;
}
