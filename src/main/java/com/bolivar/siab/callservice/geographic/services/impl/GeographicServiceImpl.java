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

@Slf4j
@Service
@RequiredArgsConstructor
public class GeographicServiceImpl implements GeographicService {

    private final LocalizacionGeograficaRepository localizacionRepository;
    private final StoredProcedureRepository storedProcedureRepository;

    @Override
    public Page<LocalizacionDTO> searchCities(String nombre, String pais, Pageable pageable) {
        if (pais != null) {
            return localizacionRepository.findByPaisAndTlgCodigo(pais, 3, pageable)
                    .map(e -> LocalizacionDTO.builder().locgeCodigo(e.getLocgeCodigo()).nombre(e.getNombre())
                            .departamento(e.getDepartamento()).pais(e.getPais()).latitud(e.getLatitud()).longitud(e.getLongitud()).build());
        }
        return localizacionRepository.findByNombreContainingIgnoreCaseAndTlgCodigo(nombre != null ? nombre : "", 3, pageable)
                .map(e -> LocalizacionDTO.builder().locgeCodigo(e.getLocgeCodigo()).nombre(e.getNombre())
                        .departamento(e.getDepartamento()).pais(e.getPais()).latitud(e.getLatitud()).longitud(e.getLongitud()).build());
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
        String coordenadas = storedProcedureRepository.getRegistrosCoordenadas(locgeCodigo, direccion);
        return CoordenadasDTO.builder().locgeCodigo(locgeCodigo).direccion(direccion).build();
    }

    @Override
    public String getCityName(Long locgeCodigo) {
        return storedProcedureRepository.getNombreCiudad(locgeCodigo);
    }
}
