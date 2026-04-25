package com.bolivar.siab.callservice.proveedor.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for DETALLE_LOGEO_PROVEEDOR table in NASIST schema.
 */
@Entity
@Table(name = "DETALLE_LOGEO_PROVEEDOR", schema = "NASIST")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleLogeoProveedorEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONSECUTIVO_LOGEO") private Long consecutivoLogeo;
    @Column(name = "NUMERO_AUTORIZACION") private Long numeroAutorizacion;
    @Column(name = "OPCION", length = 10) private String opcion;
    @Column(name = "DESC_OPCION", length = 200) private String descOpcion;
    @Column(name = "FECHA_OPCION") private LocalDateTime fechaOpcion;
    @Column(name = "DIRECCION", length = 500) private String direccion;
    @Column(name = "DIRECCION_APP", length = 500) private String direccionApp;
    @Column(name = "UBICACION", length = 500) private String ubicacion;
    @Column(name = "TIEMPO_TOTAL_EXTRA_LLEGADA", length = 20) private String tiempoTotalExtraLlegada;
    @Transient private String dspCelular;
    @Transient private String dspTecnico;
}
