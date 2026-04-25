package com.bolivar.siab.callservice.casomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for LOG_CAMBIO_CAUSA table in NASIST schema.
 * Tracks cause reclassification history.
 */
@Entity
@Table(name = "LOG_CAMBIO_CAUSA", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LogCambioCausaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "NUMERO_LLAMADA") private Long numeroLlamada;
    @Column(name = "RAMO_ANTERIOR") private Integer ramoAnterior;
    @Column(name = "PRODUCTO_ANTERIOR") private Integer productoAnterior;
    @Column(name = "CAUSA_ANTERIOR") private Long causaAnterior;
    @Column(name = "RAMO_NUEVO") private Integer ramoNuevo;
    @Column(name = "PRODUCTO_NUEVO") private Integer productoNuevo;
    @Column(name = "CAUSA_NUEVA") private Long causaNueva;
    @Column(name = "COD_RAZON", length = 20) private String codRazon;
    @Column(name = "DESCRIPCION_RAZON", length = 500) private String descripcionRazon;
    @Column(name = "FECHA_CAMBIO") private LocalDateTime fechaCambio;
    @Column(name = "USUARIO_CAMBIO", length = 50) private String usuarioCambio;
}
