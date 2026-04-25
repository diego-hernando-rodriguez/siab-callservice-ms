package com.bolivar.siab.callservice.tarifa.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DetalleTarifaDTO {
    private Long consecutivo;
    private BigDecimal valorMedida;
    private BigDecimal ancho;
    private BigDecimal alto;
    private Integer cantidad;
    private BigDecimal totalDetalleTar;
    private String dspTarifaDetalle;
}
