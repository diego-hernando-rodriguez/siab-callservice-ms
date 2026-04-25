package com.bolivar.siab.callservice.configuracion.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for CG_REF_CODES table in NASIST schema.
 * Reference codes for domain lookups (TIPO_IDENTIFICACION, etc.).
 */
@Entity
@Immutable
@Table(name = "CG_REF_CODES", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(CgRefCodesEntity.CgRefCodesId.class)
public class CgRefCodesEntity {
    @Id @Column(name = "RV_DOMAIN", length = 100) private String rvDomain;
    @Id @Column(name = "RV_LOW_VALUE", length = 240) private String rvLowValue;
    @Column(name = "RV_HIGH_VALUE", length = 240) private String rvHighValue;
    @Column(name = "RV_ABBREVIATION", length = 240) private String rvAbbreviation;
    @Column(name = "RV_MEANING", length = 240) private String rvMeaning;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CgRefCodesId implements java.io.Serializable {
        private String rvDomain;
        private String rvLowValue;
    }
}
