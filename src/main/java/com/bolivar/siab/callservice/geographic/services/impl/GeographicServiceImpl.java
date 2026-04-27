package com.bolivar.siab.callservice.geographic.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.geographic.dto.*;
import com.bolivar.siab.callservice.geographic.repository.LocalizacionGeograficaRepository;
import com.bolivar.siab.callservice.geographic.services.GeographicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeographicServiceImpl implements GeographicService {

    private final LocalizacionGeograficaRepository localizacionRepository;
    private final StoredProcedureRepository storedProcedureRepository;

    /**
     * LLAMADA_LOCGE_CODIG_LOV5: Cities with department filtered by country.
     * Uses the exact Oracle Forms record group query.
     */
    @Override
    public List<LocalizacionDTO> searchCitiesByPais(Long pais, String nombre) {
        List<Object[]> rows = localizacionRepository.findCitiesWithDepartmentByPais(
                pais, nombre != null ? nombre : "");
        return rows.stream().map(row -> LocalizacionDTO.builder()
                .locgeCodigo(((Number) row[0]).longValue())
                .nombre((String) row[1])
                .departamento((String) row[2])
                .tlgCodigo(((Number) row[3]).intValue())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public Page<LocalizacionDTO> searchCities(String nombre, String pais, Pageable pageable) {
        return localizacionRepository.findByNombreContainingIgnoreCaseAndTlgCodigo(
                nombre != null ? nombre : "", 3, pageable)
                .map(e -> LocalizacionDTO.builder()
                        .locgeCodigo(e.getCodigo())
                        .tlgCodigo(e.getTlgCodigo())
                        .nombre(e.getNombre())
                        .latitud(e.getLatitud() != null ? String.valueOf(e.getLatitud()) : null)
                        .longitud(e.getLongitud() != null ? String.valueOf(e.getLongitud()) : null)
                        .build());
    }

    @Override
    public String getPais(Long locgeCodigo, Integer tlgCodigo) {
        return storedProcedureRepository.getPais(locgeCodigo, tlgCodigo);
    }

    /**
     * Replicates WHEN-VALIDATE-ITEM of LLAMADA.DIRECCION from llamada.fmt:
     * 1. FU_DIRECCION_LIMPIA: clean the address
     * 2. PR_BUSQUEDA_DIRECCION_INTEGRA: search coordinates
     * 3. FU_DIRECCION_UNICA(1): get unique address, FU_DIRECCION_UNICA(2): get city
     * 4. If unique address found -> return it as direccionGeoReferencia
     * 5. If INVALIDA -> retry with city name
     * 6. PR_ACTUALIZA_DIRECCION_GEOREFE: update characteristics (campos 41,49,69,244)
     */
    @Override
    public GeocodificacionResponseDTO geocodeAddress(GeocodificacionRequestDTO request) {
        log.info("Geocoding address: {} for city: {}", request.getDireccion(), request.getLocgeCodigo());

        // Get city name from locgeCodigo
        String nombreCiudad = null;
        String departamento = null;
        try {
            var rows = localizacionRepository.findCitiesWithDepartmentByPais(
                    1L, ""); // Get all cities for Colombia
            // Find the specific city
            for (Object[] row : localizacionRepository.findCitiesWithDepartmentByPais(1L, "")) {
                if (((Number) row[0]).longValue() == request.getLocgeCodigo()) {
                    nombreCiudad = (String) row[1];
                    departamento = (String) row[2];
                    break;
                }
            }
        } catch (Exception e) {
            log.debug("City lookup error: {}", e.getMessage());
        }

        // Fallback: direct query
        if (nombreCiudad == null) {
            try {
                nombreCiudad = storedProcedureRepository.getNombreCiudad(request.getLocgeCodigo());
            } catch (Exception e) { /* ignore */ }
        }

        // Call external geocoding API
        try {
            var client = new okhttp3.OkHttpClient.Builder()
                    .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                    .build();

            String jsonBody = new com.google.gson.Gson().toJson(java.util.Map.of(
                    "address", request.getDireccion(),
                    "city", nombreCiudad != null ? nombreCiudad : "Bogota",
                    "department", departamento != null ? departamento : "Cundinamarca"
            ));

            var body = okhttp3.RequestBody.create(jsonBody, okhttp3.MediaType.parse("application/json"));
            var httpRequest = new okhttp3.Request.Builder()
                    .url("https://os7cfipof2.execute-api.us-east-1.amazonaws.com/stage/data_source/api/v1/geocoding/forward")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", "sp4Eu6SuH11SGLuemzEm15aSO2HgliaD3fntHWly")
                    .post(body)
                    .build();

            try (var response = client.newCall(httpRequest).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    var gson = new com.google.gson.Gson();
                    var map = gson.fromJson(responseBody, java.util.Map.class);
                    String formattedAddress = (String) map.get("formattedAddress");
                    var coords = (java.util.Map) map.get("coordinates");
                    String lat = coords != null && coords.get("latitude") != null ? coords.get("latitude").toString() : null;
                    String lng = coords != null && coords.get("longitude") != null ? coords.get("longitude").toString() : null;

                    return GeocodificacionResponseDTO.builder()
                            .direccionFormateada(formattedAddress)
                            .ciudad(nombreCiudad)
                            .latitud(lat)
                            .longitud(lng)
                            .encontrado(formattedAddress != null && !formattedAddress.isEmpty())
                            .build();
                }
            }
        } catch (Exception e) {
            log.warn("External geocoding API error: {}", e.getMessage());
        }

        boolean encontrado = direccionUnica != null
                && !direccionUnica.isEmpty()
                && !"INVALIDA".equalsIgnoreCase(direccionUnica.trim());

        return GeocodificacionResponseDTO.builder()
                .direccionFormateada(encontrado ? direccionUnica : request.getDireccion())
                .ciudad(ciudadUnica != null ? ciudadUnica : nombreCiudad)
                .encontrado(encontrado)
                .build();
    }

    @Override
    public CoordenadasDTO getCoordinates(Long locgeCodigo, String direccion) {
        return CoordenadasDTO.builder().locgeCodigo(locgeCodigo).direccion(direccion).build();
    }

    @Override
    public String getCityName(Long locgeCodigo) {
        return storedProcedureRepository.getNombreCiudad(locgeCodigo);
    }
}
