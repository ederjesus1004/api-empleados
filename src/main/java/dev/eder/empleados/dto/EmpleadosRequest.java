package dev.eder.empleados.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Lo que el cliente PUEDE mandar, ahora con las reglas de cada campo
public record EmpleadosRequest(
    @NotBlank (message = "El nombre es obligatorio")
        @Size (max = 100, message = "El nombre no puede tener más de 100 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El apellido no puede tener más de 100 caracteres")
        String apellido,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern (regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos")
        String dni,

        @NotBlank(message = "El cargo es obligatorio")
        @Size(max = 50, message = "El cargo no puede tener más de 50 caracteres")
        String cargo,

        @NotNull (message = "El sueldo es obligatorio")
        @Positive (message = "El sueldo debe ser mayor a 0")
        BigDecimal sueldo
) {

}
