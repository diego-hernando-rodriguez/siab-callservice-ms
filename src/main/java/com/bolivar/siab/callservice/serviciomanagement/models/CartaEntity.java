package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for CARTAS table in NASIST schema.
 * Letter generation records.
 */
@Entity
@Table(name = "CARTAS", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "LLAMADA_NUMERO") private Long llamadaNumero;
    @Column(name = "NUMERO_AUTORIZACION") private Long numeroAutorizacion;
    @Column(name = "TIPO_CARTA", length = 20) private String tipoCarta;
    @Column(name = "CONTENIDO", length = 4000) private String contenido;
    @Column(name = "DESTINATARIO", length = 200) private String destinatario;
    @Column(name = "ESTADO", length = 5) private String estado;
    @Column(name = "FECHA_CREACION") private LocalDateTime fechaCreacion;
    @Column(name = "USUARIO_CREACION", length = 50) private String usuarioCreacion;
}
