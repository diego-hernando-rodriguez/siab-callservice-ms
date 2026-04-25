package com.bolivar.siab.callservice.caracteristicas.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CaracteristicaCausaDTO {
    private Integer codigoCampo;
    private String valor;
    private String dspCampo;
    private String dspTipoDato;
    private String listaValores;
    private String requerido;
    private String campoNoModificable;
    private Integer codigoCampoPadre;
}
