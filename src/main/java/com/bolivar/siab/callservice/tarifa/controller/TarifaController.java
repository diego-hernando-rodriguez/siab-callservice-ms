package com.bolivar.siab.callservice.tarifa.controller;

import com.bolivar.siab.callservice.commons.dto.ApiResponse;
import com.bolivar.siab.callservice.tarifa.dto.*;
import com.bolivar.siab.callservice.tarifa.services.TarifaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tarifas")
@RequiredArgsConstructor
@Tag(name = "Tariff Management", description = "Endpoints for tariff calculation and routes")
public class TarifaController {

    private final TarifaService tarifaService;

    @PostMapping("/calcular")
    @Operation(summary = "Calculate tariff for service")
    public ResponseEntity<ApiResponse<TarifaDTO>> calculateTariff(
            @RequestParam Long llamadaNumero, @RequestParam Long numeroAutorizacion,
            @RequestParam Integer servCodigo, @RequestParam Integer clservCodigo) {
        return ResponseEntity.ok(ApiResponse.ok(tarifaService.calculateTariff(llamadaNumero, numeroAutorizacion, servCodigo, clservCodigo)));
    }

    @GetMapping
    @Operation(summary = "Get tariffs for a service")
    public ResponseEntity<ApiResponse<List<TarifaDTO>>> getTariffs(
            @RequestParam Long llamadaNumero, @RequestParam Long numeroAutorizacion) {
        return ResponseEntity.ok(ApiResponse.ok(tarifaService.getTariffsForService(llamadaNumero, numeroAutorizacion)));
    }

    @GetMapping("/rutas")
    @Operation(summary = "Get intermediate routes")
    public ResponseEntity<ApiResponse<List<RutaIntermediaDTO>>> getRoutes(
            @RequestParam Long llamadaNumero, @RequestParam Long numeroAutorizacion) {
        return ResponseEntity.ok(ApiResponse.ok(tarifaService.getRoutes(llamadaNumero, numeroAutorizacion)));
    }
}
