package com.bolivar.siab.callservice.proveedor.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for LOGEO_PROVEEDOR table in NASIST schema.
 * Provider login/tracking for service assignments.
 */
@Entity
@Table(name = "LOGEO_PROVEEDOR", schema = "NASIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogeoProveedorEntity {

    @Id
    @Column(name = "CONSECUTIVO_PUNTO", nullable = false)
    private Long consecutivoPunto;

    @Column(name = "PERSONA_NUMERO_DOCUMENTO", length = 30)
    private String personaNumeroDocumento;

    @Column(name = "PERSONA_TIPO_DOCUMENTO", length = 5)
    private String personaTipoDocumento;

    @Column(name = "NOMBRE", length = 200)
    private String nombre;

    @Column(name = "ESTADO", length = 5)
    private String estado;

    @Column(name = "SERV_CODIGO")
    private Integer servCodigo;

    @Column(name = "CLSERV_CODIGO")
    private Integer clservCodigo;

    @Column(name = "LOCGE_CODIGO")
    private Long locgeCodigo;

    @Column(name = "ZONA", length = 50)
    private String zona;

    @Column(name = "CELULAR", length = 30)
    private String celular;

    @Column(name = "ELITE", length = 5)
    private String elite;

    @Column(name = "FECHA_LOGEO")
    private LocalDateTime fechaLogeo;
}
