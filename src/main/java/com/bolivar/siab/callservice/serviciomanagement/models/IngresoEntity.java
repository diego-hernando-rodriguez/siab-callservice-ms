package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity for INGRESOS table in NASIST schema.
 * Income/payment records for services. Uses S_INGRESOS sequence.
 */
@Entity
@Table(name = "INGRESOS", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngresoEntity {

    @Id
    @Column(name = "SECUENCIA", nullable = false)
    private Long secuencia;

    @Column(name = "NUMERO_AUTORIZACION")
    private Long numeroAutorizacion;

    @Column(name = "FORMA_PAGO", length = 10)
    private String formaPago;

    @Column(name = "VALOR", precision = 18, scale = 2)
    private BigDecimal valor;

    @Column(name = "VALOR_CON_COMISION", precision = 18, scale = 2)
    private BigDecimal valorConComision;

    @Column(name = "PORC_COMISION", precision = 5, scale = 2)
    private BigDecimal porcComision;

    @Column(name = "TOTAL_PAGADO_USR", precision = 18, scale = 2)
    private BigDecimal totalPagadoUsr;

    @Column(name = "TOTAL_PAGADO_USR_COMM", precision = 18, scale = 2)
    private BigDecimal totalPagadoUsrComm;

    @Column(name = "PENDIENTE", precision = 18, scale = 2)
    private BigDecimal pendiente;

    @Column(name = "NUMERO_CONSIGNACION", length = 50)
    private String numeroConsignacion;

    @Column(name = "PERSONA_CONSIGNACION", length = 200)
    private String personaConsignacion;

    @Column(name = "CUENTA_CONSIGNACION", length = 50)
    private String cuentaConsignacion;

    @Column(name = "TIPO_TARJETA", length = 10)
    private String tipoTarjeta;

    @Column(name = "NUMERO_TARJETA", length = 50)
    private String numeroTarjeta;

    @Column(name = "VENCIMIENTO_TARJETA", length = 10)
    private String vencimientoTarjeta;

    @Column(name = "PERSONA_TARJETAHABIENTE", length = 200)
    private String personaTarjetahabiente;

    @Column(name = "NUMID_TARJETAHABIENTE", length = 30)
    private String numidTarjetahabiente;

    @Column(name = "NUMERO_APROBACION_TARJETA", length = 50)
    private String numeroAprobacionTarjeta;

    @Column(name = "NUMERO_CUOTAS_TARJETA")
    private Integer numeroCuotasTarjeta;

    @Column(name = "LOCG_CODIGO_ENT")
    private Long locgCodigoEnt;

    @Column(name = "PREFIJO_CODIGO_ENT", length = 10)
    private String prefijoCodigoEnt;

    @Column(name = "FECHA_OPERACION")
    private LocalDateTime fechaOperacion;

    @Column(name = "FECHA_TRANSACCION")
    private LocalDateTime fechaTransaccion;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;

    @Column(name = "USUARIO_CREACION", length = 50)
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION", length = 50)
    private String usuarioModificacion;

    @Column(name = "REG_ANTERIOR", length = 1)
    private String regAnterior;

    @Column(name = "SIG_REGISTRO", length = 1)
    private String sigRegistro;

    @Transient
    private String dspEntidadNombre;
}
