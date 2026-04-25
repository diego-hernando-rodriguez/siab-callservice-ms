package com.bolivar.siab.callservice.casomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for LLAMADA_EXCEPCION table in NASIST schema.
 * Exception records for cases requiring special authorization.
 */
@Entity
@Table(name = "LLAMADA_EXCEPCION", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LlamadaExcepcionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NUMERO_LLAMADA")
    private Long numeroLlamada;

    @Column(name = "NUMERO_SINIESTRO", length = 30)
    private String numeroSiniestro;

    @Column(name = "CODIGO_RAMO")
    private Integer codigoRamo;

    @Column(name = "CODIGO_PRODUCTO")
    private Integer codigoProducto;

    @Column(name = "CODIGO_POLITICA")
    private Integer codigoPolitica;

    @Column(name = "CODIGO_DEFINICION")
    private Integer codigoDefinicion;

    @Column(name = "CODIGO_ROL")
    private Integer codigoRol;

    @Column(name = "AUTORIZADOR", length = 50)
    private String autorizador;

    @Column(name = "TIPO_IDENTIFICACION", length = 5)
    private String tipoIdentificacion;

    @Column(name = "NUMERO_IDENTIFICACION", length = 30)
    private String numeroIdentificacion;

    @Column(name = "ESTADO_AUTORIZADO", length = 5)
    private String estadoAutorizado;

    @Column(name = "OBSERVACION_AUTORIZADOR", length = 4000)
    private String observacionAutorizador;

    @Column(name = "FECHA_AUTORIZACION")
    private LocalDateTime fechaAutorizacion;

    @Column(name = "FECHA_CREA")
    private LocalDateTime fechaCrea;

    @Column(name = "USUARIO_CREA", length = 50)
    private String usuarioCrea;

    @Column(name = "GUARDAR", length = 1)
    private String guardar;

    @Transient
    private String descRamo;
    @Transient
    private String descProducto;
    @Transient
    private String descPolitica;
    @Transient
    private String descDefinicion;
    @Transient
    private String descRol;
    @Transient
    private String descCargoRol;
    @Transient
    private String descAplicaExcep;
}
