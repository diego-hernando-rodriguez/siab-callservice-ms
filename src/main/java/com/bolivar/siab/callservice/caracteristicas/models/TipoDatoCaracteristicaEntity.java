package com.bolivar.siab.callservice.caracteristicas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

/**
 * JPA Entity for TIPOS_DATO_CARACTERISTICA table in NASIST schema.
 * Defines characteristic field metadata (type, name, LOV, etc.).
 */
@Entity @Immutable
@Table(name = "TIPOS_DATO_CARACTERISTICA", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoDatoCaracteristicaEntity {
    @Id @Column(name = "CODIGO_CAMPO") private Integer codigoCampo;
    @Column(name = "DESCRIPCION", length = 200) private String descripcion;
    @Column(name = "TIPO_DATO", length = 20) private String tipoDato;
    @Column(name = "LONGITUD") private Integer longitud;
    @Column(name = "LISTA_VALORES", length = 200) private String listaValores;
    @Column(name = "ESTADO", length = 5) private String estado;
    @Column(name = "CAMPO_NO_MODIFICABLE", length = 1) private String campoNoModificable;
}
