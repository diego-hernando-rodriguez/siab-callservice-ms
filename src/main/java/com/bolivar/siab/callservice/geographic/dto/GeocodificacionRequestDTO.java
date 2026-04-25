package com.bolivar.siab.callservice.geographic.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class GeocodificacionRequestDTO {
    private Long locgeCodigo;
    private String direccion;
    private String usuario;
}
