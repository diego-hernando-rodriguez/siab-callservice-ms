package com.bolivar.siab.callservice.caracteristicas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for DATOS_CARACTERISTICA_CAUSA table in NASIST schema.
 */
@Entity @Immutable
@Table(name = "DATOS_CARACTERISTICA_CAUSA", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(DatoCaracteristicaCausaEntity.DatoCaracteristicaCausaId.class)
public class DatoCaracteristicaCausaEntity {
    @Id @Column(name = "RAMO_CODIGO") private Integer ramoCodigo;
    @Id @Column(name = "PRODUCTO_CODIGO") private Integer productoCodigo;
    @Id @Column(name = "CAUSA_CODIGO") private Long causaCodigo;
    @Id @Column(name = "CODIGO_CAMPO") private Integer codigoCampo;
    @Column(name = "ESTADO", length = 5) private String estado;
    @Column(name = "REQUERIDO", length = 1) private String requerido;
    @Column(name = "ORDEN_APARICION") private Integer ordenAparicion;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class DatoCaracteristicaCausaId implements java.io.Serializable {
        private Integer ramoCodigo;
        private Integer productoCodigo;
        private Long causaCodigo;
        private Integer codigoCampo;
    }
}
