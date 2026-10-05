package dev.eder.empleados.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

// "Toda excepción que salga de un controller, me llega a mí"
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // RecursoNoEncontradoException → 404
    @ExceptionHandler (RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return armarRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of());
    }

    // DniDuplicadoException → 409
    @ExceptionHandler(DniDuplicadoException.class)
    public ResponseEntity<ErrorResponse> manejarDniDuplicado(DniDuplicadoException ex) {
        return armarRespuesta(HttpStatus.CONFLICT, ex.getMessage(), Map.of());
    }

    // Falló @Valid → 400 con el error de cada campo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        // Recorremos cada campo que falló y guardamos: campo → mensaje
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return armarRespuesta(HttpStatus.BAD_REQUEST, "Datos inválidos", errores);
    }

    // JSON mal escrito o con tipos equivocados (ej: "sueldo": "mucho") → 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return armarRespuesta(HttpStatus.BAD_REQUEST, "El JSON enviado no es válido", Map.of());
    }

    // Cualquier otra cosa que no esperábamos → 500, pero ordenado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex) {
        log.error("Error inesperado", ex);   // el detalle va a la consola, no al cliente
        return armarRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado", Map.of());
    }

    // Método de ayuda para no repetir el armado de la respuesta
    private ResponseEntity<ErrorResponse> armarRespuesta(HttpStatus status, String mensaje,
                                                         Map<String, String> errores) {
        ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), mensaje, errores);
        return ResponseEntity.status(status).body(body);
    }
}
