package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for CITAS table in NASIST schema.
 * Appointment management for services.
 */
@Entity
@Table(name = "CITAS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CitasEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "LLAMADA_NUMERO") private Long llamadaNumero;
    @Column(name = "NUMERO_AUTORIZACION") private Long numeroAutorizacion;
    @Column(name = "NUMERO_SINIESTRO", length = 30) private String numeroSiniestro;
    @Column(name = "ESTADO", length = 5) private String estado;
    @Column(name = "ESTADO_OLD", length = 5) private String estadoOld;
    @Column(name = "FECHA_ASIGNACION") private LocalDateTime fechaAsignacion;
    @Column(name = "HORA_INICIAL", length = 5) private String horaInicial;
    @Column(name = "USUARIO_ASIGNACION", length = 50) private String usuarioAsignacion;
    @Column(name = "FECHA_CAM_ESTADO") private LocalDateTime fechaCamEstado;
    @Column(name = "USUARIO_CAM_ESTADO", length = 50) private String usuarioCamEstado;
    @Column(name = "OBSERVACIONES", length = 4000) private String observaciones;
    @Column(name = "DIRECCION", length = 500) private String direccion;
    @Column(name = "TELEFONO", length = 30) private String telefono;
    @Column(name = "EXTENCION", length = 10) private String extencion;
    @Column(name = "BARRIO", length = 100) private String barrio;
    @Column(name = "TIPO_CLIENTE", length = 10) private String tipoCliente;
    @Column(name = "IDENTIFICACION_ASEGURADO", length = 30) private String identificacionAsegurado;
    @Column(name = "ASEGURADO", length = 200) private String asegurado;
    @Column(name = "CLIENTE", length = 200) private String cliente;
    @Column(name = "CAUSA", length = 200) private String causa;
    @Column(name = "PLACA", length = 20) private String placa;
    @Column(name = "PLACA_TERCERO", length = 20) private String placaTercero;
    @Column(name = "MARCA", length = 100) private String marca;
    @Column(name = "MODELO", length = 100) private String modelo;
    @Column(name = "AGEDIA_CONSECUTIVO") private Long agediaConsecutivo;
    @Column(name = "AGEDIA_TIPREA_CODIGO") private Integer agediaTipreaCodigo;
    @Transient private String descCiudad;
    @Transient private String descFecha;
    @Transient private String descHora;
    @Transient private String descReaccion;
    @Transient private String descTipoCliente;
}
