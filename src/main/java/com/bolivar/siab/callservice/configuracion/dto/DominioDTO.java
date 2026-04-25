package com.bolivar.siab.callservice.configuracion.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DominioDTO {
    private String rvDomain;
    private String rvLowValue;
    private String rvHighValue;
    private String rvAbbreviation;
    private String rvMeaning;
}
