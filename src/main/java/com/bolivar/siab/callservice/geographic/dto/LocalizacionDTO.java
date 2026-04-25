package com.bolivar.siab.callservice.geographic.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class LocalizacionDTO {
    private Long locgeCodigo;
    private Integer tlgCodigo;
    private String nombre;
    private Long locgeCodigoPadre;
    private String pais;
    private String departamento;
    private String latitud;
    private String longitud;
}
