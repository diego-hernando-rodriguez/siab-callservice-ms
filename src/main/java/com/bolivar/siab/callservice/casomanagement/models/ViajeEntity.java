package com.bolivar.siab.callservice.casomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * JPA Entity for PLANES_VIAJE table in NASIST schema.
 * Travel plan records for cases.
 */
@Entity
@Table(name = "PLANES_VIAJE", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "LLAMADA_NUMERO")
    private Long llamadaNumero;

    @Column(name = "NUMERO_AUTORIZACION")
    private Long numeroAutorizacion;

    @Column(name = "FECHA_SALIDA")
    private LocalDate fechaSalida;

    @Column(name = "FECHA_REGRESO")
    private LocalDate fechaRegreso;

    @Column(name = "CIUDAD_ORIGEN", length = 100)
    private String ciudadOrigen;

    @Column(name = "CIUDAD_DESTINO", length = 100)
    private String ciudadDestino;

    @Column(name = "PAIS_DESTINO", length = 50)
    private String paisDestino;

    @Column(name = "NUMERO_VIAJEROS")
    private Integer numeroViajeros;

    @Column(name = "MOTIVO_VIAJE", length = 200)
    private String motivoViaje;

    @Column(name = "OBSERVACIONES", length = 4000)
    private String observaciones;

    @Column(name = "ESTADO", length = 5)
    private String estado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "USUARIO_CREACION", length = 50)
    private String usuarioCreacion;

    @Column(name = "CONT_NUMERO", length = 30)
    private String contNumero;
}
