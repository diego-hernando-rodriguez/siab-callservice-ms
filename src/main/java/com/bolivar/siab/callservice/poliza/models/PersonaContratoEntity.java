package com.bolivar.siab.callservice.poliza.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for PERSONAS_CONTRATO table in NASIST schema.
 * Links persons to contracts with their role and identification.
 */
@Entity
@Table(name = "PERSONAS_CONTRATO", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaContratoEntity {

    @Id
    @Column(name = "NUMERO_ORDEN", nullable = false)
    private Long numeroOrden;

    @Column(name = "CONT_NUMERO", length = 30)
    private String contNumero;

    @Column(name = "PECO_NUMERO_ORDEN")
    private Long pecoNumeroOrden;

    @Column(name = "USU_NUMERO_DOCUMENTO", length = 30)
    private String usuNumeroDocumento;

    @Column(name = "USU_TIPO_DOCUMENTO", length = 5)
    private String usuTipoDocumento;

    @Column(name = "NOMBRE", length = 200)
    private String nombre;

    @Column(name = "RAMO_CODIGO")
    private Integer ramoCodigo;

    @Column(name = "PRODUCTO_CODIGO")
    private Integer productoCodigo;

    @Column(name = "TIPCONT_CODIGO")
    private Integer tipcontCodigo;

    @Column(name = "ROL", length = 10)
    private String rol;
}
