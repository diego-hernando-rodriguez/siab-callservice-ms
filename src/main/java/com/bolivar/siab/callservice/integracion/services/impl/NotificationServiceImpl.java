package com.bolivar.siab.callservice.integracion.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.integracion.dto.NotificacionResponseDTO;
import com.bolivar.siab.callservice.integracion.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final StoredProcedureRepository storedProcedureRepository;

    @Override
    public NotificacionResponseDTO sendEmail(String destinatario, String asunto, String cuerpo, String tipo) {
        log.info("Sending email to: {}, subject: {}", destinatario, asunto);
        try {
            storedProcedureRepository.enviarCorreo(destinatario, asunto, cuerpo, tipo);
            return NotificacionResponseDTO.builder().enviado(true).resultado("Email enviado exitosamente").build();
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
            return NotificacionResponseDTO.builder().enviado(false).resultado("Error: " + e.getMessage()).build();
        }
    }

    @Override
    public NotificacionResponseDTO sendSms(String telefono, String mensaje) {
        log.info("Sending SMS to: {}", telefono);
        try {
            // SMS is sent via PKG_CORREOS or P_ENVIAR_MENSAJE database procedure
            storedProcedureRepository.enviarCorreo(telefono, "SMS", mensaje, "SMS");
            return NotificacionResponseDTO.builder().enviado(true).resultado("SMS enviado exitosamente").build();
        } catch (Exception e) {
            log.error("Error sending SMS: {}", e.getMessage());
            return NotificacionResponseDTO.builder().enviado(false).resultado("Error: " + e.getMessage()).build();
        }
    }
}
