// Paquete donde reside este controlador, encargado de manejar excepciones globales en la aplicación
package com.vedruna.proyectoFinalServidor1.controllers;

// Importaciones necesarias para manejar excepciones y construir respuestas HTTP
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;

/**
 * Clase controladora global para el manejo de excepciones.
 * Permite capturar errores en toda la aplicación y devolver respuestas estructuradas.
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * Maneja excepciones específicas de tipo `IllegalArgumentException`.
     *
     * @param ex La excepción `IllegalArgumentException` lanzada en algún punto de la aplicación.
     * @return ResponseEntity con un mensaje de error y el estado HTTP 400 (Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Crea un objeto de respuesta con un mensaje de error y la descripción de la excepción
        ResponseDTO<String> response = new ResponseDTO<>("Bad Request", ex.getMessage());
        // Devuelve una respuesta HTTP con el estado 400 (Bad Request) y el cuerpo de respuesta
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
    }

    /**
     * Maneja excepciones generales (de tipo `Exception`).
     * Este método es un mecanismo de respaldo para capturar errores no específicos.
     *
     * @param ex La excepción `Exception` lanzada en algún punto de la aplicación.
     * @return ResponseEntity con un mensaje de error genérico y el estado HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGeneralException(Exception ex) {
        // Crea un objeto de respuesta con un mensaje de error genérico
        ResponseDTO<String> response = new ResponseDTO<>(
            "Internal Server Error", // Título del error
            "An unexpected error occurred. Check that the path is correct and does not exist in the database and try again." // Descripción del error
        );
        // Devuelve una respuesta HTTP con el estado 500 (Internal Server Error) y el cuerpo de respuesta
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
