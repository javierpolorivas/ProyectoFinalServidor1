package com.vedruna.proyectoFinalServidor1.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;

public interface TechnologyRepositoryI extends JpaRepository<Technology, Integer> {
    
    
} 
