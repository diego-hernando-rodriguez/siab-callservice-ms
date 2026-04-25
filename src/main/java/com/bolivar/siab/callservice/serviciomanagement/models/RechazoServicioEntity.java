package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for RECHAZO_SERVICIO table in NASIST schema.
 */
@Entity
@Table(name = "RECHAZO_SERVICIO", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RechazoServicioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "LLAMADA_NUMERO") private Long llamadaNumero;
    @Column(name = "NUMERO_AUTORIZACION") private Long numeroAutorizacion;
    @Column(name = "MOTIVO_RECHAZO", length = 500) private String motivoRechazo;
    @Column(name = "FECHA_RECHAZO") private LocalDateTime fechaRechazo;
    @Column(name = "USUARIO_RECHAZO", length = 50) private String usuarioRechazo;
    @Column(name = "ESTADO", length = 5) private String estado;
}
