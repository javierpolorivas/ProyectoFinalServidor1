package com.vedruna.proyectoFinalServidor1.services;

import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;

public interface TechnologyServiceI {

    /**
     * Saves a new technology.
     * 
     * @param technology the technology to be saved.
     */
    void saveTechnology(Technology technology);

    /**
     * Deletes a technology by its ID.
     * 
     * @param id the ID of the technology to be deleted.
     * @return true if the technology was successfully deleted, otherwise false.
     */
    boolean deleteTechnology(Integer id);

    /**
     * Finds a technology by its ID.
     * 
     * @param techId the ID of the technology to be found.
     * @return the technology if found, otherwise null.
     */
    Technology findById(Integer techId);

    /**
     * Associates a technology with a project.
     * 
     * @param projectId the ID of the project to associate the technology with.
     * @param technologyId the ID of the technology to be associated with the project.
     */
    void associateTechnologyWithProject(int projectId, int technologyId);
}
