package com.bolivar.siab.callservice.serviciomanagement.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdicionalesPrestadosDTO {
    private Long llamadaNumero;
    private Long numeroAutorizacion;
    private Integer codigo;
    private String tipoServicio;
    private String lTipoServicio;
    private BigDecimal hrsEspera;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private BigDecimal sumaAdicional;
}
