// Paquete donde reside este controlador, encargado de gestionar las operaciones relacionadas con las tecnologías.
package com.vedruna.proyectoFinalServidor1.controllers;

// Importaciones necesarias para trabajar con datos, servicios y validación.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;
import com.vedruna.proyectoFinalServidor1.services.TechnologyServiceI;

@RestController // Indica que esta clase manejará las solicitudes HTTP y devolverá respuestas JSON.
@RequestMapping("/api/v1") // Mapea todas las solicitudes bajo el prefijo /api/v1.
@CrossOrigin // Permite peticiones desde diferentes orígenes (habilitado por si se usa en un entorno con frontend separado).
public class TechnologyController {

    @Autowired // Inyección de dependencia para el servicio de tecnologías.
    private TechnologyServiceI technologyService; // Instancia del servicio de tecnologías.

    /**
     * Guarda una nueva tecnología junto con los proyectos asociados.
     *
     * @param technology la tecnología que se desea guardar.
     * @return una respuesta HTTP con el mensaje de éxito o error.
     */
    @PostMapping("/technologies")
    public ResponseEntity<String> createTechnology(@RequestBody Technology technology) {
        try {
            technologyService.saveTechnology(technology); // Llama al servicio para guardar la tecnología.
            return ResponseEntity.status(HttpStatus.CREATED).body("Technology created successfully"); // Respuesta de éxito con estado HTTP 201.
        } catch (IllegalArgumentException e) {
            // Si el ID ya está en uso o algún otro error, devuelve un 400 con el mensaje de error.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
        }
    }

    /**
     * Elimina una tecnología por su ID.
     *
     * @param id el ID de la tecnología a eliminar.
     * @return una respuesta HTTP con el mensaje de éxito o error.
     */
    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteTechnology(@PathVariable Integer id) {
        boolean technologyDeleted = technologyService.deleteTechnology(id); // Llama al servicio para eliminar la tecnología.
        if (!technologyDeleted) { // Si no se encuentra la tecnología con el ID proporcionado.
            throw new IllegalArgumentException("There isn't a technology with the ID: " + id); // Lanza una excepción.
        }
        // Si la tecnología se elimina correctamente, devuelve un mensaje de éxito.
        ResponseDTO<String> response = new ResponseDTO<>("Technology successfully removed", null);
        return ResponseEntity.status(HttpStatus.OK).body(response); // Responde con estado 200 (OK).
    }

    /**
     * Asocia una tecnología con un proyecto específico.
     *
     * @param technologyId el ID de la tecnología a asociar.
     * @param projectId el ID del proyecto.
     * @return una respuesta HTTP con el mensaje de éxito o error.
     */
    @PostMapping("/technologies/used/{projectId}/{technologyId}")
    public ResponseEntity<String> associateTechnologyWithProject(@PathVariable int projectId, @PathVariable int technologyId) {
        try {
            technologyService.associateTechnologyWithProject(projectId, technologyId); // Llama al servicio para asociar la tecnología al proyecto.
            return ResponseEntity.status(HttpStatus.OK).body("Technology associated with project successfully"); // Respuesta de éxito con estado HTTP 200.
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, tecnología o proyecto no existen), devuelve un 400 con el mensaje de error.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
