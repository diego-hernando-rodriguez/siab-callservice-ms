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

    @Override
    public GeocodificacionResponseDTO geocodeAddress(GeocodificacionRequestDTO request) {
        log.info("Geocoding address: {} for city: {}", request.getDireccion(), request.getLocgeCodigo());
        storedProcedureRepository.busquedaDireccionIntegra(request.getLocgeCodigo(), request.getDireccion(), request.getUsuario());
        String coordenadas = storedProcedureRepository.getRegistrosCoordenadas(request.getLocgeCodigo(), request.getDireccion());
        String ciudad = storedProcedureRepository.getNombreCiudad(request.getLocgeCodigo());
        return GeocodificacionResponseDTO.builder()
                .direccionFormateada(request.getDireccion())
                .ciudad(ciudad)
                .encontrado(coordenadas != null && !coordenadas.isEmpty())
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
