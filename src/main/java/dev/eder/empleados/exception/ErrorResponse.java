package dev.eder.empleados.exception;

import java.time.LocalDateTime;
import java.util.Map;

// Es un DTO de salida, pero para ERRORES.
// Todos los errores de la API tendrán esta misma forma.
public record ErrorResponse(
    LocalDateTime fecha,
    int estado,
    String mensaje,
    Map<String, String> errores   // detalle por campo, solo en validaciones
) {

}
