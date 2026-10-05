package dev.eder.empleados.exception;

// "Ese DNI ya lo tiene otro empleado" → la convertiremos en 409
public class DniDuplicadoException extends RuntimeException{

    public DniDuplicadoException(String dni){
        super("Ya existe un empleado con el DNI " + dni);
    }

}
