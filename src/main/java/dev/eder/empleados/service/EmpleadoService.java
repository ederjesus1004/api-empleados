package dev.eder.empleados.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.eder.empleados.Entity.Empleado;
import dev.eder.empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    
    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> listar(){
        return empleadoRepository.findAll();
    }

    public Empleado obtenerPorId(Long id){
    // orElseThrow() sin nada adentro lanza un error genérico de Java
        return empleadoRepository.findById(id).orElseThrow();
    }

    public Empleado crear(Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizar(Long id, Empleado datos){
        Empleado empleado = obtenerPorId(id);
        empleado.setNombre(datos.getNombre());
        empleado.setApellido(datos.getApellido());
        empleado.setDni(datos.getDni());
        empleado.setCargo(datos.getCargo());
        empleado.setSueldo(datos.getSueldo());
        return empleadoRepository.save(empleado);
    }

    public void eliminar(Long id){
        empleadoRepository.deleteById(id);
    }

}
