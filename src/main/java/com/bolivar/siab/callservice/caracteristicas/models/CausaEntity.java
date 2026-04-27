package com.bolivar.siab.callservice.caracteristicas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for CAUSAS table in NASIST schema.
 */
@Entity @Immutable
@Table(name = "CAUSAS", schema = "NASIST")
@IdClass(CausaEntity.CausaId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CausaEntity {
    @Id @Column(name = "RAMO_CODIGO", length = 10) private String ramoCodigo;
    @Id @Column(name = "PRODUCTO_CODIGO", length = 10) private String productoCodigo;
    @Id @Column(name = "CODIGO") private Long causaCodigo;
    @Column(name = "DESCRIPCION", length = 200) private String descripcion;
    @Column(name = "ESTADO", length = 5) private String estado;
    @Column(name = "LINEA_NEGOCIO", length = 10) private String lineaNegocio;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CausaId implements java.io.Serializable {
        private String ramoCodigo;
        private String productoCodigo;
        private Long causaCodigo;
    }
}
