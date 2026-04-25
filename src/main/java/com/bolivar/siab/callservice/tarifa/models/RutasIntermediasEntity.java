package com.bolivar.siab.callservice.tarifa.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * JPA Entity for RUTAS_INTERMEDIAS table in NASIST schema.
 * Intermediate route points for service delivery.
 */
@Entity
@Table(name = "RUTAS_INTERMEDIAS", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RutasIntermediasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "LLAMADA_NUMERO")
    private Long llamadaNumero;

    @Column(name = "NUMERO_AUTORIZACION")
    private Long numeroAutorizacion;

    @Column(name = "SERV_CODIGO")
    private Integer servCodigo;

    @Column(name = "CLSERV_CODIGO")
    private Integer clservCodigo;

    @Column(name = "SECUENCIA")
    private Integer secuencia;

    @Column(name = "DIRECCION", length = 500)
    private String direccion;

    @Column(name = "LATITUD", length = 30)
    private String latitud;

    @Column(name = "LONGITUD", length = 30)
    private String longitud;

    @Column(name = "LOCGE_CODIGO")
    private Long locgeCodigo;

    @Column(name = "DISTANCIA_KM", precision = 10, scale = 2)
    private BigDecimal distanciaKm;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "ESTADO", length = 5)
    private String estado;
}
