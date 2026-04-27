package com.bolivar.siab.callservice.casomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * JPA Entity for LLAMADA_EXCEPCION table in NASIST schema.
 * Table has no single PK — uses composite key (NUMERO_LLAMADA, CODIGO_POLITICA, CODIGO_DEFINICION).
 */
@Entity
@Table(name = "LLAMADA_EXCEPCION", schema = "NASIST")
@IdClass(LlamadaExcepcionEntity.LlamadaExcepcionId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LlamadaExcepcionEntity {

    @Id
    @Column(name = "NUMERO_LLAMADA")
    private Long numeroLlamada;

    @Id
    @Column(name = "CODIGO_POLITICA")
    private Integer codigoPolitica;

    @Id
    @Column(name = "CODIGO_DEFINICION")
    private Integer codigoDefinicion;

    @Column(name = "NUMERO_SINIESTRO")
    private Long numeroSiniestro;

    @Column(name = "CODIGO_RAMO", length = 4)
    private String codigoRamo;

    @Column(name = "CODIGO_PRODUCTO", length = 5)
    private String codigoProducto;

    @Column(name = "CODIGO_ROL")
    private Integer codigoRol;

    @Column(name = "TIPO_IDENTIFICACION", length = 5)
    private String tipoIdentificacion;

    @Column(name = "NUMERO_IDENTIFICACION")
    private Long numeroIdentificacion;

    @Column(name = "AUTORIZADOR", length = 50)
    private String autorizador;

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

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class LlamadaExcepcionId implements Serializable {
        private Long numeroLlamada;
        private Integer codigoPolitica;
        private Integer codigoDefinicion;
    }
}