package com.bolivar.siab.callservice.poliza.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RiesgoBusquedaRequestDTO {
    private String valor;
    private Integer codigoCampo;
    private String tipoRiesgo;
}
