package dev.eder.empleados.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.eder.empleados.Entity.Empleado;
import dev.eder.empleados.dto.EmpleadoResponse;
import dev.eder.empleados.dto.EmpleadosRequest;
import dev.eder.empleados.mapper.EmpleadoMapper;
import dev.eder.empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;   // ← nuevo: Spring lo inyecta

    public List<EmpleadoResponse> listar() {
        return empleadoRepository.findAll()
                .stream()
                .map(empleadoMapper::toResponse)
                .toList();
    }

    public EmpleadoResponse obtenerPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow();
        return empleadoMapper.toResponse(empleado);
    }

    public EmpleadoResponse crear(EmpleadosRequest request) {
        Empleado empleado = empleadoMapper.toEntity(request);
        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
    }

    public EmpleadoResponse actualizar(Long id, EmpleadosRequest request) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow();
        empleadoMapper.actualizarEntidad(request, empleado);
        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
    }

    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }
}
