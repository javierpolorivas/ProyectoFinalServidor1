// Paquete donde reside este controlador, que organiza las clases relacionadas con el servidor
package com.vedruna.proyectoFinalServidor1.controllers;

// Importaciones necesarias para el controlador y los servicios que utiliza
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 

import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO; 
import com.vedruna.proyectoFinalServidor1.persistance.model.Developer; 
import com.vedruna.proyectoFinalServidor1.persistance.model.Project; 
import com.vedruna.proyectoFinalServidor1.services.DeveloperServiceI; 
import com.vedruna.proyectoFinalServidor1.services.ProjectServiceI; 

// Indica que esta clase es un controlador REST y manejará peticiones HTTP
@RestController
// Define el prefijo para todas las rutas de este controlador
@RequestMapping("/api/v1")
// Permite peticiones desde diferentes orígenes (Cross-Origin Resource Sharing)
@CrossOrigin
public class DeveloperController {

    // Inyección del servicio para manejar la lógica de negocio de los desarrolladores
    @Autowired
    private DeveloperServiceI developerService;

    // Inyección del servicio para manejar la lógica de negocio de los proyectos
    @Autowired
    private ProjectServiceI projectService;

    /**
     * Maneja la creación de un nuevo desarrollador.
     *
     * @param developer El objeto Developer recibido en el cuerpo de la petición HTTP
     * @return ResponseEntity con un mensaje de éxito y el estado HTTP 201 (Created)
     */
    @PostMapping("/developers")
    public ResponseEntity<ResponseDTO<String>> postDeveloper(@RequestBody Developer developer) {
        // Guarda el nuevo desarrollador utilizando el servicio
        developerService.saveDeveloper(developer);
        // Crea un objeto de respuesta con un mensaje de éxito
        ResponseDTO<String> response = new ResponseDTO<>("Developer created successfully", null);
        // Devuelve la respuesta con el estado HTTP 201
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Maneja la eliminación de un desarrollador por su ID.
     *
     * @param id El ID del desarrollador a eliminar, recibido como parte de la URL
     * @return ResponseEntity con un mensaje de éxito y el estado HTTP 200 (OK)
     * @throws IllegalArgumentException si no se encuentra un desarrollador con el ID proporcionado
     */
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteDeveloper(@PathVariable Integer id) {
        // Intenta eliminar el desarrollador utilizando el servicio
        boolean developerDeleted = developerService.deleteDeveloper(id);
        // Si no se elimina correctamente, lanza una excepción
        if (!developerDeleted) {
            throw new IllegalArgumentException("There isn't a developer with the ID: " + id);
        }
        // Crea un objeto de respuesta con un mensaje de éxito
        ResponseDTO<String> response = new ResponseDTO<>("Developer successfully removed", null);
        // Devuelve la respuesta con el estado HTTP 200
        return ResponseEntity.status(HttpStatus.OK).body(response); // Cambié NO_CONTENT por OK
    }

    /**
     * Asocia un desarrollador a un proyecto.
     *
     * @param developerId El ID del desarrollador a asociar
     * @param projectId El ID del proyecto al que se asociará el desarrollador
     * @return ResponseEntity con un mensaje de éxito o error según el resultado
     */
    @PostMapping("/developers/worked/{developerId}/{projectId}")
    public ResponseEntity<?> addDeveloperToProject(@PathVariable int developerId, @PathVariable int projectId) {
        // Busca el desarrollador por su ID utilizando el servicio
        Developer developer = developerService.findById(developerId);
        // Busca el proyecto por su ID utilizando el servicio
        Project project = projectService.findById(projectId);

        // Si no se encuentra el desarrollador, devuelve una respuesta de error
        if (developer == null) {
            return ResponseEntity.badRequest().body("Developer not found");
        }

        // Si no se encuentra el proyecto, devuelve una respuesta de error
        if (project == null) {
            return ResponseEntity.badRequest().body("Project not found");
        }

        // Verifica si el desarrollador ya está asociado al proyecto
        if (!project.getDevelopers().contains(developer)) {
            // Si no está asociado, lo agrega a la lista de desarrolladores del proyecto
            project.getDevelopers().add(developer);
            // También agrega el proyecto a la lista de proyectos del desarrollador
            developer.getProjectsDevelopers().add(project);
            // Guarda los cambios en ambos objetos utilizando los servicios
            projectService.saveProject(project);
            developerService.saveDeveloper(developer);
        }

        // Devuelve una respuesta indicando que la operación fue exitosa
        return ResponseEntity.ok("Developer added to project");
    }
}
