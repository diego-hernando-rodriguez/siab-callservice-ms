package com.bolivar.siab.callservice.caracteristicas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for CAUSAS table in NASIST schema.
 */
@Entity @Immutable
@Table(name = "CAUSAS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CausaEntity {
    @Id @Column(name = "CAUSA_CODIGO") private Long causaCodigo;
    @Column(name = "DESCRIPCION", length = 200) private String descripcion;
    @Column(name = "RAMO_CODIGO") private Integer ramoCodigo;
    @Column(name = "PRODUCTO_CODIGO") private Integer productoCodigo;
    @Column(name = "ESTADO", length = 5) private String estado;
}
