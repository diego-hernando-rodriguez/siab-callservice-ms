package com.bolivar.siab.callservice.tarifa.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity for TARIFA_LLAMADA table in NASIST schema.
 * Stores tariff records for cases/services.
 */
@Entity
@Table(name = "TARIFA_LLAMADA", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarifaLlamadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TARIFA_CODIGO", nullable = false)
    private Long tarifaCodigo;

    @Column(name = "LLAMADA_NUMERO")
    private Long llamadaNumero;

    @Column(name = "SERV_CODIGO")
    private Integer servCodigo;

    @Column(name = "CLSERV_CODIGO")
    private Integer clservCodigo;

    @Column(name = "NUMERO_AUTORIZACION")
    private Long numeroAutorizacion;

    @Column(name = "VALOR_TARIFA", precision = 18, scale = 2)
    private BigDecimal valorTarifa;

    @Column(name = "VALOR_IMPUESTO", precision = 18, scale = 2)
    private BigDecimal valorImpuesto;

    @Column(name = "PORCENTAJE_IMPUESTO", precision = 5, scale = 2)
    private BigDecimal porcentajeImpuesto;

    @Column(name = "VALOR_DESCUENTO", precision = 18, scale = 2)
    private BigDecimal valorDescuento;

    @Column(name = "VALOR_TOTAL", precision = 18, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "FECHA_TARIFA")
    private LocalDateTime fechaTarifa;

    @Column(name = "HORA_TARIFA", length = 5)
    private String horaTarifa;

    @Column(name = "ESTADO", length = 5)
    private String estado;

    @Column(name = "USUARIO_CREACION", length = 50)
    private String usuarioCreacion;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;
}
