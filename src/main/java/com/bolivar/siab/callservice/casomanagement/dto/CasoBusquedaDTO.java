package com.bolivar.siab.callservice.casomanagement.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CasoBusquedaDTO {
    private String contNumero;
    private String usuNumeroDocumento;
    private String numeroSiniestro;
    private Long locgeCodigo;
    private String estadoLlamada;
}
