package com.bolivar.siab.callservice.proveedor.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for PROV_CALIFICACIONES table in NASIST schema.
 * Provider ratings/qualifications for services.
 */
@Entity
@Table(name = "PROV_CALIFICACIONES", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvCalificacionesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NUMERO_AUTORIZACION")
    private Long numeroAutorizacion;

    @Column(name = "CLIE_NUMERO_DOCUMENTO", length = 30)
    private String clieNumeroDocumento;

    @Column(name = "CLIE_TIPO_DOCUMENTO", length = 5)
    private String clieTipoDocumento;

    @Column(name = "CONSECUTIVO_PUNTO")
    private Long consecutivoPunto;

    @Column(name = "AMABILIDAD")
    private Integer amabilidad;

    @Column(name = "CANCELA", length = 5)
    private String cancela;

    @Column(name = "CALIFICACION_GENERAL")
    private Integer calificacionGeneral;

    @Column(name = "CALIFICACION_TIEMPO")
    private Integer calificacionTiempo;

    @Column(name = "CALIFICACION_SERVICIO")
    private Integer calificacionServicio;

    @Column(name = "CALIFICACION_PRESENTACION")
    private Integer calificacionPresentacion;

    @Column(name = "CALIFICACION_HERRAMIENTAS")
    private Integer calificacionHerramientas;

    @Column(name = "OBSERVACIONES", length = 4000)
    private String observaciones;

    @Column(name = "FECHA_CALIFICACION")
    private LocalDateTime fechaCalificacion;

    @Column(name = "USUARIO_CALIFICACION", length = 50)
    private String usuarioCalificacion;

    @Column(name = "LLAMADA_NUMERO")
    private Long llamadaNumero;

    @Column(name = "SERV_CODIGO")
    private Integer servCodigo;

    @Column(name = "CLSERV_CODIGO")
    private Integer clservCodigo;

    @Column(name = "PERSONA_NUMERO_DOCUMENTO", length = 30)
    private String personaNumeroDocumento;

    @Column(name = "PERSONA_TIPO_DOCUMENTO", length = 5)
    private String personaTipoDocumento;

    @Column(name = "ESTADO", length = 5)
    private String estado;

    @Column(name = "TIPO_CALIFICACION", length = 10)
    private String tipoCalificacion;
}
