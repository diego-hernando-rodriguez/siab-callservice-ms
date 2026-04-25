package com.bolivar.siab.callservice.proveedor.controller;

import com.bolivar.siab.callservice.commons.dto.ApiResponse;
import com.bolivar.siab.callservice.proveedor.dto.*;
import com.bolivar.siab.callservice.proveedor.services.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
@RequiredArgsConstructor
@Tag(name = "Provider Management", description = "Endpoints for provider search, assignment, and rating")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping("/buscar")
    @Operation(summary = "Search providers by service/zone")
    public ResponseEntity<ApiResponse<List<ProveedorDTO>>> searchProviders(
            @RequestParam Integer servCodigo, @RequestParam Integer clservCodigo, @RequestParam Long locgeCodigo) {
        return ResponseEntity.ok(ApiResponse.ok(proveedorService.searchProviders(servCodigo, clservCodigo, locgeCodigo)));
    }

    @PostMapping("/{id}/asignar")
    @Operation(summary = "Assign provider")
    public ResponseEntity<ApiResponse<ProveedorDTO>> assignProvider(
            @PathVariable Long id, @RequestParam Long numeroAutorizacion, @RequestParam Long llamadaNumero) {
        return ResponseEntity.ok(ApiResponse.ok(proveedorService.assignProvider(id, numeroAutorizacion, llamadaNumero)));
    }

    @PostMapping("/{id}/calificar")
    @Operation(summary = "Rate provider")
    public ResponseEntity<ApiResponse<Void>> rateProvider(
            @PathVariable Long id, @RequestBody CalificacionProveedorDTO calificacion) {
        calificacion.setConsecutivoPunto(id);
        proveedorService.rateProvider(calificacion);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Calificación registrada").build());
    }

    // === LOV ENDPOINTS ===

    @GetMapping("/lov/buscar")
    @Operation(summary = "LOV: Provider search (SERPRE_DSP_NOMBRE_LOV20 by service/zone with elite flag)")
    public ResponseEntity<ApiResponse<java.util.List<ProveedorDTO>>> lovProveedores(
            @RequestParam Integer servCodigo, @RequestParam Integer clservCodigo, @RequestParam Long locgeCodigo) {
        return ResponseEntity.ok(ApiResponse.ok(proveedorService.searchProviders(servCodigo, clservCodigo, locgeCodigo)));
    }

    @GetMapping("/lov/moviles")
    @Operation(summary = "LOV: Mobile providers (RG_MOVILES with distance/GPS)")
    public ResponseEntity<ApiResponse<java.util.List<ProveedorDTO>>> lovMoviles(
            @RequestParam(required = false) Integer servCodigo,
            @RequestParam(required = false) Long locgeCodigo) {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }
}
