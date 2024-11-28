package com.vedruna.proyectoFinalServidor1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.repository.TechnologyRepositoryI;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;

@Service
public class TechnologyServiceImpl implements TechnologyServiceI {

    @Autowired
    TechnologyRepositoryI technologyRepository;

    @Autowired
    ProjectRepositoryI projectRepository;

    /**
     * Saves a technology with the projects associated.
     * This method ensures that a technology is saved along with its associated projects.
     * 
     * @param technology the technology to be saved
     */
    @Override
    public void saveTechnology(Technology technology) {
        // Verifica si ya existe una tecnología con el mismo ID en la base de datos
        if (technologyRepository.existsById(technology.getId())) {
            throw new IllegalArgumentException("El ID de la tecnología ya está en uso");
        }

        // Lista que gestionará los proyectos asociados a la tecnología
        List<Project> managedProjects = new ArrayList<>();
        
        // Itera a través de los proyectos asociados a la tecnología
        for (Project project : technology.getProjectsTechnologies()) {
            // Verifica si el proyecto existe en la base de datos
            projectRepository.findById(project.getId()).ifPresentOrElse(
                managedProjects::add,  // Si existe, añade el proyecto a la lista
                () -> { 
                    throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
                }
            );
        }
        
        // Asocia los proyectos gestionados a la tecnología
        technology.setProjectsTechnologies(managedProjects);
        
        // Guarda la tecnología con los proyectos asociados en la base de datos
        technologyRepository.save(technology);
    }

    /**
     * Deletes a technology by its ID.
     * This method deletes a technology by its ID, throwing an exception if not found.
     * 
     * @param id the ID of the technology to be deleted
     * @return true if the technology was successfully deleted, otherwise throws an exception
     * @throws IllegalArgumentException if no technology exists with the given ID
     */
    @Override
    public boolean deleteTechnology(Integer id) {
        Optional<Technology> technology = technologyRepository.findById(id);
    
        if (technology.isPresent()) {
            // Elimina la tecnología si existe en la base de datos
            technologyRepository.deleteById(id); 
            return true;
        } else {
            // Lanza excepción si la tecnología no existe
            throw new IllegalArgumentException("No existe ninguna tecnología con el ID: " + id);
        }
    }

    /**
     * Finds a technology by its ID.
     * This method retrieves a technology from the database by its ID.
     * 
     * @param techId the ID of the technology to be found
     * @return the technology if found, otherwise null
     */
    @Override
    public Technology findById(Integer techId) {
        // Devuelve la tecnología si se encuentra, o null si no existe
        return technologyRepository.findById(techId).orElse(null); 
    }

    /**
     * Associates a technology with a project.
     * This method links a technology with a project, adding the technology to the project's list
     * and the project to the technology's list of associated projects.
     *
     * @param technologyId the ID of the technology to associate
     * @param projectId the ID of the project to associate with
     * @throws IllegalArgumentException if the technology or project with the given IDs is not found
     */
    @Override
    public void associateTechnologyWithProject(int projectId, int technologyId) {
        // Busca la tecnología por su ID
        Technology technology = technologyRepository.findById(technologyId).orElseThrow(() -> 
            new IllegalArgumentException("Technology with ID " + technologyId + " not found"));
        
        // Busca el proyecto por su ID
        Project project = projectRepository.findById(projectId).orElseThrow(() -> 
            new IllegalArgumentException("Project with ID " + projectId + " not found"));
        
        // Agrega la tecnología al proyecto y viceversa
        project.getTechnologies().add(technology);
        technology.getProjectsTechnologies().add(project);
        
        // Guarda ambos, el proyecto y la tecnología, después de la asociación
        projectRepository.save(project);
        technologyRepository.save(technology);
    }
}
