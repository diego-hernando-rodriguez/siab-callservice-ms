package com.bolivar.siab.callservice.integracion.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FieldServiceRequestDTO {
    private Long llamadaNumero;
    private Integer ramoCodigo;
    private Long locgeCodigo;
    private String tipoEnvio;
}
