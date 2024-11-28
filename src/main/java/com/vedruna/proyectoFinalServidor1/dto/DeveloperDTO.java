package com.vedruna.proyectoFinalServidor1.dto;

import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperDTO {


    private int id;
    private String name;
    private String surname;
    private String email;
    private String linkedin_url;
    private String github_url;
    


    public DeveloperDTO(Developer d) {
        this.id = d.getId();
        this.name = d.getName();
        this.surname = d.getSurname();
        this.email = d.getEmail();
        this.linkedin_url = d.getLinkedin_url();
        this.github_url = d.getGithub_url();
    }
    
}
