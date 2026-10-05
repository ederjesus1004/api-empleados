package dev.eder.empleados.mapper;

import org.springframework.stereotype.Component;

import dev.eder.empleados.Entity.Empleado;
import dev.eder.empleados.dto.EmpleadoResponse;
import dev.eder.empleados.dto.EmpleadosRequest;

// El TRADUCTOR entre DTOs y entidad.
// @Component: Spring crea un objeto de esta clase y lo inyecta en el service.
@Component
public class EmpleadoMapper {
// Request → entidad NUEVA (para crear)
public Empleado toEntity(EmpleadosRequest request) {
    Empleado empleado = new Empleado();
    actualizarEntidad(request, empleado);   // reutilizamos el método de abajo
    return empleado;
}

// Copia el request sobre una entidad que YA EXISTE (para editar)
public void actualizarEntidad(EmpleadosRequest request, Empleado empleado) {
    empleado.setNombre(request.nombre().trim());
    empleado.setApellido(request.apellido().trim());
    empleado.setDni(request.dni().trim());
    empleado.setCargo(request.cargo().trim());
    empleado.setSueldo(request.sueldo());
}

// Entidad → response (para todo lo que devuelve datos)
public EmpleadoResponse toResponse(Empleado empleado) {
    return new EmpleadoResponse(
            empleado.getId(),
            empleado.getNombre() + " " + empleado.getApellido(),
            empleado.getDni(),
            empleado.getCargo(),
            empleado.getSueldo(),
            empleado.getFechaRegistro()
    );
}

}
