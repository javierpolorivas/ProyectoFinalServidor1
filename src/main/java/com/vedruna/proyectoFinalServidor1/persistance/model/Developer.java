package com.vedruna.proyectoFinalServidor1.persistance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vedruna.proyectoFinalServidor1.validation.ValidUrl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok generará un constructor sin parámetros.
@NoArgsConstructor 
// Lombok generará automáticamente los métodos getter, setter, equals, hashCode y toString.
@Data 
// Anotación para indicar que esta clase es una entidad en la base de datos.
@Entity
// Define el nombre de la tabla en la base de datos para esta entidad.
@Table(name="developers")
public class Developer implements Serializable {

    // Campo id que es la clave primaria de la entidad.
    @Id 
    // Se indica que el valor de la clave primaria será generado automáticamente.
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // Mapea el campo id a la columna 'dev_id' de la base de datos.
    @Column(name="dev_id") 
    private int id;

    // Campo name que almacena el nombre del desarrollador.
    @Column(name="dev_name") 
    // Se valida que el nombre tenga entre 2 y 50 caracteres.
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    // Campo surname que almacena el apellido del desarrollador.
    @Column(name="dev_surname") 
    // Se valida que el apellido tenga entre 2 y 50 caracteres.
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    // Campo email que almacena el correo electrónico del desarrollador.
    @Column(name="email") 
    // Se valida que el correo electrónico tenga un formato válido.
    @Email(message = "Email should be valid")
    private String email;

    // Campo linkedin_url que almacena la URL del perfil de LinkedIn del desarrollador.
    @Column(name="linkedin_url") 
    // Se valida que la URL tenga un formato válido.
    @ValidUrl(message = "Invalid URL format")
    private String linkedin_url;

    // Campo github_url que almacena la URL del perfil de GitHub del desarrollador.
    @Column(name="github_url") 
    // Se valida que la URL tenga un formato válido.
    @ValidUrl(message = "Invalid URL format")
    private String github_url;

    // Relación muchos a muchos entre desarrolladores y proyectos.
    // Esta anotación indica que un desarrollador puede trabajar en varios proyectos y viceversa.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) 
    // La tabla de unión entre desarrolladores y proyectos se llama 'developers_worked_on_projects'.
    @JoinTable(
        name="developers_worked_on_projects", 
        joinColumns={@JoinColumn(name="developers_dev_id")}, 
        inverseJoinColumns={@JoinColumn(name="projects_project_id")}
    )
    // Lista de proyectos en los que el desarrollador ha trabajado.
    private List<Project> projectsDevelopers = new ArrayList<>();
}
