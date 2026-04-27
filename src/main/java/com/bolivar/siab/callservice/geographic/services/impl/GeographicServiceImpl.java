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

        // Step 1: FU_DIRECCION_LIMPIA
        String direccionLimpia;
        try {
            direccionLimpia = storedProcedureRepository.getDireccionLimpia(request.getDireccion());
        } catch (Exception e) {
            log.warn("FU_DIRECCION_LIMPIA error: {}", e.getMessage());
            direccionLimpia = request.getDireccion();
        }

        if (direccionLimpia == null || direccionLimpia.isEmpty()) {
            return GeocodificacionResponseDTO.builder()
                    .direccionFormateada(request.getDireccion())
                    .encontrado(false)
                    .build();
        }

        // Step 2: PR_BUSQUEDA_DIRECCION_INTEGRA
        try {
            storedProcedureRepository.busquedaDireccionIntegra(
                    request.getLocgeCodigo(), direccionLimpia, request.getUsuario());
        } catch (Exception e) {
            log.warn("PR_BUSQUEDA_DIRECCION_INTEGRA error: {}", e.getMessage());
        }

        // Step 3: FU_DIRECCION_UNICA(1) = address, FU_DIRECCION_UNICA(2) = city
        String direccionUnica = null;
        String ciudadUnica = null;
        try {
            direccionUnica = storedProcedureRepository.getDireccionUnica(1);
            ciudadUnica = storedProcedureRepository.getDireccionUnica(2);
        } catch (Exception e) {
            log.warn("FU_DIRECCION_UNICA error: {}", e.getMessage());
        }

        // Step 4: Get city name for fallback
        String nombreCiudad = null;
        try {
            nombreCiudad = storedProcedureRepository.getNombreCiudad(request.getLocgeCodigo());
        } catch (Exception e) {
            log.warn("FU_NOMBRE_CIUDAD error: {}", e.getMessage());
        }

        // Step 5: If INVALIDA, retry with city name
        if (direccionUnica != null && "INVALIDA".equalsIgnoreCase(direccionUnica.trim())) {
            log.info("Address INVALIDA, retrying with city name: {}", nombreCiudad);
            if (nombreCiudad != null) {
                try {
                    storedProcedureRepository.busquedaDireccionIntegra(
                            request.getLocgeCodigo(), nombreCiudad, request.getUsuario());
                    direccionUnica = storedProcedureRepository.getDireccionUnica(1);
                } catch (Exception e) {
                    log.warn("Retry with city name error: {}", e.getMessage());
                }
            }
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
