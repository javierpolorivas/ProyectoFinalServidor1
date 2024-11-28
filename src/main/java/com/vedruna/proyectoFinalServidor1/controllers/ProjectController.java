// Paquete donde reside este controlador, encargado de gestionar las operaciones relacionadas con los proyectos.
package com.vedruna.proyectoFinalServidor1.controllers;

// Importaciones necesarias para trabajar con datos, servicios y validación.
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.services.ProjectServiceI;

import jakarta.validation.Valid;

@RestController // Indica que esta clase manejará las solicitudes HTTP y devolverá respuestas JSON.
@RequestMapping("/api/v1") // Mapea todas las solicitudes bajo el prefijo /api/v1.
@CrossOrigin // Permite peticiones desde diferentes orígenes (habilitado por si se usa en un entorno con frontend separado).
public class ProjectController {

    @Autowired // Inyección de dependencia para el servicio de proyectos.
    private ProjectServiceI projectService; // Instancia del servicio de proyectos.

    /**
     * Obtiene todos los proyectos con soporte de paginación.
     *
     * @param page el número de la página (por defecto 0).
     * @param size el tamaño de la página (por defecto 3).
     * @return una lista de proyectos paginada.
     */
    @GetMapping("/projects")
    public Page<ProjectDTO> getAllProjects(
        @RequestParam(defaultValue = "0") int page,  // Página predeterminada es 0.
        @RequestParam(defaultValue = "3") int size // Tamaño predeterminado de la página es 3.
    ) {
        return projectService.showAllProjects(page, size); // Llama al servicio para obtener los proyectos.
    }

    /**
     * Obtiene un proyecto por su nombre.
     *
     * @param name el nombre del proyecto.
     * @return el proyecto encontrado o un 404 si no se encuentra.
     */
    @GetMapping("/projects/{name}")
    public ResponseEntity<ResponseDTO<ProjectDTO>> showProjectByName(@PathVariable String name) {
        ProjectDTO project = projectService.showProjectByName(name); // Llama al servicio para obtener el proyecto.
        ResponseDTO<ProjectDTO> response = new ResponseDTO<>("Project found successfully", project); // Crea una respuesta con el proyecto.
        return ResponseEntity.ok(response); // Devuelve el proyecto con estado HTTP 200.
    }

