package com.bolivar.siab.callservice.serviciomanagement.services;

import com.bolivar.siab.callservice.serviciomanagement.dto.*;
import java.util.List;

public interface ServicioService {
    ServicioPrestadoResponseDTO createService(ServicioPrestadoRequestDTO request);
    ServicioPrestadoResponseDTO updateService(Long numeroAutorizacion, ServicioPrestadoRequestDTO request);
    List<ServicioPrestadoResponseDTO> listServicesForCase(Long numeroCaso);
    void cancelService(Long numeroAutorizacion, String motivo);
    ServicioPrestadoResponseDTO assignProvider(Long numeroAutorizacion, Long consecutivoPunto);
    List<AdicionalesPrestadosDTO> manageAdditionalServices(Long llamadaNumero, Long numeroAutorizacion, List<AdicionalesPrestadosDTO> adicionales);
    IngresoResponseDTO createIncome(IngresoRequestDTO request);
    List<IngresoResponseDTO> getIncomesByService(Long numeroAutorizacion);
}
