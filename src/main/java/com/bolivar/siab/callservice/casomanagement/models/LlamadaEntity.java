package com.bolivar.siab.callservice.casomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * JPA Entity for LLAMADAS table in NASIST schema.
 * Only maps columns that actually exist in the database table.
 * RAMO_CODIGO and PRODUCTO_CODIGO are VARCHAR2 in Oracle.
 * HORA_LLAMADA is NUMBER (minutes since midnight).
 * NUMERO_SINIESTRO is NUMBER.
 * OBSERVACIONES_LAR is LONG.
 */
@Entity
@Table(name = "LLAMADAS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LlamadaEntity {

    @Id
    @Column(name = "NUMERO", nullable = false)
    private Long numero;

    @Column(name = "FECHA_LLAMADA")
    private LocalDateTime fechaLlamada;

    @Column(name = "TIPCONT_CODIGO")
    private Integer tipcontCodigo;

    @Column(name = "CONT_FECHA_INICIO_VIGENCIA")
    private LocalDateTime contFechaInicioVigencia;

    @Column(name = "CONT_NUMERO_CONTRATO", length = 20)
    private String contNumeroContrato;

    @Column(name = "PECO_NUMERO_ORDEN")
    private Long pecoNumeroOrden;

    @Column(name = "CODIGO_CAMPO")
    private Integer codigoCampo;

    @Column(name = "RIESGO_CODIGO", length = 4)
    private String riesgoCodigo;

    @Column(name = "CAUSA_CODIGO")
    private Long causaCodigo;

    @Column(name = "RAMO_CODIGO", length = 4)
    private String ramoCodigo;

    @Column(name = "PRODUCTO_CODIGO", length = 5)
    private String productoCodigo;

    @Column(name = "LOCGE_CODIGO")
    private Long locgeCodigo;

    @Column(name = "TLG_CODIGO")
    private Integer tlgCodigo;

    @Column(name = "RUTA_CODIGO", length = 10)
    private String rutaCodigo;

    @Column(name = "ESTADO_LLAMADA", length = 1)
    private String estadoLlamada;

    @Column(name = "VALOR_ATENCION")
    private BigDecimal valorAtencion;

    @Column(name = "TELEFONO_LLAMADA", length = 60)
    private String telefonoLlamada;

    @Column(name = "HORA_LLAMADA")
    private Integer horaLlamada;

    @Column(name = "NUMERO_SINIESTRO")
    private Long numeroSiniestro;

    @Column(name = "OBSERVACIONES", length = 1024)
    private String observaciones;

    @Column(name = "PAGO_USUARIO")
    private BigDecimal pagoUsuario;

    @Column(name = "PAGO_OTRO")
    private BigDecimal pagoOtro;

    @Column(name = "PAGO_ASISTENCIA")
    private BigDecimal pagoAsistencia;

    @Column(name = "SERVICIO_ORIGINAL")
    private Long servicioOriginal;

    @Column(name = "OPERADOR", length = 20)
    private String operador;

    @Column(name = "FECHA_HORA_LLAMADA")
    private LocalDateTime fechaHoraLlamada;

    @Column(name = "ESTADO_POLIZA", length = 2)
    private String estadoPoliza;

    @Column(name = "CONT_FECHA_FIN_VIGENCIA")
    private LocalDate contFechaFinVigencia;

    @Column(name = "TRONADOR", length = 1)
    private String tronador;

    @Column(name = "POLIZA", length = 20)
    private String poliza;

    @Column(name = "PLACA_RIESGO", length = 80)
    private String placaRiesgo;

    @Column(name = "ALTO_VALOR", length = 200)
    private String altoValor;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @Column(name = "DIRECCION_GEO_REFERENCIA", length = 200)
    private String direccionGeoReferencia;

    @Column(name = "ACUERDO_CLIENTE", length = 50)
    private String acuerdoCliente;

    @Column(name = "MCA_ENVIO_CLICKSOFTWARE", length = 1)
    private String mcaEnvioClicksoftware;

    @Column(name = "MCA_ENVIO_SALESFORCE", length = 1)
    private String mcaEnvioSalesforce;

    @Column(name = "GESTION_CASO_CLICKSOFTWARE", length = 1)
    private String gestionCasoClicksoftware;

    @Column(name = "GESTION_CASO_SALESFORCE", length = 1)
    private String gestionCasoSalesforce;

    @Column(name = "ORIGEN", length = 20)
    private String origen;

    @Column(name = "SEVERIDAD", length = 2)
    private String severidad;

    @Column(name = "LINEA_NEGOCIO", length = 20)
    private String lineaNegocio;

    @Column(name = "DIRECCION_COMPLEMENTO", length = 200)
    private String direccionComplemento;

    @Column(name = "DIRECCION_DESTINO", length = 200)
    private String direccionDestino;

    @Column(name = "ENVIO_CORREO_SINIESTRO", length = 1)
    private String envioCorreoSiniestro;

    @Column(name = "TIENE_APP_BOLIVAR", length = 1)
    private String tieneAppBolivar;

    @Column(name = "LINK_DESCARGA", length = 3)
    private String linkDescarga;

    @Column(name = "NUMERO_COTIZACION", length = 15)
    private String numeroCotizacion;

    @Column(name = "OT_SAMM", length = 10)
    private String otSamm;

    @Column(name = "RECLASIFICA_RAMO_CODIGO", length = 4)
    private String reclasificaRamoCodigo;

    @Column(name = "RECLASIFICA_PRODUCTO_CODIGO", length = 5)
    private String reclasificaProductoCodigo;

    @Column(name = "RECLASIFICA_CAUSA_CODIGO")
    private Long reclasificaCausaCodigo;

    @Column(name = "COD_RAZON_RECLASIFICA")
    private Long codRazonReclasifica;

    // OBSERVACIONES_LAR is Oracle LONG type. Do NOT use @Lob (causes read errors).
    @Column(name = "OBSERVACIONES_LAR")
    private String observacionesLar;
}
