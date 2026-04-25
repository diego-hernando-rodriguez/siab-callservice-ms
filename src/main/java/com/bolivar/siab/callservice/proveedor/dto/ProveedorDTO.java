package com.bolivar.siab.callservice.proveedor.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProveedorDTO {
    private Long consecutivoPunto;
    private String personaNumeroDocumento;
    private String nombre;
    private String estado;
    private Integer servCodigo;
    private Integer clservCodigo;
    private Long locgeCodigo;
    private String zona;
    private String celular;
    private String elite;
}
