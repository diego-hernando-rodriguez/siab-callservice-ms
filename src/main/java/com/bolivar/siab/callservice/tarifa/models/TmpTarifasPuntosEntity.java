package com.bolivar.siab.callservice.tarifa.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * JPA Entity for TMP_TARIFAS_PUNTOS table in NASIST schema.
 * Temporary tariff/route selection points.
 */
@Entity
@Table(name = "TMP_TARIFAS_PUNTOS", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmpTarifasPuntosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "LLAMADA_NUMERO")
    private Long llamadaNumero;

    @Column(name = "NUMERO_AUTORIZACION")
    private Long numeroAutorizacion;

    @Column(name = "PUNTO_ORIGEN", length = 200)
    private String puntoOrigen;

    @Column(name = "PUNTO_DESTINO", length = 200)
    private String puntoDestino;

    @Column(name = "DISTANCIA_KM", precision = 10, scale = 2)
    private BigDecimal distanciaKm;

    @Column(name = "VALOR_TARIFA", precision = 18, scale = 2)
    private BigDecimal valorTarifa;

    @Column(name = "BANDERAZO", precision = 18, scale = 2)
    private BigDecimal banderazo;

    @Column(name = "VALOR_TOTAL", precision = 18, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "ESTADO", length = 5)
    private String estado;
}
