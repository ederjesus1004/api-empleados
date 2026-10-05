package dev.eder.empleados.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.eder.empleados.Entity.Empleado;
import dev.eder.empleados.dto.EmpleadoResponse;
import dev.eder.empleados.dto.EmpleadosRequest;
import dev.eder.empleados.exception.DniDuplicadoException;
import dev.eder.empleados.exception.RecursoNoEncontradoException;
import dev.eder.empleados.mapper.EmpleadoMapper;
import dev.eder.empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;

    @Transactional (readOnly = true)
    public List<EmpleadoResponse> listar() {
        return empleadoRepository.findAll()
                .stream()
                .map(empleadoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse obtenerPorId(Long id) {
        return empleadoMapper.toResponse(buscarEmpleado(id));
    }

    @Transactional
    public EmpleadoResponse crear(EmpleadosRequest request) {
        // Regla: el DNI no puede existir
        if (empleadoRepository.existsByDni(request.dni())) {
            throw new DniDuplicadoException(request.dni());
        }
        Empleado empleado = empleadoMapper.toEntity(request);
        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
    }

    @Transactional
    public EmpleadoResponse actualizar(Long id, EmpleadosRequest request) {
        Empleado empleado = buscarEmpleado(id);   // si no existe → 404

        // Regla: el DNI no puede ser de OTRO empleado
        if (empleadoRepository.existsByDniAndIdNot(request.dni(), id)) {
            throw new DniDuplicadoException(request.dni());
        }
        empleadoMapper.actualizarEntidad(request, empleado);
        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
    }

    @Transactional
    public void eliminar(Long id) {
        Empleado empleado = buscarEmpleado(id);   // si no existe → 404
        empleadoRepository.delete(empleado);
    }

    // Busca o lanza 404. Lo usan obtener, actualizar y eliminar.
    private Empleado buscarEmpleado(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el empleado con id " + id));
    }
}