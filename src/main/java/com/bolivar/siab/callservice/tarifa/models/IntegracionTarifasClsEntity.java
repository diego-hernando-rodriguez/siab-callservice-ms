package com.bolivar.siab.callservice.tarifa.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity for INTEGRACION_TARIFAS_CLS table in NASIST schema.
 * ClickSoftware tariff integration records.
 */
@Entity
@Table(name = "INTEGRACION_TARIFAS_CLS", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntegracionTarifasClsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "LLAMADA_NUMERO")
    private Long llamadaNumero;

    @Column(name = "NUMERO_AUTORIZACION")
    private Long numeroAutorizacion;

    @Column(name = "VALOR_TARIFA", precision = 18, scale = 2)
    private BigDecimal valorTarifa;

    @Column(name = "VALOR_ADICIONAL", precision = 18, scale = 2)
    private BigDecimal valorAdicional;

    @Column(name = "VALOR_TOTAL", precision = 18, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "ESTADO", length = 5)
    private String estado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;
}
