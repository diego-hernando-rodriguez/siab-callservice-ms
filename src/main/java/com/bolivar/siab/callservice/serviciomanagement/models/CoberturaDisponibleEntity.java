package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for COBERTURA_DISPONIBLE table in NASIST schema.
 */
@Entity
@Table(name = "COBERTURA_DISPONIBLE", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CoberturaDisponibleEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "LLAMADA_NUMERO") private Long llamadaNumero;
    @Column(name = "CONT_NUMERO", length = 30) private String contNumero;
    @Column(name = "RAMO_CODIGO") private Integer ramoCodigo;
    @Column(name = "PRODUCTO_CODIGO") private Integer productoCodigo;
    @Column(name = "COBERTURA", length = 100) private String cobertura;
    @Column(name = "ESTADO", length = 5) private String estado;
    @Column(name = "FECHA_CREACION") private LocalDateTime fechaCreacion;
}
