package com.bolivar.siab.callservice.geographic.controller;

import com.bolivar.siab.callservice.commons.dto.ApiResponse;
import com.bolivar.siab.callservice.geographic.dto.*;
import com.bolivar.siab.callservice.geographic.models.LocalizacionGeograficaEntity;
import com.bolivar.siab.callservice.geographic.repository.LocalizacionGeograficaRepository;
import com.bolivar.siab.callservice.geographic.services.GeographicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Geographic", description = "Endpoints for geographic and address services")
public class GeographicController {

    private final GeographicService geographicService;
    private final LocalizacionGeograficaRepository localizacionRepository;

    @GetMapping("/localizaciones")
    @Operation(summary = "Search cities")
    public ResponseEntity<ApiResponse<Page<LocalizacionDTO>>> searchCities(
            @RequestParam(required = false) String nombre, @RequestParam(required = false) String pais, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(geographicService.searchCities(nombre, pais, pageable)));
    }

    @GetMapping("/localizaciones/{codigo}/pais")
    @Operation(summary = "Get country for location")
    public ResponseEntity<ApiResponse<String>> getPais(@PathVariable Long codigo, @RequestParam(defaultValue = "3") Integer tlgCodigo) {
        return ResponseEntity.ok(ApiResponse.ok(geographicService.getPais(codigo, tlgCodigo)));
    }

    @PostMapping("/direcciones/geocodificar")
    @Operation(summary = "Geocode address")
    public ResponseEntity<ApiResponse<GeocodificacionResponseDTO>> geocode(@RequestBody GeocodificacionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.ok(geographicService.geocodeAddress(request)));
    }

    @GetMapping("/direcciones/coordenadas")
    @Operation(summary = "Get coordinates")
    public ResponseEntity<ApiResponse<CoordenadasDTO>> getCoordinates(
            @RequestParam Long locgeCodigo, @RequestParam String direccion) {
        return ResponseEntity.ok(ApiResponse.ok(geographicService.getCoordinates(locgeCodigo, direccion)));
    }

    // === LOV ENDPOINTS ===

    @GetMapping("/localizaciones/lov/paises")
    @Operation(summary = "LOV: Countries (LOCALIZACIONES_GEOGRAFICAS WHERE TLG_CODIGO=1)")
    public ResponseEntity<ApiResponse<java.util.List<LocalizacionDTO>>> lovPaises() {
        java.util.List<LocalizacionGeograficaEntity> paises = localizacionRepository.findByTlgCodigoOrderByNombreAsc(1);
        java.util.List<LocalizacionDTO> result = paises.stream()
                .map(p -> LocalizacionDTO.builder()
                        .locgeCodigo(p.getCodigo())
                        .tlgCodigo(p.getTlgCodigo())
                        .nombre(p.getNombre())
                        .build())
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/localizaciones/lov/ciudades")
    @Operation(summary = "LOV: City search with department (LLAMADA_LOCGE_CODIG_LOV5)")
    public ResponseEntity<ApiResponse<java.util.List<LocalizacionDTO>>> lovCiudades(
            @RequestParam(defaultValue = "1") Long pais,
            @RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(ApiResponse.ok(geographicService.searchCitiesByPais(pais, query)));
    }

    @GetMapping("/direcciones/lov/google")
    @Operation(summary = "LOV: Google Maps addresses (LOV_DIRECCIONES_GOOGLE)")
    public ResponseEntity<ApiResponse<java.util.List<CoordenadasDTO>>> lovDireccionesGoogle(
            @RequestParam Long locgeCodigo, @RequestParam String direccion) {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of(geographicService.getCoordinates(locgeCodigo, direccion))));
    }

    @GetMapping("/direcciones/lov/puntos-referencia")
    @Operation(summary = "LOV: Reference points (LOV_PUNTOS_REFERENCIA from USR_CARTOG)")
    public ResponseEntity<ApiResponse<java.util.List<CoordenadasDTO>>> lovPuntosReferencia(
            @RequestParam(required = false) String query) {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }

    @GetMapping("/direcciones/lov/barrios")
    @Operation(summary = "LOV: Neighborhoods (LOV_BARRIOS from USR_CARTOG)")
    public ResponseEntity<ApiResponse<java.util.List<CoordenadasDTO>>> lovBarrios(
            @RequestParam(required = false) String query) {
        return ResponseEntity.ok(ApiResponse.ok(java.util.List.of()));
    }
}
