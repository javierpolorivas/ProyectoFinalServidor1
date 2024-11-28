package com.vedruna.proyectoFinalServidor1.persistance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Genera un constructor sin parámetros
@Data // Lombok genera automáticamente los métodos getter, setter, toString, equals, hashCode
@Entity // Marca la clase como una entidad JPA (se mapea a una tabla de la base de datos)
@Table(name="technologies") // Especifica el nombre de la tabla en la base de datos
public class Technology implements Serializable {

    @Id // Marca el campo id como la clave primaria de la entidad
    @Column(name="tech_id") // Especifica el nombre de la columna en la base de datos
    @NotNull(message = "Id cannot be null") // Valida que el id no sea nulo
    private int id;

    @Column(name="tech_name") // Especifica el nombre de la columna para el campo "name"
    private String name;

    // Relación Muchos a Muchos (Many-to-Many) con la entidad Project
    @ManyToMany(cascade = {CascadeType.PERSIST}) // Indica que puede haber múltiples proyectos asociados a esta tecnología
    @JoinTable(name="technologies_used_in_projects", // Define la tabla de unión en la base de datos para la relación
        joinColumns={@JoinColumn(name="technologies_tech_id")}, // Especifica el nombre de la columna para la tecnología en la tabla de unión
        inverseJoinColumns={@JoinColumn(name="projects_project_id")}) // Especifica el nombre de la columna para el proyecto en la tabla de unión
    private List<Project> projectsTechnologies = new ArrayList<>(); // Lista de proyectos asociados a esta tecnología

}
