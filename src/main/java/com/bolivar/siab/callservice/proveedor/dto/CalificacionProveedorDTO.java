package com.bolivar.siab.callservice.proveedor.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CalificacionProveedorDTO {
    private Long numeroAutorizacion;
    private Long consecutivoPunto;
    private Integer amabilidad;
    private Integer calificacionGeneral;
    private Integer calificacionTiempo;
    private Integer calificacionServicio;
    private Integer calificacionPresentacion;
    private Integer calificacionHerramientas;
    private String observaciones;
}
