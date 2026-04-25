package com.bolivar.siab.callservice.integracion.services;

import com.bolivar.siab.callservice.integracion.dto.NotificacionResponseDTO;

public interface NotificationService {
    NotificacionResponseDTO sendEmail(String destinatario, String asunto, String cuerpo, String tipo);
    NotificacionResponseDTO sendSms(String telefono, String mensaje);
}
