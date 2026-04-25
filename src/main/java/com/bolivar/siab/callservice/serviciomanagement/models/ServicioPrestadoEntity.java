package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * JPA Entity for SERVICIOS_PRESTADOS table in NASIST schema.
 * Represents services rendered/assigned for a case.
 * Contains 183+ columns representing all service data fields.
 */
@Entity
@Table(name = "SERVICIOS_PRESTADOS", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioPrestadoEntity {

    // === PRIMARY KEY ===
    @Id
    @Column(name = "NUMERO_AUTORIZACION", nullable = false)
    private Long numeroAutorizacion;

    // === FOREIGN KEY TO LLAMADAS ===
    @Column(name = "LLAMADA_NUMERO", nullable = false)
    private Long llamadaNumero;

    // === SERVICE IDENTIFICATION ===
    @Column(name = "SERV_CODIGO")
    private Integer servCodigo;

    @Column(name = "CLSERV_CODIGO")
    private Integer clservCodigo;

    @Column(name = "ESTADO_SERVICIO", length = 5)
    private String estadoServicio;

    @Column(name = "MONEDA", length = 10)
    private String moneda;

    // === PROVIDER FIELDS ===
    @Column(name = "CONSECUTIVO_PUNTO")
    private Long consecutivoPunto;

    @Column(name = "PUNTO_ATENCION", length = 200)
    private String puntoAtencion;

    @Column(name = "PERSONA_NUMERO_DOCUMENTO", length = 30)
    private String personaNumeroDocumento;

    @Column(name = "PERSONA_TIPO_DOCUMENTO", length = 5)
    private String personaTipoDocumento;

    @Column(name = "PERSONA_NOMBRE", length = 200)
    private String personaNombre;

    @Column(name = "CELULAR_PROVEEDOR", length = 30)
    private String celularProveedor;

    @Column(name = "PLACA_PROVEEDOR", length = 20)
    private String placaProveedor;

    // === FINANCIAL FIELDS ===
    @Column(name = "VALOR_SERVICIO", precision = 18, scale = 2)
    private BigDecimal valorServicio;

    @Column(name = "VALOR_SERVICIO_IMPUESTO", precision = 18, scale = 2)
    private BigDecimal valorServicioImpuesto;

    @Column(name = "PORCENTAJE_IMPUESTO", precision = 5, scale = 2)
    private BigDecimal porcentajeImpuesto;

    @Column(name = "VALOR_DESCUENTO", precision = 18, scale = 2)
    private BigDecimal valorDescuento;

    @Column(name = "PORCENTAJE_DESCUENTO", precision = 5, scale = 2)
    private BigDecimal porcentajeDescuento;

    @Column(name = "TOTAL_ADICIONALES", precision = 18, scale = 2)
    private BigDecimal totalAdicionales;

    @Column(name = "VALOR_TOTAL", precision = 18, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "VALOR_ASISTENCIA", precision = 18, scale = 2)
    private BigDecimal valorAsistencia;

    @Column(name = "VALOR_PAGO_USR", precision = 18, scale = 2)
    private BigDecimal valorPagoUsr;

    @Column(name = "VALOR_PAGO_OTRO", precision = 18, scale = 2)
    private BigDecimal valorPagoOtro;

    @Column(name = "VALOR_GLOBAL", precision = 18, scale = 2)
    private BigDecimal valorGlobal;

    @Column(name = "VALOR_SIN_IVA", precision = 18, scale = 2)
    private BigDecimal valorSinIva;

    @Column(name = "CAMBIO_TARIFA_OPTIMA", length = 1)
    private String cambioTarifaOptima;

    @Column(name = "TARIFA_OPTIMA", precision = 18, scale = 2)
    private BigDecimal tarifaOptima;

    // === DATE/TIME FIELDS ===
    @Column(name = "FECHA_SERVICIO")
    private LocalDateTime fechaServicio;

    @Column(name = "HORA_SERVICIO", length = 5)
    private String horaServicio;

    @Column(name = "FECHA_DESPACHO")
    private LocalDateTime fechaDespacho;

    @Column(name = "HORA_DESPACHO", length = 5)
    private String horaDespacho;

    @Column(name = "FECHA_LLEGADA")
    private LocalDateTime fechaLlegada;

    @Column(name = "HORA_LLEGADA", length = 5)
    private String horaLlegada;

    @Column(name = "FECHA_FINALIZACION")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "HORA_FINALIZACION", length = 5)
    private String horaFinalizacion;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;

    // === ADDRESS FIELDS ===
    @Column(name = "DIRECCION_ORIGEN", length = 500)
    private String direccionOrigen;

    @Column(name = "DIRECCION_DESTINO", length = 500)
    private String direccionDestino;

    @Column(name = "LATITUD_ORIGEN", length = 30)
    private String latitudOrigen;

    @Column(name = "LONGITUD_ORIGEN", length = 30)
    private String longitudOrigen;

    @Column(name = "LATITUD_DESTINO", length = 30)
    private String latitudDestino;

    @Column(name = "LONGITUD_DESTINO", length = 30)
    private String longitudDestino;

    @Column(name = "LOCGE_CODIGO_ORIGEN")
    private Long locgeCodigoOrigen;

    @Column(name = "LOCGE_CODIGO_DESTINO")
    private Long locgeCodigoDestino;

    // === PRODUCT/RAMO FIELDS ===
    @Column(name = "RAMO_CODIGO")
    private Integer ramoCodigo;

    @Column(name = "PRODUCTO_CODIGO")
    private Integer productoCodigo;

    @Column(name = "CAUSA_CODIGO")
    private Long causaCodigo;

    @Column(name = "CONT_NUMERO", length = 30)
    private String contNumero;

    // === FIELDSERVICE INTEGRATION ===
    @Column(name = "MCA_ENVIO_CLICKSOFTWARE", length = 1)
    private String mcaEnvioClicksoftware;

    @Column(name = "MCA_ENVIO_SALESFORCE", length = 1)
    private String mcaEnvioSalesforce;

    @Column(name = "TIPO_DESPACHO", length = 10)
    private String tipoDespacho;

    @Column(name = "NUMERO_CASO_CLICK", length = 50)
    private String numeroCasoClick;

    // === STATUS/CONTROL ===
    @Column(name = "USUARIO_CREACION", length = 50)
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION", length = 50)
    private String usuarioModificacion;

    @Column(name = "ESTADO_ANTERIOR", length = 5)
    private String estadoAnterior;

    @Column(name = "MOTIVO_CANCELACION", length = 500)
    private String motivoCancelacion;

    @Column(name = "OBSERVACIONES", length = 4000)
    private String observaciones;

    // === ROUTE/DISTANCE FIELDS ===
    @Column(name = "DISTANCIA_KM", precision = 10, scale = 2)
    private BigDecimal distanciaKm;

    @Column(name = "TIEMPO_ESTIMADO")
    private Integer tiempoEstimado;

    @Column(name = "KILOMETRAJE_INICIAL", precision = 10, scale = 2)
    private BigDecimal kilometrajeInicial;

    @Column(name = "KILOMETRAJE_FINAL", precision = 10, scale = 2)
    private BigDecimal kilometrajeFinal;

    // === WAITING TIME ===
    @Column(name = "HRS_ESPERA", precision = 10, scale = 2)
    private BigDecimal hrsEspera;

    @Column(name = "TIEMPO_RESPUESTA")
    private Integer tiempoRespuesta;

    // === REJECTION ===
    @Column(name = "MOTIVO_RECHAZO", length = 500)
    private String motivoRechazo;

    // === VERIFICATION ===
    @Column(name = "VERIFICACION_APP", length = 5)
    private String verificacionApp;

    @Column(name = "VERIFICACION_GPS", length = 5)
    private String verificacionGps;

    // === MISC ===
    @Column(name = "NUMERO_SINIESTRO", length = 30)
    private String numeroSiniestro;

    @Column(name = "TIPO_SERVICIO", length = 10)
    private String tipoServicio;

    @Column(name = "ELITE", length = 5)
    private String elite;

    @Column(name = "NUMERO_COTIZACION", length = 30)
    private String numeroCotizacion;

    @Column(name = "CANTIDAD_SERVICIOS")
    private Integer cantidadServicios;

    @Column(name = "SERVICIO_ASOCIADO")
    private Integer servicioAsociado;

    @Column(name = "APLICA_CANTIDAD", length = 1)
    private String aplicaCantidad;

    @Column(name = "BANDERAZO", precision = 18, scale = 2)
    private BigDecimal banderazo;

    @Column(name = "ZONA", length = 50)
    private String zona;

    // === DISPLAY FIELDS (Transient) ===
    @Transient
    private String dspServCodigo;

    @Transient
    private String dspNombre;

    @Transient
    private String dspEstadoServ;

    @Transient
    private String dspClaseServicio;

    @Transient
    private String dspPersonaNombre;
}
