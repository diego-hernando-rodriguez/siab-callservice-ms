package com.bolivar.siab.callservice.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standard API response wrapper for all REST endpoints.
 * Provides a consistent response structure across the application.
 *
 * @param <T> the type of the response data payload
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta estándar de la API para todos los endpoints REST")
public class ApiResponse<T> {

    @Builder.Default
    @Schema(description = "Indica si la operación fue exitosa", example = "true")
    private boolean success = true;

    @Schema(description = "Mensaje descriptivo del resultado o error", example = "Operación exitosa")
    private String message;

    @Schema(description = "Código de error cuando success=false", example = "VALIDATION_ERROR")
    private String errorCode;

    @Schema(description = "Datos de respuesta de la operación")
    private T data;

    @Builder.Default
    @Schema(description = "Marca de tiempo de la respuesta", example = "2025-01-15T10:30:00")
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Creates a successful response with data.
     */
    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    /**
     * Creates a successful response with data and message.
     */
    public static <T> ApiResponse<T> ok(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }

    /**
     * Creates an error response.
     */
    public static <T> ApiResponse<T> error(String errorCode, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}
