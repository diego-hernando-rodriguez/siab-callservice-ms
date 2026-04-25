package com.bolivar.siab.callservice.tarifa.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity representing zones (from FICHEROS query source) in NASIST schema.
 */
@Entity
@Table(name = "ZONAS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ZonasEntity {
    @Id @Column(name = "CODIGO", length = 50) private String codigo;
    @Column(name = "DESCRIPCION", length = 200) private String descripcion;
    @Column(name = "LOCGE_CODIGO") private Long locgeCodigo;
    @Column(name = "ESTADO", length = 5) private String estado;
}
