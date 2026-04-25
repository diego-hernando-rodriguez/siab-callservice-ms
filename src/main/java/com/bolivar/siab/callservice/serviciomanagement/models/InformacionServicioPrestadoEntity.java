package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for INFORMACION_SERVICIO_PRESTADO table in NASIST schema.
 * Service-specific characteristic information.
 */
@Entity
@Table(name = "INFORMACION_SERVICIO_PRESTADO", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(InformacionServicioPrestadoEntity.InfServicioId.class)
public class InformacionServicioPrestadoEntity {

    @Id
    @Column(name = "NUMERO_AUTORIZACION", nullable = false)
    private Long numeroAutorizacion;

    @Id
    @Column(name = "CODIGO_CAMPO", nullable = false)
    private Integer codigoCampo;

    @Column(name = "CLSERV_CODIGO")
    private Integer clservCodigo;

    @Column(name = "SERV_CODIGO")
    private Integer servCodigo;

    @Column(name = "VALOR", length = 4000)
    private String valor;

    @Column(name = "REQUERIDO", length = 1)
    private String requerido;

    @Transient
    private String dspCampo;

    @Transient
    private Integer totalRegistros;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfServicioId implements java.io.Serializable {
        private Long numeroAutorizacion;
        private Integer codigoCampo;
    }
}
