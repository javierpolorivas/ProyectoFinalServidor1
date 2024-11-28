package com.vedruna.proyectoFinalServidor1.validation;

// Importación de las clases necesarias de la API de validación de Jakarta
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Anotación personalizada para validar el formato de una URL
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE }) // Aplica a campos, métodos, parámetros y otros tipos de anotaciones.
@Retention(RetentionPolicy.RUNTIME) // La anotación estará disponible en tiempo de ejecución para que pueda ser procesada por el validador.
@Constraint(validatedBy = UrlValidator.class) // Indica que la lógica de validación de esta anotación será manejada por la clase `UrlValidator`
public @interface ValidUrl {
    // Mensaje predeterminado que se usará si la validación falla
    String message() default "Invalid URL format"; 
    
    // Permite agrupar las validaciones (no se usa en este caso, pero se deja para flexibilidad futura)
    Class<?>[] groups() default {}; 
    
    // Permite asociar carga adicional con la anotación, como metadatos, aunque normalmente no se utiliza
    Class<? extends Payload>[] payload() default {}; 
}
