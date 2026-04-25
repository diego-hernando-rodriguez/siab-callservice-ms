package com.bolivar.siab.callservice.poliza.dto;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RiesgoBusquedaResponseDTO {
    private List<RiesgoAseguradoDTO> riesgos;
    private boolean encontrado;
    private String modelo;
    private String color;
    private String tipoAsistencia;
    private String opcionCobertura;
}
