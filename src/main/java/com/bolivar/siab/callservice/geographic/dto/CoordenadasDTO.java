package com.bolivar.siab.callservice.geographic.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CoordenadasDTO {
    private String latitud;
    private String longitud;
    private String direccion;
    private Long locgeCodigo;
}
