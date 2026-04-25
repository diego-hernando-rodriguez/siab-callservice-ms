package com.bolivar.siab.callservice.geographic.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for LOCALIDADES_GEOGRAFICAS table in NASIST schema.
 * Read-only lookup for geographic locations (cities, departments, countries).
 */
@Entity
@Immutable
@Table(name = "LOCALIDADES_GEOGRAFICAS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LocalizacionGeograficaEntity {
    @Id @Column(name = "LOCGE_CODIGO") private Long locgeCodigo;
    @Column(name = "TLG_CODIGO") private Integer tlgCodigo;
    @Column(name = "NOMBRE", length = 200) private String nombre;
    @Column(name = "LOCGE_CODIGO_PADRE") private Long locgeCodigoPadre;
    @Column(name = "PAIS", length = 10) private String pais;
    @Column(name = "DEPARTAMENTO", length = 100) private String departamento;
    @Column(name = "LATITUD", length = 30) private String latitud;
    @Column(name = "LONGITUD", length = 30) private String longitud;
    @Column(name = "ESTADO", length = 5) private String estado;
}
