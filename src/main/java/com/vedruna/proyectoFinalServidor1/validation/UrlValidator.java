package com.vedruna.proyectoFinalServidor1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    // Expresión regular para validar el formato de una URL
    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9-_.]+(?:\\.[a-zA-Z]{2,})+(?:/[\\w-._~:/?#[\\]@!$&'()*+,;=.]+)*$";

    /**
     * Inicializa el validador. Este método se ejecuta cuando el validador es creado.
     * Aquí no es necesario hacer nada ya que la validación no depende de atributos adicionales.
     * 
     * @param constraintAnnotation la anotación que contiene los atributos de la restricción
     */
    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    /**
     * Evalúa si una URL es válida según el patrón definido.
     * 
     * @param value   el valor (URL) que se debe validar
     * @param context contexto en el que se evalúa la restricción
     * @return true si la URL es válida, false si no lo es
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si el valor es nulo o vacío, se considera válido. Esto es útil si la URL no es obligatoria.
        if (value == null || value.isEmpty()) {
            return true;
        }

        // Si el valor no es nulo ni vacío, se valida con la expresión regular
        return Pattern.matches(URL_REGEX, value);
    }
}
