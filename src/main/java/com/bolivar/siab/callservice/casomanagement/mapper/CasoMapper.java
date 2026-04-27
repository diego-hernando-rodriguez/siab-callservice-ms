package com.bolivar.siab.callservice.casomanagement.mapper;

import com.bolivar.siab.callservice.casomanagement.dto.CasoRequestDTO;
import com.bolivar.siab.callservice.casomanagement.dto.CasoResponseDTO;
import com.bolivar.siab.callservice.casomanagement.models.LlamadaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CasoMapper {
    CasoResponseDTO toResponse(LlamadaEntity entity);

    @Mapping(target = "numero", ignore = true)
    @Mapping(target = "fechaLlamada", ignore = true)
    @Mapping(target = "numeroSiniestro", ignore = true)
    @Mapping(target = "estadoLlamada", ignore = true)
    @Mapping(target = "operador", ignore = true)
    @Mapping(target = "horaLlamada", ignore = true)
    @Mapping(target = "fechaHoraLlamada", ignore = true)
    @Mapping(target = "observaciones", ignore = true)
    @Mapping(target = "poliza", ignore = true)
    @Mapping(target = "tronador", ignore = true)
    @Mapping(target = "estadoPoliza", ignore = true)
    @Mapping(target = "contFechaFinVigencia", ignore = true)
    @Mapping(target = "contFechaInicioVigencia", ignore = true)
    @Mapping(target = "servicioOriginal", ignore = true)
    @Mapping(target = "pagoUsuario", ignore = true)
    @Mapping(target = "pagoOtro", ignore = true)
    @Mapping(target = "pagoAsistencia", ignore = true)
    @Mapping(target = "valorAtencion", ignore = true)
    @Mapping(target = "rutaCodigo", ignore = true)
    @Mapping(target = "origen", ignore = true)
    @Mapping(target = "mcaEnvioClicksoftware", ignore = true)
    @Mapping(target = "mcaEnvioSalesforce", ignore = true)
    @Mapping(target = "gestionCasoClicksoftware", ignore = true)
    @Mapping(target = "gestionCasoSalesforce", ignore = true)
    @Mapping(target = "envioCorreoSiniestro", ignore = true)
    @Mapping(target = "tieneAppBolivar", ignore = true)
    @Mapping(target = "linkDescarga", ignore = true)
    @Mapping(target = "numeroCotizacion", ignore = true)
    @Mapping(target = "otSamm", ignore = true)
    @Mapping(target = "reclasificaRamoCodigo", ignore = true)
    @Mapping(target = "reclasificaProductoCodigo", ignore = true)
    @Mapping(target = "reclasificaCausaCodigo", ignore = true)
    @Mapping(target = "codRazonReclasifica", ignore = true)
    @Mapping(target = "altoValor", ignore = true)
    @Mapping(target = "acuerdoCliente", ignore = true)
    @Mapping(target = "direccionDestino", ignore = true)
    LlamadaEntity toEntity(CasoRequestDTO request);

    @Mapping(target = "numero", ignore = true)
    @Mapping(target = "fechaLlamada", ignore = true)
    @Mapping(target = "numeroSiniestro", ignore = true)
    void updateEntity(CasoRequestDTO request, @MappingTarget LlamadaEntity entity);
}
