package com.bolivar.siab.callservice.caracteristicas.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TipoDatoCaracteristicaDTO {
    private Integer codigoCampo;
    private String descripcion;
    private String tipoDato;
    private Integer longitud;
    private String listaValores;
    private String campoNoModificable;
}
