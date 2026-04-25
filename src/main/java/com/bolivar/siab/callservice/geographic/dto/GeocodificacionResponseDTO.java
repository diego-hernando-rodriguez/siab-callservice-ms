package com.bolivar.siab.callservice.geographic.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class GeocodificacionResponseDTO {
    private String latitud;
    private String longitud;
    private String direccionFormateada;
    private String ciudad;
    private boolean encontrado;
}
