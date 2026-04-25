package com.bolivar.siab.callservice.casomanagement.controller;

import com.bolivar.siab.callservice.casomanagement.dto.*;
import com.bolivar.siab.callservice.casomanagement.services.CasoService;
import com.bolivar.siab.callservice.commons.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/casos")
@RequiredArgsConstructor
@Tag(name = "Case Management", description = "Endpoints for case (llamada) management")
public class CasoController {

    private final CasoService casoService;

    @PostMapping
    @Operation(summary = "Create a new case")
    public ResponseEntity<ApiResponse<CasoResponseDTO>> createCase(@Valid @RequestBody CasoRequestDTO request) {
        CasoResponseDTO response = casoService.createCase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response, "Caso creado exitosamente"));
    }

    @PutMapping("/{numero}")
    @Operation(summary = "Update an existing case")
    public ResponseEntity<ApiResponse<CasoResponseDTO>> updateCase(
            @PathVariable Long numero, @Valid @RequestBody CasoRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.ok(casoService.updateCase(numero, request)));
    }

    @GetMapping("/{numero}")
    @Operation(summary = "Get case details")
    public ResponseEntity<ApiResponse<CasoResponseDTO>> getCaseDetails(@PathVariable Long numero) {
        return ResponseEntity.ok(ApiResponse.ok(casoService.getCaseDetails(numero)));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Search cases")
    public ResponseEntity<ApiResponse<Page<CasoResponseDTO>>> searchCases(
            CasoBusquedaDTO criteria, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(casoService.searchCases(criteria, pageable)));
    }

    @PostMapping("/{numero}/reclasificar")
    @Operation(summary = "Reclassify case cause")
    public ResponseEntity<ApiResponse<ReclasificacionResponseDTO>> reclassifyCase(
            @PathVariable Long numero, @Valid @RequestBody ReclasificacionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.ok(casoService.reclassifyCase(numero, request)));
    }

    @GetMapping("/validar-duplicado")
    @Operation(summary = "Validate duplicate case (VALIDA_CASO_ATENDIDO)")
    public ResponseEntity<ApiResponse<Boolean>> validateDuplicate(
            @RequestParam Long locgeCodigo,
            @RequestParam String contNumero,
            @RequestParam String riesgoCodigo) {
        boolean isDuplicate = casoService.validateDuplicateCase(locgeCodigo, contNumero, riesgoCodigo);
        return ResponseEntity.ok(ApiResponse.ok(isDuplicate));
    }

    // === LOV ENDPOINTS ===

    @GetMapping("/lov/contratos")
    @Operation(summary = "LOV: Search contracts (LLAMADA_CONT_NUMERO_LOV0)")
    public ResponseEntity<ApiResponse<Page<CasoResponseDTO>>> lovContratos(
            @RequestParam(required = false) String query, Pageable pageable) {
        CasoBusquedaDTO criteria = CasoBusquedaDTO.builder().contNumero(query).build();
        return ResponseEntity.ok(ApiResponse.ok(casoService.searchCases(criteria, pageable)));
    }

    @GetMapping("/lov/usuarios")
    @Operation(summary = "LOV: Search users by document (LLAMADA_DSP_USU_NUM_LOV1)")
    public ResponseEntity<ApiResponse<Page<CasoResponseDTO>>> lovUsuarios(
            @RequestParam(required = false) String query, Pageable pageable) {
        CasoBusquedaDTO criteria = CasoBusquedaDTO.builder().usuNumeroDocumento(query).build();
        return ResponseEntity.ok(ApiResponse.ok(casoService.searchCases(criteria, pageable)));
    }

    @GetMapping("/lov/ramos")
    @Operation(summary = "LOV: List ramos for reclassification")
    public ResponseEntity<ApiResponse<java.util.List<com.bolivar.siab.callservice.configuracion.dto.DominioDTO>>> lovRamos() {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }

    @GetMapping("/lov/productos")
    @Operation(summary = "LOV: List products for reclassification")
    public ResponseEntity<ApiResponse<java.util.List<com.bolivar.siab.callservice.configuracion.dto.DominioDTO>>> lovProductos(
            @RequestParam(required = false) Integer ramo) {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }

    @GetMapping("/lov/causas")
    @Operation(summary = "LOV: List causes for reclassification")
    public ResponseEntity<ApiResponse<java.util.List<com.bolivar.siab.callservice.configuracion.dto.DominioDTO>>> lovCausas(
            @RequestParam(required = false) Integer ramo, @RequestParam(required = false) Integer producto) {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }

    @GetMapping("/lov/razones")
    @Operation(summary = "LOV: List reasons for reclassification")
    public ResponseEntity<ApiResponse<java.util.List<com.bolivar.siab.callservice.configuracion.dto.DominioDTO>>> lovRazones() {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }
}
