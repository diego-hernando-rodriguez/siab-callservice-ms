package com.bolivar.siab.callservice.configuracion.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for CG_CODE_CONTROLS table in NASIST schema.
 * Sequence generation control table.
 */
@Entity
@Table(name = "CG_CODE_CONTROLS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CgCodeControlsEntity {
    @Id @Column(name = "TABLE_NAME", length = 100) private String tableName;
    @Column(name = "LAST_SEQ") private Long lastSeq;
    @Column(name = "PREFIX", length = 10) private String prefix;
}
