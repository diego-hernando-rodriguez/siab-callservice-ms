package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for SERVICIOS table in NASIST schema.
 */
@Entity @Immutable
@Table(name = "SERVICIOS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServicioEntity {
    @Id @Column(name = "SERV_CODIGO") private Integer servCodigo;
    @Column(name = "DESCRIPCION", length = 200) private String descripcion;
    @Column(name = "ESTADO", length = 5) private String estado;
    @Column(name = "TIPO", length = 10) private String tipo;
}
