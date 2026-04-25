package com.bolivar.siab.callservice.serviciomanagement.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

/**
 * JPA Entity for TIPOS_DATO_VALOR_SERVICIO table in NASIST schema.
 */
@Entity @Immutable
@Table(name = "TIPOS_DATO_VALOR_SERVICIO", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TiposDatoValorServicioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") private Long id;
    @Column(name = "SERV_CODIGO") private Integer servCodigo;
    @Column(name = "CLSERV_CODIGO") private Integer clservCodigo;
    @Column(name = "CODIGO") private Integer codigo;
    @Column(name = "DESCRIPCION", length = 200) private String descripcion;
    @Column(name = "APLICA_CANTIDAD", length = 1) private String aplicaCantidad;
    @Column(name = "VALOR_SERVICIO", precision = 18, scale = 2) private BigDecimal valorServicio;
    @Column(name = "CAMBIO_TARIFA_OPTIMA", length = 1) private String cambioTarifaOptima;
    @Column(name = "ESTADO", length = 5) private String estado;
}
