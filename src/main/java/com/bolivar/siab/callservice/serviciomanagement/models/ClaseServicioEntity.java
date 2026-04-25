package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for CLASES_SERVICIO table in NASIST schema.
 */
@Entity @Immutable
@Table(name = "CLASES_SERVICIO", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(ClaseServicioEntity.ClaseServicioId.class)
public class ClaseServicioEntity {
    @Id @Column(name = "CLSERV_CODIGO") private Integer clservCodigo;
    @Id @Column(name = "SERV_CODIGO") private Integer servCodigo;
    @Column(name = "DESCRIPCION", length = 200) private String descripcion;
    @Column(name = "ESTADO", length = 5) private String estado;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class ClaseServicioId implements java.io.Serializable {
        private Integer clservCodigo;
        private Integer servCodigo;
    }
}
