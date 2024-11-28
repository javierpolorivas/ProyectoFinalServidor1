package com.vedruna.proyectoFinalServidor1.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private int id;
    private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private String repository_url;
    private String demo_url;
    private String picture;
    private String stateProjectName; // Cambiado a String para almacenar el nombre
    private List<TechnologyDTO> technologies;
    private List<DeveloperDTO> developers;

    public ProjectDTO(Project p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.start_date = p.getStart_date();
        this.end_date = p.getEnd_date();
        this.repository_url = p.getRepository_url();
        this.demo_url = p.getDemo_url();
        this.picture = p.getPicture();
        this.stateProjectName = p.getStateProject() != null ? p.getStateProject().getName() : null; // Obtener el nombre del estado
        this.technologies = technologiesDTO(p.getTechnologies());
        this.developers = developersDTO(p.getDevelopers());
    }

    /**
     * Converts a list of Technology objects to a list of TechnologyDTO objects.
     *
     * @param technologies the list of Technology objects to be converted
     * @return a list of TechnologyDTO objects
     */
    public List<TechnologyDTO> technologiesDTO(List<Technology> technologies) {
        List<TechnologyDTO> technologiesRegistered = new ArrayList<>();
        for (Technology t : technologies) {
            technologiesRegistered.add(new TechnologyDTO(t));
        }
        return technologiesRegistered;
    }

    /**

     * Converts a list of Developer objects to a list of DeveloperDTO objects,
     * to be serialized to JSON.
     * 
     * @param developers list of Developer objects to convert
     * @return list of DeveloperDTO objects, ready to be serialized to DeveloperDTO
     */
    public List<DeveloperDTO> developersDTO(List<Developer> developers) {
        List<DeveloperDTO> developersRegistered = new ArrayList<>();
        for (Developer d : developers) {
            developersRegistered.add(new DeveloperDTO(d));
        }
        return developersRegistered;
    }
}
