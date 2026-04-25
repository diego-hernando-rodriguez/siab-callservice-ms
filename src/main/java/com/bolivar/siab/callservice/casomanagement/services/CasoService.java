package com.bolivar.siab.callservice.casomanagement.services;

import com.bolivar.siab.callservice.casomanagement.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for Case Management (LLAMADAS).
 * Implements the core Call Creation flow business logic.
 */
public interface CasoService {
    CasoResponseDTO createCase(CasoRequestDTO request);
    CasoResponseDTO updateCase(Long numero, CasoRequestDTO request);
    CasoResponseDTO getCaseDetails(Long numero);
    Page<CasoResponseDTO> searchCases(CasoBusquedaDTO criteria, Pageable pageable);
    ReclasificacionResponseDTO reclassifyCase(Long numero, ReclasificacionRequestDTO request);
    boolean validateDuplicateCase(Long locgeCodigo, String contNumero, String riesgoCodigo);
}
