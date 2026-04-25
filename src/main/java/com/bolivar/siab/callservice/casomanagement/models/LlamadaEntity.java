package com.bolivar.siab.callservice.casomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * JPA Entity for LLAMADAS table in NASIST schema.
 * Central table for case management in the SIAB system.
 * Contains 157+ columns representing all case data fields.
 */
@Entity
@Table(name = "LLAMADAS", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LlamadaEntity {

    // === PRIMARY KEY ===
    @Id
    @Column(name = "NUMERO", nullable = false)
    private Long numero;

    // === CORE CASE FIELDS ===
    @Column(name = "CONT_NUMERO_CONTRATO", length = 30)
    private String contNumeroContrato;

    @Column(name = "RIESGO_CODIGO", length = 20)
    private String riesgoCodigo;

    @Column(name = "LOCGE_CODIGO")
    private Long locgeCodigo;

    @Column(name = "TLG_CODIGO")
    private Integer tlgCodigo;

    @Column(name = "CAUSA_CODIGO")
    private Long causaCodigo;

    @Column(name = "DIRECCION", length = 500)
    private String direccion;

    @Column(name = "OBSERVACIONES_LAR", length = 4000)
    private String observacionesLar;

    @Column(name = "NUMERO_SINIESTRO", length = 30)
    private String numeroSiniestro;

    // === FIELD SERVICE FLAGS ===
    @Column(name = "MCA_ENVIO_CLICKSOFTWARE", length = 1)
    private String mcaEnvioClicksoftware;

    @Column(name = "MCA_ENVIO_SALESFORCE", length = 1)
    private String mcaEnvioSalesforce;

    @Column(name = "GESTION_CASO_CLICKSOFTWARE", length = 1)
    private String gestionCasoClicksoftware;

    // === FINANCIAL ===
    @Column(name = "VALOR_ATENCION", precision = 18, scale = 2)
    private BigDecimal valorAtencion;

    @Column(name = "PAGO_ASISTENCIA", precision = 18, scale = 2)
    private BigDecimal pagoAsistencia;

    @Column(name = "PAGO_USUARIO", precision = 18, scale = 2)
    private BigDecimal pagoUsuario;

    @Column(name = "PAGO_OTRO", precision = 18, scale = 2)
    private BigDecimal pagoOtro;

    // === DATE/TIME FIELDS ===
    @Column(name = "FECHA_LLAMADA")
    private LocalDateTime fechaLlamada;

    @Column(name = "HORA_LLAMADA", length = 5)
    private String horaLlamada;

    @Column(name = "FECHA_RETROLLAMADA")
    private LocalDateTime fechaRetrollamada;

    @Column(name = "HORA_RETROLLAMADA", length = 5)
    private String horaRetrollamada;

    @Column(name = "FECHA_INICIO_VIG")
    private LocalDate fechaInicioVig;

    @Column(name = "FECHA_FIN_VIG")
    private LocalDate fechaFinVig;

    @Column(name = "FECHA_VIGENCIA_POLIZA")
    private LocalDate fechaVigenciaPoliza;

    @Column(name = "CONT_FECHA_INICIO_VIGENCIA")
    private LocalDate contFechaInicioVigencia;

    @Column(name = "CONT_FECHA_FIN_VIGENCIA")
    private LocalDate contFechaFinVigencia;

    // === USER/DOCUMENT FIELDS ===
    @Column(name = "USU_NUMERO_DOCUMENTO", length = 30)
    private String usuNumeroDocumento;

    @Column(name = "USU_TIPO_DOCUMENTO", length = 5)
    private String usuTipoDocumento;

    @Column(name = "CODIGO_CAMPO")
    private Integer codigoCampo;

    @Column(name = "PAIS", length = 10)
    private String pais;

    // === ADDRESS FIELDS ===
    @Column(name = "DIRECCION_GEO_REFERENCIA", length = 500)
    private String direccionGeoReferencia;

    @Column(name = "DIRECCION_COMPLEMENTO", length = 500)
    private String direccionComplemento;

    @Column(name = "DIRECCION_DESTINO", length = 500)
    private String direccionDestino;

    // === PRODUCT/RAMO/CAUSE ===
    @Column(name = "RAMO_CODIGO")
    private Integer ramoCodigo;

    @Column(name = "PRODUCTO_CODIGO")
    private Integer productoCodigo;

    @Column(name = "TIPCONT_CODIGO")
    private Integer tipcontCodigo;

    @Column(name = "POLIZA", length = 30)
    private String poliza;

    @Column(name = "PECO_NUMERO_ORDEN")
    private Long pecoNumeroOrden;

    // === RISK/INSURED FIELDS ===
    @Column(name = "PLACA_RIESGO", length = 20)
    private String placaRiesgo;

    @Column(name = "CIUDAD_RIESGO", length = 100)
    private String ciudadRiesgo;

    // === STATUS FIELDS ===
    @Column(name = "ESTADO_LLAMADA", length = 5)
    private String estadoLlamada;

    @Column(name = "ESTADO_POLIZA", length = 5)
    private String estadoPoliza;

    @Column(name = "ESTADO_RETROLLAMADA", length = 5)
    private String estadoRetrollamada;

    @Column(name = "EXISTENTE", length = 5)
    private String existente;

    @Column(name = "INTERNO", length = 5)
    private String interno;

    // === CLASSIFICATION/SEVERITY ===
    @Column(name = "SEVERIDAD", length = 10)
    private String severidad;

    @Column(name = "LINEA_NEGOCIO", length = 10)
    private String lineaNegocio;

    @Column(name = "ORIGEN", length = 10)
    private String origen;

    @Column(name = "PREFERENCIAL", length = 5)
    private String preferencial;

    @Column(name = "ALTO_VALOR", length = 5)
    private String altoValor;

    // === RECLASSIFICATION ===
    @Column(name = "RECLASIFICA_RAMO_CODIGO")
    private Integer reclasificaRamoCodigo;

    @Column(name = "RECLASIFICA_PRODUCTO_CODIGO")
    private Integer reclasificaProductoCodigo;

    @Column(name = "RECLASIFICA_CAUSA_CODIGO")
    private Long reclasificaCausaCodigo;

    @Column(name = "COD_RAZON_RECLASIFICA", length = 20)
    private String codRazonReclasifica;

    // === DISPLAY/COMPUTED FIELDS ===
    @Transient
    private String dspRamo;

    @Transient
    private String dspProducto;

    @Transient
    private String dspNombre;

    @Transient
    private String dspTomador;

    @Transient
    private String dspRiesgo;

    @Transient
    private String dspRiesgoCampo;

    @Transient
    private String dspRiesgoValor;

    @Transient
    private String dspEstadoLlamada;

    @Transient
    private String dspEstadoServ;

    @Transient
    private String dspUsuario;

    @Transient
    private String dspDpto;

    @Transient
    private String dspOrigen;

    @Transient
    private String dspPreferencial;

    @Transient
    private String dspSeveridad;

    @Transient
    private String dspTipoAsistencia;

    @Transient
    private String dspOpcionCobertura;

    @Transient
    private String dspCoberturaVehiculo;

    @Transient
    private String dspCoberturaEdificio;

    @Transient
    private String dspAutomatico;

    @Transient
    private String dspFuncionarioBolivar;

    @Transient
    private String dspMechoqueHacedias;

    @Transient
    private String dspEnviadoCasoClick;

    @Transient
    private String dspDavivienda;

    @Transient
    private String dspFinVigencia;

    @Transient
    private String dspAgentPrivilegiado;

    @Transient
    private String dspNombreAgente;

    @Transient
    private String dspClaveAgente;

    @Transient
    private String dspDescripcion2;

    @Transient
    private String dspUsunumeroDocumento;

    @Transient
    private String dspUsuTipoDocumento;

    // === CONTACT FIELDS ===
    @Column(name = "TELEFONO_LLAMADA", length = 30)
    private String telefonoLlamada;

    // === CALLBACK FIELDS ===
    @Column(name = "OPERADOR_RETROLLAMADA", length = 50)
    private String operadorRetrollamada;

    @Column(name = "RAZON_NO_RETROLLAMADA", length = 200)
    private String razonNoRetrollamada;

    // === COVERAGE/INSURANCE ===
    @Column(name = "COBERTURA_360", length = 5)
    private String cobertura360;

    @Column(name = "ACUERDO_CLIENTE", length = 5)
    private String acuerdoCliente;

    @Column(name = "REGISTRA_ID_TITULAR", length = 5)
    private String registraIdTitular;

    @Column(name = "TRONADOR", length = 5)
    private String tronador;

    @Column(name = "TIENE_APP_BOLIVAR", length = 5)
    private String tieneAppBolivar;

    // === EXCEPTION FIELDS ===
    @Column(name = "EXCEPCIONES")
    private Integer excepciones;

    // === BILLING/SAP ===
    @Column(name = "ID_FACTURA_SAP", length = 50)
    private String idFacturaSap;

    @Column(name = "ID_PAGO_SAP", length = 50)
    private String idPagoSap;

    @Column(name = "ID_PEDIDO_SAP", length = 50)
    private String idPedidoSap;

    // === CLAIM FIELDS ===
    @Column(name = "NUMERO_AUTORIZACION", length = 30)
    private String numeroAutorizacion;

    @Column(name = "NUMERO_COTIZACION", length = 30)
    private String numeroCotizacion;

    @Column(name = "SERVICIO_ORIGINAL", length = 30)
    private String servicioOriginal;

    // === ALERT FIELDS ===
    @Column(name = "ALERTA_PYP", length = 500)
    private String alertaPyp;

    // === OBSERVATIONS ===
    @Column(name = "OBSERVACIONES_CAM", length = 4000)
    private String observacionesCam;

    // === MISCELLANEOUS ===
    @Column(name = "ENVIO_CORREO_SINIESTRO", length = 5)
    private String envioCorreoSiniestro;

    @Column(name = "CONTROL_CARTA", length = 5)
    private String controlCarta;

    @Column(name = "MENSAJE_CANCELACION", length = 500)
    private String mensajeCancelacion;

    @Column(name = "OT_SAMM", length = 30)
    private String otSamm;

    @Column(name = "LINK_DESCARGA", length = 500)
    private String linkDescarga;

    // === CLV FIELDS ===
    @Column(name = "VAR_CARACTERISTICA_CLIENTE_CLV", length = 500)
    private String varCaracteristicaClienteClv;

    @Column(name = "VAR_TIPO_RIESGO_CLV", length = 500)
    private String varTipoRiesgoClv;

    @Column(name = "VAR_WS_CLV", length = 500)
    private String varWsClv;

    @Column(name = "DESC_DATOS_CLIENTE_CLV", length = 500)
    private String descDatosClienteClv;

    @Column(name = "DESC_DATOS_RIESGO_ASEGURADO", length = 500)
    private String descDatosRiesgoAsegurado;
}
