package com.vedruna.proyectoFinalServidor1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.model.State;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;
import com.vedruna.proyectoFinalServidor1.persistance.repository.StateRepositoryI;

@Service
public class ProjectServiceImpl implements ProjectServiceI {

    @Autowired
    ProjectRepositoryI projectRepository;  // Repositorio para acceder a datos de proyectos

    @Autowired
    StateRepositoryI stateRepository; // Repositorio para acceder a datos de estados

    /**
     * Retrieves all projects with pagination.
     *
     * @param page the page number to fetch (0-based index).
     * @param size the number of items per page.
     * @return a paginated list of projects as ProjectDTO objects.
     */
    @Override
    public Page<ProjectDTO> showAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // Crea un objeto Pageable con los parámetros de la paginación
        Page<Project> projectPage = projectRepository.findAll(pageable);  // Recupera los proyectos con paginación
        return projectPage.map(ProjectDTO::new);  // Convierte la lista de proyectos a DTOs antes de devolverla
    }

    /**
     * Retrieves a project by its name.
     *
     * @param name the name of the project to find.
     * @return the ProjectDTO of the project found.
     * @throws IllegalArgumentException if no project is found with the given name.
     */
    @Override
    public ProjectDTO showProjectByName(String name) {
        List<Project> projects = projectRepository.findAll();  // Recupera todos los proyectos
        Project project = null;
        for (Project p : projects) {  // Itera sobre todos los proyectos
            if (p.getName().contains(name)) {  // Busca el proyecto por nombre
                project = p;
                break;
            }
        }
        if (project == null) {  // Si no se encuentra el proyecto
            throw new IllegalArgumentException("No project found with name containing: " + name);  // Lanza una excepción
        }
        return new ProjectDTO(project);  // Devuelve el DTO del proyecto encontrado
    }

    /**
     * Saves a project in the database.
     *
     * @param project the project to be saved.
     */
    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);  // Guarda el proyecto en la base de datos
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id the ID of the project to delete.
     * @return true if the project was successfully deleted, otherwise throws an exception.
     * @throws IllegalArgumentException if no project exists with the given ID.
     */
    public boolean deleteProject(Integer id) {
        Optional<Project> project = projectRepository.findById(id);  // Busca el proyecto por ID

        if (project.isPresent()) {  // Si el proyecto existe
            projectRepository.deleteById(id);  // Elimina el proyecto
            return true;
        } else {
            throw new IllegalArgumentException("No project found with ID: " + id);  // Si el proyecto no existe, lanza una excepción
        }
    }

    /**
     * Updates an existing project with new data.
     *
     * @param id the ID of the project to be updated.
     * @param project the updated project data.
     * @return true if the project was successfully updated, otherwise false.
     */
    public boolean updateProject(Integer id, Project project) {
        Optional<Project> projectToUpdate = projectRepository.findById(id);  // Busca el proyecto por ID

        if (projectToUpdate.isPresent()) {  // Si el proyecto existe
            // Actualiza los campos del proyecto
            projectToUpdate.get().setName(project.getName());
            projectToUpdate.get().setDescription(project.getDescription());
            projectToUpdate.get().setStart_date(project.getStart_date());
            projectToUpdate.get().setEnd_date(project.getEnd_date());
            projectToUpdate.get().setRepository_url(project.getRepository_url());
            projectToUpdate.get().setDemo_url(project.getDemo_url());
            projectToUpdate.get().setPicture(project.getPicture());
            projectToUpdate.get().setTechnologies(project.getTechnologies());
            projectToUpdate.get().setDevelopers(project.getDevelopers());
            projectRepository.save(projectToUpdate.get());  // Guarda el proyecto actualizado
            return true;
        } else {
            return false;  // Si el proyecto no se encuentra, no se actualiza
        }
    }

    /**
     * Moves a project to the testing state.
     *
     * @param id the ID of the project to move to testing.
     * @return true if the project was successfully moved to testing, false if no such project exists.
     */
    @Override
    public boolean moveProjectToTesting(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);  // Busca el proyecto por ID
        boolean isUpdated = false;
        if (projectOptional.isPresent()) {  // Si el proyecto existe
            Project project = projectOptional.get();
            Optional<State> stateOptional = stateRepository.findById(2);  // Busca el estado de "testing" (ID 2)
            if (stateOptional.isPresent()) {
                project.setStateProject(stateOptional.get());  // Asigna el estado de "testing" al proyecto
                projectRepository.save(project);  // Guarda el proyecto con el nuevo estado
                isUpdated = true;
            } else {
                System.out.println("State with ID 2 does not exist.");
            }
        } else {
            System.out.println("Project with ID " + id + " does not exist.");
        }
        return isUpdated;  // Retorna si el proyecto fue actualizado exitosamente
    }

    /**
     * Moves a project to the production state.
     *
     * @param id the ID of the project to move to production.
     * @return true if the project was successfully moved to production, false if no such project exists.
     */
    @Override
    public boolean moveProjectToProduction(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);  // Busca el proyecto por ID
        boolean isUpdated = false;
        if (projectOptional.isPresent()) {  // Si el proyecto existe
            Project project = projectOptional.get();
            Optional<State> stateOptional = stateRepository.findById(3);  // Busca el estado de "production" (ID 3)
            if (stateOptional.isPresent()) {
                project.setStateProject(stateOptional.get());  // Asigna el estado de "production" al proyecto
                projectRepository.save(project);  // Guarda el proyecto con el nuevo estado
                isUpdated = true;
            } else {
                System.out.println("State with ID 3 does not exist.");
            }
        } else {
            System.out.println("Project with ID " + id + " does not exist.");
        }
        return isUpdated;  // Retorna si el proyecto fue actualizado exitosamente
    }

    /**
     * Finds a project by its ID.
     *
     * @param projectId the ID of the project to be found.
     * @return the project if found, otherwise null.
     */
    @Override
    public Project findById(Integer projectId) {
        return projectRepository.findById(projectId).orElse(null);  // Busca el proyecto por ID y retorna el resultado o null si no se encuentra
    }

    /**
     * Retrieves projects by a specific technology name.
     *
     * @param techName the name of the technology used by the projects.
     * @return a list of projects that use the given technology.
     */
    @Override
    public List<ProjectDTO> getProjectsByTechnology(String techName) {
        return projectRepository.findProjectsByTechnology(techName);  // Llama al repositorio para obtener los proyectos que usan una tecnología específica
    }
}
