package com.bolivar.siab.callservice.poliza.controller;

import com.bolivar.siab.callservice.commons.dto.ApiResponse;
import com.bolivar.siab.callservice.poliza.dto.*;
import com.bolivar.siab.callservice.poliza.repository.RiesgoAseguradoRepository;
import com.bolivar.siab.callservice.poliza.services.PolizaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Policy & Risk", description = "Endpoints for policy and risk management")
public class PolizaController {

    private final PolizaService polizaService;
    private final RiesgoAseguradoRepository riesgoRepository;

    @GetMapping("/riesgos/buscar")
    @Operation(summary = "Search risks by value (LTRIM logic)")
    public ResponseEntity<ApiResponse<RiesgoBusquedaResponseDTO>> searchRisks(@RequestParam String valor) {
        return ResponseEntity.ok(ApiResponse.ok(polizaService.searchRisks(valor)));
    }

    @PostMapping("/riesgos")
    @Operation(summary = "Create risk when not found")
    public ResponseEntity<ApiResponse<RiesgoAseguradoDTO>> createRisk(
            @RequestParam String contNumero, @RequestParam String riesgoCodigo,
            @RequestParam Integer codigoCampo, @RequestParam String valor) {
        return ResponseEntity.ok(ApiResponse.ok(polizaService.createRisk(contNumero, riesgoCodigo, codigoCampo, valor)));
    }

    @GetMapping("/polizas/{contrato}/validar")
    @Operation(summary = "Validate policy with wildcard ASISTBOL fallback")
    public ResponseEntity<ApiResponse<PolizaValidacionDTO>> validatePolicy(@PathVariable String contrato) {
        return ResponseEntity.ok(ApiResponse.ok(polizaService.validatePolicy(contrato)));
    }

    // === LOV ENDPOINTS ===

    @GetMapping("/riesgos/lov/buscar")
    @Operation(summary = "LOV: Risk search (LLAMADA_RIESGO_CODI_LOV8)")
    public ResponseEntity<ApiResponse<RiesgoBusquedaResponseDTO>> lovRiesgos(@RequestParam String valor) {
        return ResponseEntity.ok(ApiResponse.ok(polizaService.searchRisks(valor)));
    }

    @GetMapping("/riesgos/datos-contrato")
    @Operation(summary = "Get user/tomador data for a contract (CGFK$CHK_LLAMADA_LLAMADA_PR2)")
    public ResponseEntity<ApiResponse<java.util.Map<String, Object>>> getDatosContrato(
            @RequestParam String contNumeroContrato) {
        java.util.List<Object[]> rows = riesgoRepository.findDatosUsuarioContrato(contNumeroContrato);
        if (rows.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.ok(java.util.Map.of()));
        }
        Object[] row = rows.get(0);
        java.util.Map<String, Object> result = new java.util.LinkedHashMap<>();
        result.put("usuTipoDocumento", row[0] != null ? row[0].toString() : null);
        result.put("usuNumeroDocumento", row[1] != null ? row[1].toString().trim() : null);
        result.put("nombreUsuario", row[2] != null ? row[2].toString() : null);
        result.put("preferencial", row[3] != null ? row[3].toString() : null);
        result.put("nombreTomador", row[4] != null ? row[4].toString() : null);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/riesgos/campos-busqueda")
    @Operation(summary = "LOV: Risk search fields (RG_RIESGO / LOV_RIESGOS) - all searchable field types")
    public ResponseEntity<ApiResponse<java.util.List<java.util.Map<String, Object>>>> getCamposBusqueda(
            @RequestParam(required = false) String ramo, @RequestParam(required = false) String producto) {
        java.util.List<Object[]> rows;
        if (ramo != null && producto != null) {
            rows = riesgoRepository.findCamposBusqueda(ramo, producto);
        } else {
            rows = riesgoRepository.findAllCamposBusqueda();
        }
        java.util.List<java.util.Map<String, Object>> result = rows.stream().map(row -> {
            java.util.Map<String, Object> map = new java.util.LinkedHashMap<>();
            map.put("codigoCampo", row[0] != null ? Integer.parseInt(row[0].toString()) : null);
            map.put("nombreCampo", row[1] != null ? row[1].toString() : null);
            if (row.length > 2) { map.put("riesgoCodigo", row[2] != null ? row[2].toString() : null); }
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/polizas/lov/productos")
    @Operation(summary = "LOV: Products for risk value (P_PRODUCTOS_CONSULTA + T_PRODUCTOS_CONSULTA)")
    public ResponseEntity<ApiResponse<java.util.List<java.util.Map<String, Object>>>> lovProductos(
            @RequestParam(required = false) String ramo,
            @RequestParam(required = false) String producto,
            @RequestParam(required = false, defaultValue = "1") Long pais,
            @RequestParam String valor,
            @RequestParam Integer codigoCampo) {
        return ResponseEntity.ok(ApiResponse.ok(polizaService.getProductosConsulta(ramo, producto, pais, valor, codigoCampo)));
    }

    @GetMapping("/riesgos/lov/contratos")
    @Operation(summary = "LOV: Contracts/risks for a product (P_RIESGOS_CEDULA + T_RIESGOS_CEDULA)")
    public ResponseEntity<ApiResponse<java.util.List<java.util.Map<String, Object>>>> lovContratos(
            @RequestParam(required = false) String ramo,
            @RequestParam(required = false) String producto,
            @RequestParam String ramo2,
            @RequestParam String producto2,
            @RequestParam String valor,
            @RequestParam(required = false, defaultValue = "1") Long pais) {
        return ResponseEntity.ok(ApiResponse.ok(polizaService.getRiesgosCedula(ramo, producto, ramo2, producto2, valor, pais)));
    }

    @GetMapping("/polizas/lov/productos-bolivar")
    @Operation(summary = "LOV: Bolivar products")
    public ResponseEntity<ApiResponse<java.util.List<com.bolivar.siab.callservice.configuracion.dto.DominioDTO>>> lovProductosBolivar() {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }

    @GetMapping("/polizas/lov/productos-libertador")
    @Operation(summary = "LOV: Libertador products")
    public ResponseEntity<ApiResponse<java.util.List<com.bolivar.siab.callservice.configuracion.dto.DominioDTO>>> lovProductosLibertador() {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }
}
