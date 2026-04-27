package com.bolivar.siab.callservice.configuracion.controller;

import com.bolivar.siab.callservice.commons.dto.ApiResponse;
import com.bolivar.siab.callservice.configuracion.dto.DescriptorDTO;
import com.bolivar.siab.callservice.configuracion.dto.DominioDTO;
import com.bolivar.siab.callservice.configuracion.dto.VariableGlobalDTO;
import com.bolivar.siab.callservice.configuracion.services.ConfiguracionService;
import com.bolivar.siab.callservice.configuracion.services.DescriptorService;
import com.bolivar.siab.callservice.configuracion.services.PicoPlacaService;
import com.bolivar.siab.callservice.configuracion.services.VariablesGlobalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Configuration", description = "Endpoints for domain lookups, descriptors, and global variables")
public class ConfiguracionController {

    private final ConfiguracionService configuracionService;
    private final DescriptorService descriptorService;
    private final VariablesGlobalesService variablesGlobalesService;
    private final PicoPlacaService picoPlacaService;

    @GetMapping("/dominios/{dominio}/{referencia}")
    @Operation(summary = "Get domain value via PKG_UTILITARIOS.FN_VAL_DOMINIO")
    public ResponseEntity<ApiResponse<String>> getDominioValue(
            @PathVariable String dominio, @PathVariable String referencia) {
        return ResponseEntity.ok(ApiResponse.ok(configuracionService.getValDominio(dominio, referencia)));
    }

    @GetMapping("/dominios/{dominio}")
    @Operation(summary = "Get all values for a domain from CG_REF_CODES")
    public ResponseEntity<ApiResponse<List<DominioDTO>>> getDomainValues(@PathVariable String dominio) {
        return ResponseEntity.ok(ApiResponse.ok(configuracionService.getDomainValues(dominio)));
    }

    @GetMapping("/descriptores/{tipo}/{codigo}")
    @Operation(summary = "Get descriptor via PKG_DESCRIPTORES")
    public ResponseEntity<ApiResponse<DescriptorDTO>> getDescriptor(
            @PathVariable String tipo, @PathVariable String codigo,
            @RequestParam(required = false) Integer ramo,
            @RequestParam(required = false) Integer producto) {
        String descripcion;
        switch (tipo.toUpperCase()) {
            case "RAMO": descripcion = descriptorService.getDescriptorRamo(Integer.parseInt(codigo)); break;
            case "PRODUCTO": descripcion = descriptorService.getDescriptorProducto(
                    ramo != null ? ramo : 0, Integer.parseInt(codigo)); break;
            case "CAUSA": descripcion = descriptorService.getDescriptorCausa(
                    ramo != null ? ramo : 0, producto != null ? producto : 0, Long.parseLong(codigo)); break;
            case "CARACTERISTICA": descripcion = descriptorService.getDescriptorCaracteristicas(Integer.parseInt(codigo)); break;
            case "ENTIDAD": descripcion = descriptorService.getDescriptorEntidad(Long.parseLong(codigo)); break;
            default: descripcion = "Tipo no soportado: " + tipo;
        }
        return ResponseEntity.ok(ApiResponse.ok(
                DescriptorDTO.builder().tipo(tipo).codigo(codigo).descripcion(descripcion).build()));
    }

    @GetMapping("/variables-globales")
    @Operation(summary = "Get all global variables via PKG_VARIABLES_GLOBALES")
    public ResponseEntity<ApiResponse<List<VariableGlobalDTO>>> getVariablesGlobales() {
        return ResponseEntity.ok(ApiResponse.ok(variablesGlobalesService.getAllVariables()));
    }

    @GetMapping("/pico-placa")
    @Operation(summary = "Evaluate Pico y Placa restriction for Bogota")
    public ResponseEntity<ApiResponse<String>> evaluatePicoPlaca(
            @RequestParam String placa, @RequestParam Long locgeCodigo) {
        return ResponseEntity.ok(ApiResponse.ok(picoPlacaService.evaluateAlertaPyp(placa, locgeCodigo)));
    }

    // === LOV ENDPOINTS ===

    @GetMapping("/dominios/lov/tipos-documento")
    @Operation(summary = "LOV: Document types (RG_TIPO_DOCUMENTO from CG_REF_CODES)")
    public ResponseEntity<ApiResponse<List<DominioDTO>>> lovTiposDocumento() {
        return ResponseEntity.ok(ApiResponse.ok(configuracionService.getDomainValues("TIPO_IDENTIFICACION")));
    }

    @GetMapping("/dominios/lov/lineas-negocio")
    @Operation(summary = "LOV: Business lines (RG_LINEA_NEGOCIO)")
    public ResponseEntity<ApiResponse<List<DominioDTO>>> lovLineasNegocio() {
        return ResponseEntity.ok(ApiResponse.ok(configuracionService.getDomainValues("LINEA_NEGOCIO")));
    }

    @GetMapping("/dominios/lov/listas-dinamicas")
    @Operation(summary = "LOV: Dynamic lists (RG_LISTA_DINAMICA)")
    public ResponseEntity<ApiResponse<List<DominioDTO>>> lovListasDinamicas(
            @RequestParam(required = false) String dominio) {
        return ResponseEntity.ok(ApiResponse.ok(configuracionService.getDomainValues(dominio != null ? dominio : "LISTA_DINAMICA")));
    }

    @GetMapping("/dominios/lov/severidad-evento")
    @Operation(summary = "LOV: Event severity levels")
    public ResponseEntity<ApiResponse<List<DominioDTO>>> lovSeveridadEvento() {
        return ResponseEntity.ok(ApiResponse.ok(configuracionService.getDomainValues("SEVERIDAD_EVENTO")));
    }
}
