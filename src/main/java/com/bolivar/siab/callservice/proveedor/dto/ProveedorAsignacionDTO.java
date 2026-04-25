package com.bolivar.siab.callservice.proveedor.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProveedorAsignacionDTO {
    private Long consecutivoPunto;
    private Long numeroAutorizacion;
    private Long llamadaNumero;
}
