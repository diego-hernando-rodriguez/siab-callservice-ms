package com.bolivar.siab.callservice.casomanagement.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ExcepcionDTO {
    private Long id;
    private Long numeroLlamada;
    private String numeroSiniestro;
    private Integer codigoRamo;
    private Integer codigoProducto;
    private Integer codigoPolitica;
    private Integer codigoDefinicion;
    private Integer codigoRol;
    private String autorizador;
    private String estadoAutorizado;
    private String observacionAutorizador;
    private LocalDateTime fechaAutorizacion;
    private String descRamo;
    private String descProducto;
    private String descPolitica;
    private String descDefinicion;
    private String descRol;
}
