package com.vedruna.proyectoFinalServidor1.persistance.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Genera un constructor sin parámetros
@Data // Lombok genera los métodos getter, setter, toString, equals, hashCode automáticamente
@Entity // Marca la clase como una entidad JPA que se mapea a una tabla en la base de datos
@Table(name="status") // Especifica el nombre de la tabla en la base de datos
public class State {

    @Id // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de datos genera automáticamente el valor de este campo
    @Column(name="status_id") // Especifica el nombre de la columna en la base de datos
    private int id;

    @Column(name="status_name") // Especifica el nombre de la columna en la base de datos
    @NotNull(message = "Name cannot be null") // Valida que el nombre no sea nulo
    private String name;

    // Relación Uno a Muchos (One-to-Many) con la entidad Project
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stateProject") // Un estado puede estar asociado a varios proyectos
    private List<Project> statesWithProject;

}
