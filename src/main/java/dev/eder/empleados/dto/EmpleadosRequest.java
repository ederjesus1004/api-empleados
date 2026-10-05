package dev.eder.empleados.dto;

import java.math.BigDecimal;

// Lo que el cliente PUEDE mandar.
// Fíjate en lo que NO está: id, activo, fechaRegistro.
public record EmpleadosRequest(
    String nombre,
    String apellido,
    String dni,
    String cargo,
    BigDecimal sueldo
) {

}