    /**
     * Guarda un nuevo proyecto.
     *
     * @param project el proyecto a guardar.
     * @param bindingResult los errores de validación si los hay.
     * @return una respuesta HTTP con el mensaje de éxito o error.
     */
    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO<Object>> postProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        // Verificar si hay errores de validación
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder(); // Acumulador de errores de validación.
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField()) // Agrega el nombre del campo con el error.
                             .append(": ")
                             .append(error.getDefaultMessage()) // Mensaje de error.
                             .append("\n")
            );
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", errorMessages.toString()); // Crea respuesta de error.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Devuelve estado 400 con el error.
        }

        // Verificar si start_date es antes de hoy
        LocalDate today = LocalDate.now(); // Fecha actual.
        if (project.getStart_date().toLocalDate().isBefore(today)) { // Verifica si la fecha de inicio es antes de hoy.
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", "The start date cannot be before today."); // Error si la fecha es inválida.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Devuelve error 400.
        }

        // Guardar el proyecto si no hay errores
        projectService.saveProject(project); // Llama al servicio para guardar el proyecto.
        ResponseDTO<Object> response = new ResponseDTO<>("Project created successfully", null); // Respuesta de éxito.
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Devuelve estado 201 con mensaje de éxito.
    }

    /**
     * Elimina un proyecto.
     *
     * @param id el ID del proyecto a eliminar.
     * @return una respuesta con el mensaje de éxito o un 404 si el proyecto no existe.
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteProject(@PathVariable Integer id) {
        boolean projectDeleted = projectService.deleteProject(id); // Llama al servicio para eliminar el proyecto.
        if (!projectDeleted) { // Si el proyecto no se encontró o no se pudo eliminar.
            throw new IllegalArgumentException("There isn't any project with the ID:" + id); // Lanza una excepción.
        }
        ResponseDTO<String> response = new ResponseDTO<>("Project deleted successfully", "Project with ID " + id + " deleted."); // Mensaje de éxito.
        return ResponseEntity.status(HttpStatus.OK).body(response); // Devuelve estado 200 con el mensaje.
    }

    /**
     * Actualiza un proyecto existente.
     *
     * @param id el ID del proyecto a actualizar.
     * @param project los datos actualizados del proyecto.
     * @param bindingResult los errores de validación si los hay.
     * @return una respuesta con el mensaje de éxito o error.
     */
    @PutMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<Object>> updateProject(@PathVariable Integer id, @Valid @RequestBody Project project, BindingResult bindingResult) {
        // Verificar si hay errores de validación
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder(); // Acumulador de errores de validación.
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField()) // Agrega el nombre del campo con el error.
                             .append(": ")
                             .append(error.getDefaultMessage()) // Mensaje de error.
                             .append("\n")
            );
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", errorMessages.toString()); // Crea respuesta de error.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Devuelve estado 400 con el error.
        }

        boolean projectUpdated = projectService.updateProject(id, project); // Llama al servicio para actualizar el proyecto.
        if (!projectUpdated) { // Si el proyecto no se encontró para actualizar.
            ResponseDTO<Object> response = new ResponseDTO<>("Error", "There isn't any project with the ID: " + id); // Mensaje de error.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Devuelve estado 404.
        }

        ResponseDTO<Object> response = new ResponseDTO<>("Project updated successfully", project); // Respuesta de éxito con el proyecto actualizado.
        return ResponseEntity.status(HttpStatus.OK).body(response); // Devuelve estado 200 con el proyecto actualizado.
    }

    /**
     * Mueve un proyecto al estado de prueba.
     *
     * @param id el ID del proyecto a mover.
     * @return una respuesta con el estado de la operación.
     */
    @PatchMapping("/projects/totesting/{id}")
    public ResponseEntity<String> moveProjectToTesting(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToTesting(id); // Llama al servicio para mover el proyecto a pruebas.
            if (result) {
                return ResponseEntity.ok("Projects moved to testing successfully"); // Mensaje de éxito.
            } else if(projectService.findById(id)==null){ 
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found"); // Mensaje si el proyecto no existe.
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No projects were moved to testing"); // Si no se movió el proyecto.
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while moving projects to testing: " + e.getMessage()); // Si hay error en el proceso.
        }
    }

    /**
     * Mueve un proyecto al estado de producción.
     *
     * @param id el ID del proyecto a mover.
     * @return una respuesta con el estado de la operación.
     */
    @PatchMapping("/projects/toprod/{id}")
    public ResponseEntity<String> moveProjectToProduction(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToProduction(id); // Llama al servicio para mover el proyecto a producción.
            if (result) {
                return ResponseEntity.ok("Projects moved to production successfully"); // Mensaje de éxito.
            } else if(projectService.findById(id)==null){ 
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found"); // Mensaje si el proyecto no existe.
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No projects were moved to testing"); // Si no se movió el proyecto.
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while moving projects to production: " + e.getMessage()); // Si hay error en el proceso.
        }
    }

    /**
     * Obtiene proyectos por una tecnología específica.
     * 
     * @param tech el nombre de la tecnología a buscar.
     * @return una lista de proyectos que usan esa tecnología.
     */
    @GetMapping("/projects/tec/{tech}")
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> getProjectsByTechnology(@PathVariable String tech) {
        List<ProjectDTO> projects = projectService.getProjectsByTechnology(tech); // Llama al servicio para obtener proyectos con esa tecnología.
    
        if (projects.isEmpty()) { // Si no se encuentran proyectos.
            ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("No projects found with this technology", null); // Respuesta con mensaje de error.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Devuelve estado 404.
        }
    
        ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("Projects found successfully", projects); // Respuesta con proyectos encontrados.
        return ResponseEntity.ok(response); // Devuelve los proyectos encontrados con estado 200.
    }
}
