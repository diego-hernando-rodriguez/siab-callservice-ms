package com.bolivar.siab.callservice.configuracion.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.configuracion.services.PicoPlacaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service wrapping PKG_PICO_PLACA for Bogota vehicle restriction alerts.
 * Applies for Bogota (city 14000) with plates of 6 characters.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PicoPlacaServiceImpl implements PicoPlacaService {

    private static final Long BOGOTA_CODE = 14000L;
    private static final int PLATE_LENGTH = 6;

    private final StoredProcedureRepository storedProcedureRepository;

    @Override
    public String getTipoRestriccion(Long locgeCodigo) {
        return storedProcedureRepository.getTipoRestriccion(locgeCodigo);
    }

    @Override
    public String getPicoPlaca(String placa, Long locgeCodigo) {
        return storedProcedureRepository.getPicoPlaca(placa, locgeCodigo);
    }

    @Override
    public String getRestriccion(String placa, Long locgeCodigo) {
        return storedProcedureRepository.getRestriccion(placa, locgeCodigo);
    }

    @Override
    public String evaluateAlertaPyp(String placa, Long locgeCodigo) {
        // SQL-05: Prevention alert for plates in Bogota with 6 chars
        if (placa != null && placa.length() == PLATE_LENGTH && BOGOTA_CODE.equals(locgeCodigo)) {
            try {
                return storedProcedureRepository.getPicoPlaca(placa, locgeCodigo);
            } catch (Exception e) {
                log.warn("Error evaluating pico y placa for plate {}: {}", placa, e.getMessage());
            }
        }
        return null;
    }
}
