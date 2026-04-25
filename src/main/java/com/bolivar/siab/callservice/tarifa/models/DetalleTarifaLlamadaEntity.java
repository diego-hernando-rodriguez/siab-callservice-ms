package com.bolivar.siab.callservice.tarifa.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity for DETALLE_TARIFA_LLAMADA table in NASIST schema.
 */
@Entity
@Table(name = "DETALLE_TARIFA_LLAMADA", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleTarifaLlamadaEntity {

    @Id
    @Column(name = "CONSECUTIVO", nullable = false)
    private Long consecutivo;

    @Column(name = "TARIFA_CODIGO")
    private Long tarifaCodigo;

    @Column(name = "LLAMADA_NUMERO")
    private Long llamadaNumero;

    @Column(name = "SERV_CODIGO")
    private Integer servCodigo;

    @Column(name = "CLSERV_CODIGO")
    private Integer clservCodigo;

    @Column(name = "VALOR_MEDIDA", precision = 18, scale = 2)
    private BigDecimal valorMedida;

    @Column(name = "ANCHO", precision = 10, scale = 2)
    private BigDecimal ancho;

    @Column(name = "ALTO", precision = 10, scale = 2)
    private BigDecimal alto;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "TOTAL_DETALLE_TAR", precision = 18, scale = 2)
    private BigDecimal totalDetalleTar;

    @Column(name = "FECHA_SERVICIO")
    private LocalDateTime fechaServicio;

    @Column(name = "HORA_SERVICIO", length = 5)
    private String horaServicio;

    @Transient
    private String dspTarifaDetalle;
}
