package com.bolivar.siab.callservice.geographic.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.geographic.dto.*;
import com.bolivar.siab.callservice.geographic.repository.LocalizacionGeograficaRepository;
import com.bolivar.siab.callservice.geographic.services.GeographicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GeographicServiceImpl implements GeographicService {

    private final LocalizacionGeograficaRepository localizacionRepository;
    private final StoredProcedureRepository storedProcedureRepository;
    private final RestTemplate restTemplate;

    @Value("${external-services.geocoding.base-url:http://cls-fee-management-dev-nlb-4ea8dc72491d961a.elb.us-east-1.amazonaws.com/data_source/api/v1/geocoding/forward}")
    private String geocodingUrl;

    public GeographicServiceImpl(LocalizacionGeograficaRepository localizacionRepository,
                                  StoredProcedureRepository storedProcedureRepository) {
        this.localizacionRepository = localizacionRepository;
        this.storedProcedureRepository = storedProcedureRepository;
        this.restTemplate = new RestTemplate();
    }

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
                .pais(row.length > 4 && row[4] != null ? String.valueOf(row[4]) : String.valueOf(pais))
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

        // Get city and department names directly from LOCALIZACIONES_GEOGRAFICAS
        String nombreCiudad = null;
        String nombreDepartamento = null;
        try {
            List<Object[]> rows = localizacionRepository.findCityAndDepartmentByCodigo(request.getLocgeCodigo());
            if (!rows.isEmpty()) {
                nombreCiudad = rows.get(0)[0] != null ? rows.get(0)[0].toString() : null;
                nombreDepartamento = rows.get(0)[1] != null ? rows.get(0)[1].toString() : null;
            }
        } catch (Exception e) {
            log.warn("Error getting city/department: {}", e.getMessage());
        }

        // Call external geocoding service        // Call external geocoding service
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = Map.of(
                    "address", request.getDireccion(),
                    "city", nombreCiudad != null ? nombreCiudad : "",
                    "department", nombreDepartamento != null ? nombreDepartamento : ""
            );

            HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.exchange(geocodingUrl, HttpMethod.POST, httpEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> respBody = response.getBody();

                // Parse coordinates object
                String lat = null;
                String lng = null;
                if (respBody.get("coordinates") instanceof Map) {
                    Map<String, Object> coords = (Map<String, Object>) respBody.get("coordinates");
                    lat = coords.get("latitude") != null ? String.valueOf(coords.get("latitude")) : null;
                    lng = coords.get("longitude") != null ? String.valueOf(coords.get("longitude")) : null;
                }

                String formattedAddress = respBody.get("formattedAddress") != null
                        ? String.valueOf(respBody.get("formattedAddress")) : null;

                boolean found = lat != null && lng != null;
                return GeocodificacionResponseDTO.builder()
                        .latitud(lat)
                        .longitud(lng)
                        .direccionFormateada(found ? (formattedAddress != null ? formattedAddress : request.getDireccion()) : request.getDireccion())
                        .ciudad(nombreCiudad)
                        .encontrado(found)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error calling geocoding service: {}", e.getMessage());
        }

        return GeocodificacionResponseDTO.builder()
                .direccionFormateada(request.getDireccion())
                .ciudad(nombreCiudad)
                .encontrado(false)
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
