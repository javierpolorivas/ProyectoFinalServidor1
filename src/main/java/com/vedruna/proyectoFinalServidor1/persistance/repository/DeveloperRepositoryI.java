package com.vedruna.proyectoFinalServidor1.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;

public interface DeveloperRepositoryI extends JpaRepository<Developer, Integer> {
    
    
} 
