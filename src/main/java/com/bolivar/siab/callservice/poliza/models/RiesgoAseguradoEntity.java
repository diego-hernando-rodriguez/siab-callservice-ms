package com.bolivar.siab.callservice.poliza.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for RIESGOS_ASEGURADOS table in NASIST schema.
 * Represents insured risks/assets linked to policies.
 */
@Entity
@Table(name = "RIESGOS_ASEGURADOS", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RiesgoAseguradoEntity.RiesgoAseguradoId.class)
public class RiesgoAseguradoEntity {

    @Id
    @Column(name = "CONT_NUMERO", length = 30, nullable = false)
    private String contNumero;

    @Id
    @Column(name = "RIESGO_CODIGO", length = 20, nullable = false)
    private String riesgoCodigo;

    @Column(name = "CODIGO_CAMPO")
    private Integer codigoCampo;

    @Column(name = "VALOR", length = 200)
    private String valor;

    @Column(name = "VALOR_SIN_CEROS", length = 200)
    private String valorSinCeros;

    @Column(name = "RAMO_CODIGO")
    private Integer ramoCodigo;

    @Column(name = "PRODUCTO_CODIGO")
    private Integer productoCodigo;

    @Column(name = "TIPCONT_CODIGO")
    private Integer tipcontCodigo;

    @Column(name = "PECO_NUMERO_ORDEN")
    private Long pecoNumeroOrden;

    @Column(name = "ESTADO", length = 5)
    private String estado;

    @Column(name = "NUMERO_ORDEN")
    private Long numeroOrden;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RiesgoAseguradoId implements java.io.Serializable {
        private String contNumero;
        private String riesgoCodigo;
    }
}
