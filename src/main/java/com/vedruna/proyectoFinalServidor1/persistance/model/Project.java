package com.vedruna.proyectoFinalServidor1.persistance.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.vedruna.proyectoFinalServidor1.validation.ValidUrl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Genera un constructor sin parámetros
@Data // Lombok genera los métodos getter, setter, toString, equals, hashCode automáticamente
@Entity // Marca la clase como una entidad JPA que se mapea a una tabla en la base de datos
@Table(name="projects") // Especifica el nombre de la tabla en la base de datos
public class Project implements Serializable {

    @Id // Indica que este campo es la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name="project_id") // Especifica el nombre de la columna en la base de datos
    private int id;

    @Column(name="project_name") // Especifica el nombre de la columna en la base de datos
    @NotNull(message = "Name cannot be null") // Valida que el nombre no sea nulo
    @NotBlank(message = "Name cannot be empty") // Valida que el nombre no esté vacío
    private String name;

    @Column(name="description") // Especifica el nombre de la columna en la base de datos
    @Size(min = 2, max = 50, message = "Description must be between 2 and 50 characters") // Valida que la descripción tenga entre 2 y 50 caracteres
    private String description;
    
    @Column(name="start_date") // Especifica el nombre de la columna en la base de datos
    @FutureOrPresent(message = "The start date cannot be before today") // Valida que la fecha de inicio no sea anterior al día de hoy
    private Date start_date;

    @Column(name="end_date") // Especifica el nombre de la columna en la base de datos
    private Date end_date;

    @Column(name="repository_url") // Especifica el nombre de la columna en la base de datos
    @ValidUrl(message = "Invalid URL format") // Valida que el formato de la URL sea correcto
    private String repository_url;

    @Column(name="demo_url") // Especifica el nombre de la columna en la base de datos
    @ValidUrl(message = "Invalid URL format") // Valida que el formato de la URL sea correcto
    private String demo_url;

    @Column(name="picture") // Especifica el nombre de la columna en la base de datos
    private String picture;

    // Relación Muchos a Uno (Many-to-One) con la entidad State
    @ManyToOne(fetch= FetchType.LAZY) // Relación con la entidad State, usando carga perezosa (Lazy Loading)
    @JoinColumn(name="status_status_id", referencedColumnName = "status_id") // La columna de la tabla de proyectos que hace referencia al estado
    private State stateProject;

    // Relación Muchos a Muchos (Many-to-Many) con la entidad Technology
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy="projectsTechnologies") // Relación bidireccional con la entidad Technology
    private List<Technology> technologies = new ArrayList<>();

    // Relación Muchos a Muchos (Many-to-Many) con la entidad Developer
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy="projectsDevelopers") // Relación bidireccional con la entidad Developer
    private List<Developer> developers = new ArrayList<>();

}
