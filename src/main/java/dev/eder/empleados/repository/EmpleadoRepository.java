package dev.eder.empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eder.empleados.Entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

}
