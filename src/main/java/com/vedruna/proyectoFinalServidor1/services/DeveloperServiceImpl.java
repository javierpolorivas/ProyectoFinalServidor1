package com.vedruna.proyectoFinalServidor1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.repository.DeveloperRepositoryI;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;

@Service
public class DeveloperServiceImpl implements DeveloperServiceI {

    // Inyección de dependencias para los repositorios de Developer y Project
    @Autowired
    DeveloperRepositoryI developerRepository;

    @Autowired
    ProjectRepositoryI projectRepository;
    
    /**
     * Saves a developer with the projects associated
     * @param developer the developer to be saved
     */
    @Override
    public void saveDeveloper(Developer developer) {
        // Lista para almacenar los proyectos que se gestionarán
        List<Project> managedProjects = new ArrayList<>();
        
        // Recorre todos los proyectos asociados al desarrollador
        for (Project project : developer.getProjectsDevelopers()) {
            // Verifica si el proyecto existe en la base de datos
            projectRepository.findById(project.getId()).ifPresentOrElse(
                managedProjects::add,  // Si el proyecto existe, lo agrega a la lista de proyectos gestionados
                () -> { 
                    // Si no se encuentra el proyecto, lanza una excepción con el mensaje de error
                    throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
                }
            );
        }
        
        // Asocia los proyectos gestionados con el desarrollador
        developer.setProjectsDevelopers(managedProjects);
        
        // Guarda el desarrollador con los proyectos asociados
        developerRepository.save(developer);
    }

    /**
     * Deletes a developer by their ID.
     * 
     * @param id the ID of the developer to be deleted
     * @return true if the developer was successfully deleted, otherwise throws an exception
     * @throws IllegalArgumentException if no developer exists with the given ID
     */
    @Override
    public boolean deleteDeveloper(Integer id) {
        // Busca el desarrollador por su ID
        Optional<Developer> developer = developerRepository.findById(id);
    
        // Si el desarrollador existe, se elimina
        if (developer.isPresent()) {
            developerRepository.deleteById(id); 
            return true;
        } else {
            // Si no existe el desarrollador, lanza una excepción
            throw new IllegalArgumentException("No existe ningún developer con el ID: " + id);
        }
    }

    /**
     * Finds a developer by their ID.
     * 
     * @param developerId the ID of the developer
     * @return the developer if found, otherwise null
     */
    @Override
    public Developer findById(Integer developerId) {
        // Busca y devuelve el desarrollador con el ID proporcionado
        return developerRepository.findById(developerId).orElse(null);
    }
}
