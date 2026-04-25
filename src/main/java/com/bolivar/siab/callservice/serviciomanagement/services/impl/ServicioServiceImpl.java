package com.bolivar.siab.callservice.serviciomanagement.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.exception.BusinessException;
import com.bolivar.siab.callservice.proveedor.models.LogeoProveedorEntity;
import com.bolivar.siab.callservice.proveedor.models.ProvCalificacionesEntity;
import com.bolivar.siab.callservice.proveedor.repository.LogeoProveedorRepository;
import com.bolivar.siab.callservice.proveedor.repository.ProvCalificacionesRepository;
import com.bolivar.siab.callservice.serviciomanagement.dto.*;
import com.bolivar.siab.callservice.serviciomanagement.mapper.ServicioMapper;
import com.bolivar.siab.callservice.serviciomanagement.models.*;
import com.bolivar.siab.callservice.serviciomanagement.repository.*;
import com.bolivar.siab.callservice.serviciomanagement.services.ServicioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService {

    private final ServicioPrestadoRepository servicioPrestadoRepository;
    private final ServiciosAsociadosRepository serviciosAsociadosRepository;
    private final ValoresServicioRepository valoresServicioRepository;
    private final IngresoRepository ingresoRepository;
    private final AdicionalesPrestadosRepository adicionalesRepository;
    private final LogeoProveedorRepository logeoProveedorRepository;
    private final ProvCalificacionesRepository provCalificacionesRepository;
    private final StoredProcedureRepository storedProcedureRepository;
    private final ServicioMapper servicioMapper;

    @Override
    @Transactional
    public ServicioPrestadoResponseDTO createService(ServicioPrestadoRequestDTO request) {
        log.info("Creating service for case: {}, service code: {}", request.getLlamadaNumero(), request.getServCodigo());

        // Validate service against SERVICIOS_ASOCIADOS
        List<ServiciosAsociadosEntity> asociados = serviciosAsociadosRepository
                .findByRamoCodigoAndProductoCodigoAndCausaCodigo(null, null, null);
        // Auto-assign NUMERO_AUTORIZACION
        Long numeroAutorizacion = storedProcedureRepository.getConsecutivoSiab("NUMERO_AUTORIZACION");

        ServicioPrestadoEntity entity = ServicioPrestadoEntity.builder()
                .numeroAutorizacion(numeroAutorizacion)
                .llamadaNumero(request.getLlamadaNumero())
                .servCodigo(request.getServCodigo())
                .clservCodigo(request.getClservCodigo())
                .estadoServicio("AB")
                .moneda(request.getMoneda() != null ? request.getMoneda() : "COP")
                .direccionOrigen(request.getDireccionOrigen())
                .direccionDestino(request.getDireccionDestino())
                .fechaCreacion(LocalDateTime.now())
                .tipoDespacho(request.getTipoDespacho())
                .build();

        // Calculate tariff from VALORES_SERVICIO
        valoresServicioRepository.findByServCodigoAndClservCodigo(request.getServCodigo(), request.getClservCodigo())
                .stream().findFirst().ifPresent(tariff -> {
                    entity.setValorServicio(tariff.getValorServicio());
                    entity.setPorcentajeImpuesto(tariff.getPorcentajeImpuesto());
                    if (tariff.getValorServicio() != null && tariff.getPorcentajeImpuesto() != null) {
                        BigDecimal impuesto = tariff.getValorServicio()
                                .multiply(tariff.getPorcentajeImpuesto())
                                .divide(BigDecimal.valueOf(100));
                        entity.setValorServicioImpuesto(impuesto);
                        entity.setValorTotal(tariff.getValorServicio().add(impuesto));
                    }
                });

        ServicioPrestadoEntity saved = servicioPrestadoRepository.save(entity);
        log.info("Service created with authorization: {}", saved.getNumeroAutorizacion());
        return servicioMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ServicioPrestadoResponseDTO updateService(Long numeroAutorizacion, ServicioPrestadoRequestDTO request) {
        ServicioPrestadoEntity entity = servicioPrestadoRepository.findById(numeroAutorizacion)
                .orElseThrow(() -> new BusinessException("SERVICIO_NO_ENCONTRADO", "Servicio no encontrado: " + numeroAutorizacion));
        entity.setFechaModificacion(LocalDateTime.now());
        if (request.getDireccionOrigen() != null) entity.setDireccionOrigen(request.getDireccionOrigen());
        if (request.getDireccionDestino() != null) entity.setDireccionDestino(request.getDireccionDestino());
        if (request.getObservaciones() != null) entity.setObservaciones(request.getObservaciones());
        return servicioMapper.toResponse(servicioPrestadoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioPrestadoResponseDTO> listServicesForCase(Long numeroCaso) {
        return servicioPrestadoRepository.findByLlamadaNumeroOrderByFechaCreacionDesc(numeroCaso)
                .stream().map(servicioMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelService(Long numeroAutorizacion, String motivo) {
        ServicioPrestadoEntity entity = servicioPrestadoRepository.findById(numeroAutorizacion)
                .orElseThrow(() -> new BusinessException("SERVICIO_NO_ENCONTRADO", "Servicio no encontrado"));
        entity.setEstadoAnterior(entity.getEstadoServicio());
        entity.setEstadoServicio("AN");
        entity.setMotivoCancelacion(motivo);
        entity.setFechaModificacion(LocalDateTime.now());
        servicioPrestadoRepository.save(entity);
    }

    @Override
    @Transactional
    public ServicioPrestadoResponseDTO assignProvider(Long numeroAutorizacion, Long consecutivoPunto) {
        ServicioPrestadoEntity servicio = servicioPrestadoRepository.findById(numeroAutorizacion)
                .orElseThrow(() -> new BusinessException("SERVICIO_NO_ENCONTRADO", "Servicio no encontrado"));

        LogeoProveedorEntity proveedor = logeoProveedorRepository.findById(consecutivoPunto)
                .orElseThrow(() -> new BusinessException("PROVEEDOR_NO_ENCONTRADO", "Proveedor no encontrado"));

        // Update provider on service
        servicio.setConsecutivoPunto(consecutivoPunto);
        servicio.setPersonaNumeroDocumento(proveedor.getPersonaNumeroDocumento());
        servicio.setPersonaTipoDocumento(proveedor.getPersonaTipoDocumento());
        servicio.setPersonaNombre(proveedor.getNombre());
        servicio.setCelularProveedor(proveedor.getCelular());
        servicio.setFechaModificacion(LocalDateTime.now());

        // SQL-11: Update LOGEO_PROVEEDOR SET ESTADO='AS'
        proveedor.setEstado("AS");
        logeoProveedorRepository.save(proveedor);

        // SQL-16: Insert PROV_CALIFICACIONES
        ProvCalificacionesEntity calificacion = ProvCalificacionesEntity.builder()
                .numeroAutorizacion(numeroAutorizacion)
                .consecutivoPunto(consecutivoPunto)
                .personaNumeroDocumento(proveedor.getPersonaNumeroDocumento())
                .personaTipoDocumento(proveedor.getPersonaTipoDocumento())
                .llamadaNumero(servicio.getLlamadaNumero())
                .servCodigo(servicio.getServCodigo())
                .clservCodigo(servicio.getClservCodigo())
                .fechaCalificacion(LocalDateTime.now())
                .estado("PE")
                .build();
        provCalificacionesRepository.save(calificacion);

        return servicioMapper.toResponse(servicioPrestadoRepository.save(servicio));
    }

    @Override
    @Transactional
    public List<AdicionalesPrestadosDTO> manageAdditionalServices(Long llamadaNumero, Long numeroAutorizacion, List<AdicionalesPrestadosDTO> adicionales) {
        // Delete existing and re-create
        adicionalesRepository.deleteByLlamadaNumeroAndNumeroAutorizacion(llamadaNumero, numeroAutorizacion);
        BigDecimal totalAdicionales = BigDecimal.ZERO;
        for (AdicionalesPrestadosDTO dto : adicionales) {
            BigDecimal valorTotal = dto.getValorUnitario() != null && dto.getHrsEspera() != null
                    ? dto.getValorUnitario().multiply(dto.getHrsEspera()) : BigDecimal.ZERO;
            AdicionalesPrestadosEntity entity = AdicionalesPrestadosEntity.builder()
                    .llamadaNumero(llamadaNumero)
                    .numeroAutorizacion(numeroAutorizacion)
                    .codigo(dto.getCodigo())
                    .tipoServicio(dto.getTipoServicio())
                    .hrsEspera(dto.getHrsEspera())
                    .valorUnitario(dto.getValorUnitario())
                    .valorTotal(valorTotal)
                    .build();
            adicionalesRepository.save(entity);
            totalAdicionales = totalAdicionales.add(valorTotal);
        }
        // Update total on service
        final BigDecimal finalTotalAdicionales = totalAdicionales;
        servicioPrestadoRepository.findById(numeroAutorizacion).ifPresent(s -> {
            s.setTotalAdicionales(finalTotalAdicionales);
            servicioPrestadoRepository.save(s);
        });
        return adicionales;
    }

    @Override
    @Transactional
    public IngresoResponseDTO createIncome(IngresoRequestDTO request) {
        Long secuencia = storedProcedureRepository.getConsecutivoSiab("S_INGRESOS");
        IngresoEntity entity = IngresoEntity.builder()
                .secuencia(secuencia)
                .numeroAutorizacion(request.getNumeroAutorizacion())
                .formaPago(request.getFormaPago())
                .valor(request.getValor())
                .fechaCreacion(LocalDateTime.now())
                .build();
        IngresoEntity saved = ingresoRepository.save(entity);
        return IngresoResponseDTO.builder()
                .secuencia(saved.getSecuencia())
                .numeroAutorizacion(saved.getNumeroAutorizacion())
                .formaPago(saved.getFormaPago())
                .valor(saved.getValor())
                .fechaCreacion(saved.getFechaCreacion())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngresoResponseDTO> getIncomesByService(Long numeroAutorizacion) {
        return ingresoRepository.findByNumeroAutorizacion(numeroAutorizacion).stream()
                .map(e -> IngresoResponseDTO.builder()
                        .secuencia(e.getSecuencia())
                        .numeroAutorizacion(e.getNumeroAutorizacion())
                        .formaPago(e.getFormaPago())
                        .valor(e.getValor())
                        .totalPagadoUsr(e.getTotalPagadoUsr())
                        .pendiente(e.getPendiente())
                        .fechaCreacion(e.getFechaCreacion())
                        .build())
                .collect(Collectors.toList());
    }
}
