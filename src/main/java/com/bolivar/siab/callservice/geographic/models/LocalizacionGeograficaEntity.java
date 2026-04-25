package com.bolivar.siab.callservice.geographic.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for LOCALIZACIONES_GEOGRAFICAS table in NASIST schema.
 * Read-only lookup for geographic locations (countries TLG=1, departments TLG=2, cities TLG=3).
 */
@Entity
@Immutable
@Table(name = "LOCALIZACIONES_GEOGRAFICAS", schema = "NASIST")
@IdClass(LocalizacionGeograficaEntity.LocalizacionId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LocalizacionGeograficaEntity {

    @Id @Column(name = "CODIGO") private Long codigo;
    @Id @Column(name = "TLG_CODIGO") private Integer tlgCodigo;
    @Column(name = "NOMBRE", length = 200) private String nombre;
    @Column(name = "LONGITUD") private Double longitud;
    @Column(name = "LATITUD") private Double latitud;
    @Column(name = "NOMBRE_GOOGLE", length = 200) private String nombreGoogle;
    @Column(name = "COORDENADA_GOOGLE", length = 500) private String coordenadaGoogle;
    @Column(name = "NOMBRE_MIN", length = 200) private String nombreMin;
    @Column(name = "CODIGO_DANE") private Long codigoDane;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class LocalizacionId implements java.io.Serializable {
        private Long codigo;
        private Integer tlgCodigo;
    }
}
