package com.bolivar.siab.callservice.serviciomanagement.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class IngresoResponseDTO {
    private Long secuencia;
    private Long numeroAutorizacion;
    private String formaPago;
    private BigDecimal valor;
    private BigDecimal totalPagadoUsr;
    private BigDecimal pendiente;
    private LocalDateTime fechaCreacion;
    private String dspEntidadNombre;
}
