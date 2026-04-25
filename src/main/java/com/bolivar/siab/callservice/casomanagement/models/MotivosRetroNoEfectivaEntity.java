package com.bolivar.siab.callservice.casomanagement.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for MOTIVOS_RETRO_NO_EFECTIVA table in NASIST schema.
 */
@Entity
@Table(name = "MOTIVOS_RETRO_NO_EFECTIVA", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MotivosRetroNoEfectivaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "LLAMADA_NUMERO") private Long llamadaNumero;
    @Column(name = "MOTIVO_CODIGO", length = 20) private String motivoCodigo;
    @Column(name = "DESCRIPCION", length = 500) private String descripcion;
    @Column(name = "ESTADO", length = 5) private String estado;
}
