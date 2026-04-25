package com.bolivar.siab.callservice.caracteristicas.controller;

import com.bolivar.siab.callservice.caracteristicas.dto.*;
import com.bolivar.siab.callservice.caracteristicas.services.CaracteristicaService;
import com.bolivar.siab.callservice.commons.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Characteristics", description = "Endpoints for cause characteristics (CALLA block)")
public class CaracteristicaController {

    private final CaracteristicaService caracteristicaService;

    @GetMapping("/casos/{numeroCaso}/caracteristicas")
    @Operation(summary = "Load cause characteristics for a case")
    public ResponseEntity<ApiResponse<CaracteristicaCausaResponseDTO>> loadCharacteristics(
            @PathVariable Long numeroCaso,
            @RequestParam Integer ramo, @RequestParam Integer producto, @RequestParam Long causa) {
        return ResponseEntity.ok(ApiResponse.ok(caracteristicaService.loadCharacteristics(numeroCaso, ramo, producto, causa)));
    }

    @PostMapping("/casos/{numeroCaso}/caracteristicas")
    @Operation(summary = "Save cause characteristics")
    public ResponseEntity<ApiResponse<Void>> saveCharacteristics(
            @PathVariable Long numeroCaso, @RequestBody CaracteristicaCausaRequestDTO request) {
        request.setLlamadaNumero(numeroCaso);
        caracteristicaService.saveCharacteristics(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Características guardadas").build());
    }

    @GetMapping("/caracteristicas/tipos/{causaCodigo}")
    @Operation(summary = "Get characteristic types for a cause")
    public ResponseEntity<ApiResponse<List<TipoDatoCaracteristicaDTO>>> getTypes(
            @PathVariable Long causaCodigo, @RequestParam Integer ramo, @RequestParam Integer producto) {
        return ResponseEntity.ok(ApiResponse.ok(caracteristicaService.getCharacteristicTypesForCause(causaCodigo, ramo, producto)));
    }

    @GetMapping("/casos/{numeroCaso}/caracteristicas/auto-fill")
    @Operation(summary = "Auto-fill characteristics with business rules")
    public ResponseEntity<ApiResponse<CaracteristicaCausaResponseDTO>> autoFill(
            @PathVariable Long numeroCaso,
            @RequestParam Integer ramo, @RequestParam Integer producto, @RequestParam Long causa,
            @RequestParam(required = false) String contNumero,
            @RequestParam(required = false) String riesgoCodigo,
            @RequestParam(required = false) Long locgeCodigo,
            @RequestParam(required = false) String direccion) {
        return ResponseEntity.ok(ApiResponse.ok(caracteristicaService.autoFillCharacteristics(
                numeroCaso, ramo, producto, causa, contNumero, riesgoCodigo, locgeCodigo, direccion)));
    }
}
