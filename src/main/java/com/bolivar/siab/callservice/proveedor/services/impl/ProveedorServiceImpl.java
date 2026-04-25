package com.bolivar.siab.callservice.proveedor.services.impl;

import com.bolivar.siab.callservice.exception.BusinessException;
import com.bolivar.siab.callservice.proveedor.dto.*;
import com.bolivar.siab.callservice.proveedor.models.*;
import com.bolivar.siab.callservice.proveedor.repository.*;
import com.bolivar.siab.callservice.proveedor.services.ProveedorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final LogeoProveedorRepository logeoProveedorRepository;
    private final ProvCalificacionesRepository provCalificacionesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> searchProviders(Integer servCodigo, Integer clservCodigo, Long locgeCodigo) {
        return logeoProveedorRepository.findByServCodigoAndClservCodigoAndLocgeCodigoAndEstado(
                servCodigo, clservCodigo, locgeCodigo, "DI").stream()
                .map(e -> ProveedorDTO.builder()
                        .consecutivoPunto(e.getConsecutivoPunto())
                        .personaNumeroDocumento(e.getPersonaNumeroDocumento())
                        .nombre(e.getNombre())
                        .estado(e.getEstado())
                        .servCodigo(e.getServCodigo())
                        .clservCodigo(e.getClservCodigo())
                        .locgeCodigo(e.getLocgeCodigo())
                        .zona(e.getZona())
                        .celular(e.getCelular())
                        .elite(e.getElite())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProveedorDTO assignProvider(Long consecutivoPunto, Long numeroAutorizacion, Long llamadaNumero) {
        LogeoProveedorEntity proveedor = logeoProveedorRepository.findById(consecutivoPunto)
                .orElseThrow(() -> new BusinessException("PROVEEDOR_NO_ENCONTRADO", "Proveedor no encontrado"));
        proveedor.setEstado("AS");
        logeoProveedorRepository.save(proveedor);
        return ProveedorDTO.builder()
                .consecutivoPunto(proveedor.getConsecutivoPunto())
                .nombre(proveedor.getNombre())
                .estado(proveedor.getEstado())
                .build();
    }

    @Override
    @Transactional
    public void rateProvider(CalificacionProveedorDTO calificacion) {
        ProvCalificacionesEntity entity = ProvCalificacionesEntity.builder()
                .numeroAutorizacion(calificacion.getNumeroAutorizacion())
                .consecutivoPunto(calificacion.getConsecutivoPunto())
                .amabilidad(calificacion.getAmabilidad())
                .calificacionGeneral(calificacion.getCalificacionGeneral())
                .calificacionTiempo(calificacion.getCalificacionTiempo())
                .calificacionServicio(calificacion.getCalificacionServicio())
                .calificacionPresentacion(calificacion.getCalificacionPresentacion())
                .calificacionHerramientas(calificacion.getCalificacionHerramientas())
                .observaciones(calificacion.getObservaciones())
                .fechaCalificacion(LocalDateTime.now())
                .estado("CA")
                .build();
        provCalificacionesRepository.save(entity);
    }
}
