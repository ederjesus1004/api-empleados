package dev.eder.empleados.exception;

// "Lo que buscas no existe" → la convertiremos en 404
public class RecursoNoEncontradoException extends RuntimeException{

    public RecursoNoEncontradoException(String mensaje){
        // le pasa el mensaje a RuntimeException para que lo guarde
        super(mensaje);
    }
}
