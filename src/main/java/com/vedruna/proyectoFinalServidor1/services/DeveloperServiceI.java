package com.vedruna.proyectoFinalServidor1.services;

import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;

public interface DeveloperServiceI {

    // Método para guardar un nuevo desarrollador en la base de datos
    void saveDeveloper(Developer developer);

    // Método para eliminar un desarrollador de la base de datos usando su ID
    boolean deleteDeveloper(Integer id);

    // Método para buscar un desarrollador en la base de datos por su ID
    Developer findById(Integer developerId);
}
