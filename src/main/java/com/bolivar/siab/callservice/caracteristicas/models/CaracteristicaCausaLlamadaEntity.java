package com.bolivar.siab.callservice.caracteristicas.models;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

/**
 * JPA Entity for CARACTERISTICAS_CAUSA_LLAMADA table in NASIST schema.
 * Stores cause-related characteristics for each case.
 * Uses composite primary key (LLAMADA_NUMERO + CODIGO_CAMPO).
 */
@Entity
@Table(name = "CARACTERISTICAS_CAUSA_LLAMADA", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(CaracteristicaCausaLlamadaEntity.CaracteristicaCausaLlamadaId.class)
public class CaracteristicaCausaLlamadaEntity {

    @Id
    @Column(name = "LLAMADA_NUMERO", nullable = false)
    private Long llamadaNumero;

    @Id
    @Column(name = "CODIGO_CAMPO", nullable = false)
    private Integer codigoCampo;

    @Column(name = "RAMO_CODIGO")
    private Integer ramoCodigo;

    @Column(name = "PRODUCTO_CODIGO")
    private Integer productoCodigo;

    @Column(name = "CAUSA_CODIGO")
    private Long causaCodigo;

    @Column(name = "VALOR", length = 4000)
    private String valor;

    @Column(name = "CODIGO_CAMPO_PADRE")
    private Integer codigoCampoPadre;

    @Column(name = "REGISTRO_CAMBIO", length = 1)
    private String registroCambio;

    @Column(name = "CAMPO_NO_MODIFICABLE", length = 1)
    private String campoNoModificable;

    // === TRANSIENT / DISPLAY FIELDS from JOIN with TIPOS_DATO_CARACTERISTICA ===
    @Transient
    private String dspCampo;

    @Transient
    private String dspTipoDato;

    @Transient
    private String listaValores;

    @Transient
    private String requerido;

    @Transient
    private Integer totalRegistros;

    /**
     * Composite primary key class for CARACTERISTICAS_CAUSA_LLAMADA.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CaracteristicaCausaLlamadaId implements Serializable {
        private Long llamadaNumero;
        private Integer codigoCampo;
    }
}
