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

    @GetMapping("/riesgos/validar-existente")
    @Operation(summary = "Check if risk exists in non-ASIS contracts (CONSULTA_EXISTENTES)")
    public ResponseEntity<ApiResponse<java.util.Map<String, Object>>> validarExistente(
            @RequestParam String valor,
            @RequestParam(required = false) String ramo,
            @RequestParam(required = false) String producto,
            @RequestParam(required = false) Integer codigoCampo) {
        java.util.List<Object[]> rows = riesgoRepository.consultaExistentes(valor, ramo, producto, codigoCampo);
        boolean existe = !rows.isEmpty();
        java.util.Map<String, Object> result = new java.util.LinkedHashMap<>();
        result.put("existe", existe);
        result.put("indicador", existe ? "S" : "N");
        if (!existe) {
            result.put("mensaje", "Riesgo no tiene Cargue. Consulte en TRONADOR");
        }
        return ResponseEntity.ok(ApiResponse.ok(result));
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

    @GetMapping("/riesgos/lov/autos")
    @Operation(summary = "LOV: Contracts for a plate (RIESGOS_AUTOS) - used when codigo_campo=1")
    public ResponseEntity<ApiResponse<java.util.List<java.util.Map<String, Object>>>> lovRiesgosAutos(
            @RequestParam String valor,
            @RequestParam(required = false) String ramo,
            @RequestParam(required = false) String producto,
            @RequestParam(required = false, defaultValue = "1") Long pais) {
        java.util.List<Object[]> rows = riesgoRepository.findRiesgosAutos(valor, ramo, producto, pais);
        java.util.List<java.util.Map<String, Object>> result = rows.stream().map(row -> {
            java.util.Map<String, Object> map = new java.util.LinkedHashMap<>();
            map.put("producto", row[0] != null ? row[0].toString() : null);
            map.put("poliza", row[1] != null ? row[1].toString() : null);
            map.put("inicio", row[2] != null ? row[2].toString() : null);
            map.put("fin", row[3] != null ? row[3].toString() : null);
            map.put("estado", row[4] != null ? row[4].toString() : null);
            map.put("valorRiesgo", row[5] != null ? row[5].toString() : null);
            map.put("numOrden", row[6] != null ? Long.parseLong(row[6].toString()) : null);
            map.put("tipContrato", row[7] != null ? Integer.parseInt(row[7].toString()) : null);
            map.put("riesgo", row[8] != null ? row[8].toString() : null);
            map.put("ramoCodigo", row[9] != null ? row[9].toString() : null);
            map.put("productoCodigo", row[10] != null ? row[10].toString() : null);
            map.put("descRiesgo", row[11] != null ? row[11].toString() : null);
            map.put("numeroDocumento", row[12] != null ? row[12].toString().trim() : null);
            map.put("nombresApellidos", row[13] != null ? row[13].toString() : null);
            map.put("tipoDocumento", row[14] != null ? row[14].toString() : null);
            map.put("preferencial", row[15] != null ? row[15].toString() : null);
            map.put("codigoCampo", row[16] != null ? Integer.parseInt(row[16].toString()) : null);
            map.put("valorRiesgoOri", row[17] != null ? row[17].toString() : null);
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(result));
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

    @GetMapping("/usuarios/lov/buscar")
    @Operation(summary = "LOV: User search by document or name (LLAMADA_DSP_USU_NUM_LOV1)")
    public ResponseEntity<ApiResponse<java.util.List<java.util.Map<String, Object>>>> lovUsuarios(
            @RequestParam String query) {
        java.util.List<Object[]> rows = riesgoRepository.findUsuariosByDocOrName(query);
        java.util.List<java.util.Map<String, Object>> result = rows.stream().map(row -> {
            java.util.Map<String, Object> map = new java.util.LinkedHashMap<>();
            map.put("numeroDocumento", row[0] != null ? row[0].toString().trim() : null);
            map.put("nombresApellidos", row[1] != null ? row[1].toString() : null);
            map.put("tipoDocumento", row[2] != null ? row[2].toString() : null);
            map.put("preferencial", row[3] != null ? row[3].toString() : null);
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/usuarios/lov/contratos")
    @Operation(summary = "LOV: Contracts for a user (LLAMADA_DSP_USUARIO_LOV2)")
    public ResponseEntity<ApiResponse<java.util.List<java.util.Map<String, Object>>>> lovContratosUsuario(
            @RequestParam String numeroDocumento, @RequestParam String tipoDocumento,
            @RequestParam(required = false) Integer codigoCampo) {
        java.util.List<Object[]> rows = riesgoRepository.findContratosByUsuario(
                numeroDocumento, tipoDocumento, codigoCampo != null ? codigoCampo : 1);
        java.util.List<java.util.Map<String, Object>> result = rows.stream().map(row -> {
            java.util.Map<String, Object> map = new java.util.LinkedHashMap<>();
            map.put("contNumero", row[0] != null ? row[0].toString() : null);
            map.put("fechaInicio", row[1] != null ? row[1].toString() : null);
            map.put("fechaFin", row[2] != null ? row[2].toString() : null);
            map.put("numOrden", row[3] != null ? Long.parseLong(row[3].toString()) : null);
            map.put("tipContrato", row[4] != null ? Integer.parseInt(row[4].toString()) : null);
            map.put("riesgo", row[5] != null ? row[5].toString() : null);
            map.put("valorRiesgo", row[6] != null ? row[6].toString() : null);
            map.put("ramoCodigo", row[7] != null ? row[7].toString() : null);
            map.put("productoCodigo", row[8] != null ? row[8].toString() : null);
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}
