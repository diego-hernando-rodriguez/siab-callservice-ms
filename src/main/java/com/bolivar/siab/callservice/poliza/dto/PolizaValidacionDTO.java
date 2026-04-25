package com.bolivar.siab.callservice.poliza.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PolizaValidacionDTO {
    private String contNumero;
    private boolean polizaValida;
    private boolean esInexistente;
    private String mensaje;
    private Integer ramoCodigo;
    private Integer productoCodigo;
    private String estadoPoliza;
    private boolean pideIdTitular;
}
