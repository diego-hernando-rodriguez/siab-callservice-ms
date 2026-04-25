package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

/**
 * JPA Entity for VALORES_SERVICIO table in NASIST schema.
 * Tariff values per service type.
 */
@Entity @Immutable
@Table(name = "VALORES_SERVICIO", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ValoresServicioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "SERV_CODIGO") private Integer servCodigo;
    @Column(name = "CLSERV_CODIGO") private Integer clservCodigo;
    @Column(name = "RAMO_CODIGO") private Integer ramoCodigo;
    @Column(name = "PRODUCTO_CODIGO") private Integer productoCodigo;
    @Column(name = "CAUSA_CODIGO") private Long causaCodigo;
    @Column(name = "LOCGE_CODIGO") private Long locgeCodigo;
    @Column(name = "VALOR_SERVICIO", precision = 18, scale = 2) private BigDecimal valorServicio;
    @Column(name = "PORCENTAJE_IMPUESTO", precision = 5, scale = 2) private BigDecimal porcentajeImpuesto;
    @Column(name = "ESTADO", length = 5) private String estado;
    @Column(name = "APLICA_CANTIDAD", length = 1) private String aplicaCantidad;
    @Column(name = "CAMBIO_TARIFA_OPTIMA", length = 1) private String cambioTarifaOptima;
}
