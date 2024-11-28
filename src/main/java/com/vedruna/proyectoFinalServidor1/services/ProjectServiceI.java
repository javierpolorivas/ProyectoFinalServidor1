package com.vedruna.proyectoFinalServidor1.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;

public interface ProjectServiceI {

    /**
     * Retrieves a paginated list of all projects.
     * 
     * @param page the page number to retrieve (starting from 0)
     * @param size the number of projects to display per page
     * @return a Page of ProjectDTO objects containing projects with pagination
     */
    Page<ProjectDTO> showAllProjects(int page, int size); 

    /**
     * Retrieves a project by its name.
     * 
     * @param name the name of the project to search for
     * @return the ProjectDTO of the project with the given name
     */
    ProjectDTO showProjectByName(String name);

    /**
     * Saves a new project in the system.
     * 
     * @param project the project to be saved
     */
    void saveProject(Project project);

    /**
     * Deletes a project by its ID.
     * 
     * @param id the ID of the project to delete
     * @return true if the project was deleted successfully, otherwise false
     */
    boolean deleteProject(Integer id);

    /**
     * Moves a project to the testing state.
     * 
     * @param id the ID of the project to move to testing
     * @return true if the project was successfully moved to testing, otherwise false
     */
    boolean moveProjectToTesting(Integer id);

    /**
     * Moves a project to the production state.
     * 
     * @param id the ID of the project to move to production
     * @return true if the project was successfully moved to production, otherwise false
     */
    boolean moveProjectToProduction(Integer id);

    /**
     * Updates the details of an existing project.
     * 
     * @param id the ID of the project to update
     * @param project the updated project data
     * @return true if the project was updated successfully, otherwise false
     */
    boolean updateProject(Integer id, Project project);

    /**
     * Retrieves a project by its ID.
     * 
     * @param projectId the ID of the project to search for
     * @return the Project object with the given ID
     */
    Project findById(Integer projectId);

    /**
     * Retrieves a list of projects that use a specific technology.
     * 
     * @param techName the name of the technology to filter projects by
     * @return a list of ProjectDTO objects of projects using the specified technology
     */
    List<ProjectDTO> getProjectsByTechnology(String techName);
}
