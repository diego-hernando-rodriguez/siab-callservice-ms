package com.bolivar.siab.callservice.tarifa.services.impl;

import com.bolivar.siab.callservice.serviciomanagement.models.ValoresServicioEntity;
import com.bolivar.siab.callservice.serviciomanagement.repository.ValoresServicioRepository;
import com.bolivar.siab.callservice.tarifa.dto.*;
import com.bolivar.siab.callservice.tarifa.models.*;
import com.bolivar.siab.callservice.tarifa.repository.*;
import com.bolivar.siab.callservice.tarifa.services.TarifaService;
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
public class TarifaServiceImpl implements TarifaService {

    private final TarifaLlamadaRepository tarifaLlamadaRepository;
    private final DetalleTarifaLlamadaRepository detalleTarifaRepository;
    private final ValoresServicioRepository valoresServicioRepository;
    private final RutasIntermediasRepository rutasIntermediasRepository;
    private final TmpTarifasPuntosRepository tmpTarifasPuntosRepository;
    private final IntegracionTarifasClsRepository integracionTarifasClsRepository;

    @Override
    @Transactional
    public TarifaDTO calculateTariff(Long llamadaNumero, Long numeroAutorizacion, Integer servCodigo, Integer clservCodigo) {
        log.info("Calculating tariff for case: {}, authorization: {}", llamadaNumero, numeroAutorizacion);

        // Get base tariff from VALORES_SERVICIO
        List<ValoresServicioEntity> tarifas = valoresServicioRepository.findByServCodigoAndClservCodigo(servCodigo, clservCodigo);
        BigDecimal valorBase = BigDecimal.ZERO;
        BigDecimal porcentajeImpuesto = BigDecimal.ZERO;

        if (!tarifas.isEmpty()) {
            ValoresServicioEntity tarifa = tarifas.get(0);
            valorBase = tarifa.getValorServicio() != null ? tarifa.getValorServicio() : BigDecimal.ZERO;
            porcentajeImpuesto = tarifa.getPorcentajeImpuesto() != null ? tarifa.getPorcentajeImpuesto() : BigDecimal.ZERO;
        }

        BigDecimal valorImpuesto = valorBase.multiply(porcentajeImpuesto).divide(BigDecimal.valueOf(100));
        BigDecimal valorTotal = valorBase.add(valorImpuesto);

        TarifaLlamadaEntity tarifaEntity = TarifaLlamadaEntity.builder()
                .llamadaNumero(llamadaNumero)
                .numeroAutorizacion(numeroAutorizacion)
                .servCodigo(servCodigo)
                .clservCodigo(clservCodigo)
                .valorTarifa(valorBase)
                .valorImpuesto(valorImpuesto)
                .porcentajeImpuesto(porcentajeImpuesto)
                .valorTotal(valorTotal)
                .fechaTarifa(LocalDateTime.now())
                .estado("AC")
                .build();

        TarifaLlamadaEntity saved = tarifaLlamadaRepository.save(tarifaEntity);

        // Update INTEGRACION_TARIFAS_CLS
        integracionTarifasClsRepository.findByNumeroAutorizacion(numeroAutorizacion).ifPresentOrElse(
                itc -> { itc.setValorTarifa(valorTotal); itc.setFechaModificacion(LocalDateTime.now()); integracionTarifasClsRepository.save(itc); },
                () -> integracionTarifasClsRepository.save(IntegracionTarifasClsEntity.builder()
                        .llamadaNumero(llamadaNumero).numeroAutorizacion(numeroAutorizacion)
                        .valorTarifa(valorTotal).estado("AC").fechaCreacion(LocalDateTime.now()).build()));

        return TarifaDTO.builder()
                .tarifaCodigo(saved.getTarifaCodigo())
                .llamadaNumero(llamadaNumero)
                .numeroAutorizacion(numeroAutorizacion)
                .valorTarifa(valorBase)
                .valorImpuesto(valorImpuesto)
                .porcentajeImpuesto(porcentajeImpuesto)
                .valorTotal(valorTotal)
                .estado(saved.getEstado())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TarifaDTO> getTariffsForService(Long llamadaNumero, Long numeroAutorizacion) {
        return tarifaLlamadaRepository.findByLlamadaNumeroAndNumeroAutorizacion(llamadaNumero, numeroAutorizacion)
                .stream().map(t -> {
                    List<DetalleTarifaDTO> detalles = detalleTarifaRepository.findByTarifaCodigo(t.getTarifaCodigo())
                            .stream().map(d -> DetalleTarifaDTO.builder()
                                    .consecutivo(d.getConsecutivo())
                                    .valorMedida(d.getValorMedida())
                                    .ancho(d.getAncho())
                                    .alto(d.getAlto())
                                    .cantidad(d.getCantidad())
                                    .totalDetalleTar(d.getTotalDetalleTar())
                                    .build())
                            .collect(Collectors.toList());
                    return TarifaDTO.builder()
                            .tarifaCodigo(t.getTarifaCodigo())
                            .llamadaNumero(t.getLlamadaNumero())
                            .numeroAutorizacion(t.getNumeroAutorizacion())
                            .valorTarifa(t.getValorTarifa())
                            .valorImpuesto(t.getValorImpuesto())
                            .porcentajeImpuesto(t.getPorcentajeImpuesto())
                            .valorTotal(t.getValorTotal())
                            .estado(t.getEstado())
                            .detalles(detalles)
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RutaIntermediaDTO> getRoutes(Long llamadaNumero, Long numeroAutorizacion) {
        return rutasIntermediasRepository.findByLlamadaNumeroAndNumeroAutorizacionOrderBySecuencia(llamadaNumero, numeroAutorizacion)
                .stream().map(r -> RutaIntermediaDTO.builder()
                        .id(r.getId())
                        .secuencia(r.getSecuencia())
                        .direccion(r.getDireccion())
                        .latitud(r.getLatitud())
                        .longitud(r.getLongitud())
                        .distanciaKm(r.getDistanciaKm())
                        .descripcion(r.getDescripcion())
                        .build())
                .collect(Collectors.toList());
    }
}
