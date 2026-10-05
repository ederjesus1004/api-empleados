package dev.eder.empleados.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Lo que el cliente PUEDE ver.
// No está "activo" (es interno) y hay un campo nuevo: nombreCompleto.
public record EmpleadoResponse(
        Long id,
        String nombreCompleto,
        String dni,
        String cargo,
        BigDecimal sueldo,
        LocalDateTime fechaRegistro
) {

}
