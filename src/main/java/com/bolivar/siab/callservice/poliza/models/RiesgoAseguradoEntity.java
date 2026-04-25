package com.bolivar.siab.callservice.poliza.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * JPA Entity for RIESGOS_ASEGURADOS table in NASIST schema.
 * Represents insured risks/assets linked to policies.
 */
@Entity
@Table(name = "RIESGOS_ASEGURADOS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(RiesgoAseguradoEntity.RiesgoAseguradoId.class)
public class RiesgoAseguradoEntity {

    @Id @Column(name = "RAMO_CODIGO", length = 10) private String ramoCodigo;
    @Id @Column(name = "PRODUCTO_CODIGO", length = 10) private String productoCodigo;
    @Id @Column(name = "CODIGO_CAMPO") private Integer codigoCampo;
    @Id @Column(name = "RIESGO_CODIGO", length = 20) private String riesgoCodigo;
    @Id @Column(name = "CONT_TIPO_CONTRATO") private Integer contTipoContrato;
    @Id @Column(name = "CONT_NUMERO_CONTRATO", length = 30) private String contNumeroContrato;
    @Id @Column(name = "CONT_FECHA_INICIO_VIGENCIA") private LocalDate contFechaInicioVigencia;
    @Id @Column(name = "PECO_NUMERO_ORDEN") private Long pecoNumeroOrden;

    @Column(name = "PECO_TIPO_USUARIO", length = 5) private String pecoTipoUsuario;
    @Column(name = "VALOR", length = 200) private String valor;
    @Column(name = "EJECAR_CODIGO") private Integer ejecarCodigo;
    @Column(name = "TIPCAR_CODIGO") private Integer tipcarCodigo;
    @Column(name = "VALOR_SIN_CEROS", length = 200) private String valorSinCeros;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class RiesgoAseguradoId implements java.io.Serializable {
        private String ramoCodigo;
        private String productoCodigo;
        private Integer codigoCampo;
        private String riesgoCodigo;
        private Integer contTipoContrato;
        private String contNumeroContrato;
        private LocalDate contFechaInicioVigencia;
        private Long pecoNumeroOrden;
    }
}
