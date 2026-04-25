package com.bolivar.siab.callservice.casomanagement.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReclasificacionResponseDTO {
    private Long nuevoNumeroLlamada;
    private String resultado;
}
