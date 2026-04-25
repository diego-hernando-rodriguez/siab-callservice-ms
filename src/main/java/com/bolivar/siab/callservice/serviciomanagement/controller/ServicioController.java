package com.bolivar.siab.callservice.serviciomanagement.controller;

import com.bolivar.siab.callservice.commons.dto.ApiResponse;
import com.bolivar.siab.callservice.serviciomanagement.dto.*;
import com.bolivar.siab.callservice.serviciomanagement.services.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Service Management", description = "Endpoints for service assignment and management")
public class ServicioController {

    private final ServicioService servicioService;

    @PostMapping("/casos/{numeroCaso}/servicios")
    @Operation(summary = "Create a service for a case")
    public ResponseEntity<ApiResponse<ServicioPrestadoResponseDTO>> createService(
            @PathVariable Long numeroCaso, @RequestBody ServicioPrestadoRequestDTO request) {
        request.setLlamadaNumero(numeroCaso);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(servicioService.createService(request)));
    }

    @PutMapping("/servicios/{numeroAutorizacion}")
    @Operation(summary = "Update a service")
    public ResponseEntity<ApiResponse<ServicioPrestadoResponseDTO>> updateService(
            @PathVariable Long numeroAutorizacion, @RequestBody ServicioPrestadoRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.ok(servicioService.updateService(numeroAutorizacion, request)));
    }

    @GetMapping("/casos/{numeroCaso}/servicios")
    @Operation(summary = "List services for a case")
    public ResponseEntity<ApiResponse<List<ServicioPrestadoResponseDTO>>> listServices(@PathVariable Long numeroCaso) {
        return ResponseEntity.ok(ApiResponse.ok(servicioService.listServicesForCase(numeroCaso)));
    }

    @DeleteMapping("/servicios/{numeroAutorizacion}")
    @Operation(summary = "Cancel/annul a service")
    public ResponseEntity<ApiResponse<Void>> cancelService(
            @PathVariable Long numeroAutorizacion, @RequestParam(required = false) String motivo) {
        servicioService.cancelService(numeroAutorizacion, motivo);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Servicio anulado").build());
    }

    @PostMapping("/servicios/{numeroAutorizacion}/proveedor")
    @Operation(summary = "Assign provider to service")
    public ResponseEntity<ApiResponse<ServicioPrestadoResponseDTO>> assignProvider(
            @PathVariable Long numeroAutorizacion, @RequestParam Long consecutivoPunto) {
        return ResponseEntity.ok(ApiResponse.ok(servicioService.assignProvider(numeroAutorizacion, consecutivoPunto)));
    }

    @PostMapping("/servicios/{numeroAutorizacion}/ingresos")
    @Operation(summary = "Create income record for service")
    public ResponseEntity<ApiResponse<IngresoResponseDTO>> createIncome(
            @PathVariable Long numeroAutorizacion, @RequestBody IngresoRequestDTO request) {
        request.setNumeroAutorizacion(numeroAutorizacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(servicioService.createIncome(request)));
    }

    @GetMapping("/servicios/{numeroAutorizacion}/ingresos")
    @Operation(summary = "Get incomes for a service")
    public ResponseEntity<ApiResponse<List<IngresoResponseDTO>>> getIncomes(@PathVariable Long numeroAutorizacion) {
        return ResponseEntity.ok(ApiResponse.ok(servicioService.getIncomesByService(numeroAutorizacion)));
    }

    // === LOV ENDPOINTS ===

    @GetMapping("/servicios/lov/servicios")
    @Operation(summary = "LOV: Services filtered by ramo/producto/causa (SERPRE_SERV_CODIGO_LOV14)")
    public ResponseEntity<ApiResponse<List<com.bolivar.siab.callservice.configuracion.dto.DominioDTO>>> lovServicios(
            @RequestParam(required = false) Integer ramo,
            @RequestParam(required = false) Integer producto,
            @RequestParam(required = false) Long causa) {
        return ResponseEntity.ok(ApiResponse.ok(List.of()));
    }

    @GetMapping("/servicios/lov/estados")
    @Operation(summary = "LOV: Service states")
    public ResponseEntity<ApiResponse<List<com.bolivar.siab.callservice.configuracion.dto.DominioDTO>>> lovEstados() {
        return ResponseEntity.ok(ApiResponse.ok(List.of()));
    }
}
