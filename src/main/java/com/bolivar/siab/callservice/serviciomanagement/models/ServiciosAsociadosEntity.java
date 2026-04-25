package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for SERVICIOS_ASOCIADOS table in NASIST schema.
 */
@Entity @Immutable
@Table(name = "SERVICIOS_ASOCIADOS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiciosAsociadosEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "RAMO_CODIGO") private Integer ramoCodigo;
    @Column(name = "PRODUCTO_CODIGO") private Integer productoCodigo;
    @Column(name = "CAUSA_CODIGO") private Long causaCodigo;
    @Column(name = "SERV_CODIGO") private Integer servCodigo;
    @Column(name = "CLSERV_CODIGO") private Integer clservCodigo;
    @Column(name = "ESTADO", length = 5) private String estado;
}
