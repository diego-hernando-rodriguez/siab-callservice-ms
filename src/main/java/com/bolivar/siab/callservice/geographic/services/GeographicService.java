package com.bolivar.siab.callservice.geographic.services;

import com.bolivar.siab.callservice.geographic.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GeographicService {
    Page<LocalizacionDTO> searchCities(String nombre, String pais, Pageable pageable);
    String getPais(Long locgeCodigo, Integer tlgCodigo);
    GeocodificacionResponseDTO geocodeAddress(GeocodificacionRequestDTO request);
    CoordenadasDTO getCoordinates(Long locgeCodigo, String direccion);
    String getCityName(Long locgeCodigo);
}
