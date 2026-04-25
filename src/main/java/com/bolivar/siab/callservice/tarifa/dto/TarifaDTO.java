package com.bolivar.siab.callservice.tarifa.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TarifaDTO {
    private Long tarifaCodigo;
    private Long llamadaNumero;
    private Long numeroAutorizacion;
    private BigDecimal valorTarifa;
    private BigDecimal valorImpuesto;
    private BigDecimal porcentajeImpuesto;
    private BigDecimal valorDescuento;
    private BigDecimal valorTotal;
    private String estado;
    private List<DetalleTarifaDTO> detalles;
}
