package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity for ADICIONALES_PRESTADOS table in NASIST schema.
 * Additional services rendered with tariff calculation.
 */
@Entity
@Table(name = "ADICIONALES_PRESTADOS", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(AdicionalesPrestadosEntity.AdicionalesPrestadosId.class)
public class AdicionalesPrestadosEntity {

    @Id
    @Column(name = "LLAMADA_NUMERO", nullable = false)
    private Long llamadaNumero;

    @Id
    @Column(name = "NUMERO_AUTORIZACION", nullable = false)
    private Long numeroAutorizacion;

    @Id
    @Column(name = "CODIGO", nullable = false)
    private Integer codigo;

    @Column(name = "SERV_CODIGO")
    private Integer servCodigo;

    @Column(name = "CLSERV_CODIGO")
    private Integer clservCodigo;

    @Column(name = "TIPO_SERVICIO", length = 100)
    private String tipoServicio;

    @Column(name = "L_TIPO_SERVICIO", length = 200)
    private String lTipoServicio;

    @Column(name = "HRS_ESPERA", precision = 10, scale = 2)
    private BigDecimal hrsEspera;

    @Column(name = "VALOR_UNITARIO", precision = 18, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "VALOR_TOTAL", precision = 18, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "SUMA_ADICIONAL", precision = 18, scale = 2)
    private BigDecimal sumaAdicional;

    @Column(name = "FECHA_SERVICIO")
    private LocalDateTime fechaServicio;

    @Column(name = "HORA_SERVICIO", length = 5)
    private String horaServicio;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdicionalesPrestadosId implements java.io.Serializable {
        private Long llamadaNumero;
        private Long numeroAutorizacion;
        private Integer codigo;
    }
}
