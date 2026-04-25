package com.bolivar.siab.callservice.casomanagement.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for INFORMACION_MERCADEO table in NASIST schema.
 * User information/marketing data.
 */
@Entity
@Table(name = "INFORMACION_MERCADEO", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(InformacionUsuariosEntity.InfoUsuarioId.class)
public class InformacionUsuariosEntity {

    @Id
    @Column(name = "USU_NUMERO_DOCUMENTO", length = 30, nullable = false)
    private String usuNumeroDocumento;

    @Id
    @Column(name = "USU_TIPO_DOCUMENTO", length = 5, nullable = false)
    private String usuTipoDocumento;

    @Id
    @Column(name = "CODIGO_CAMPO", nullable = false)
    private Integer codigoCampo;

    @Column(name = "VALOR", length = 4000)
    private String valor;

    @Column(name = "TIPO_DATO", length = 20)
    private String tipoDato;

    @Column(name = "LONGITUD_CAMPO")
    private Integer longitudCampo;

    @Column(name = "CAMPO_CARACTERISTICA", length = 200)
    private String campoCaracteristica;

    @Transient
    private String dspCampo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoUsuarioId implements java.io.Serializable {
        private String usuNumeroDocumento;
        private String usuTipoDocumento;
        private Integer codigoCampo;
    }
}
