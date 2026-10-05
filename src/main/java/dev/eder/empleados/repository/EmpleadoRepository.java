package dev.eder.empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eder.empleados.Entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

    // ¿Algún empleado tiene este DNI? (al crear)
    boolean existsByDni(String dni);

    // ¿OTRO empleado (con id distinto) tiene este DNI? (al editar)
    boolean existsByDniAndIdNot(String dni, Long id);
}
