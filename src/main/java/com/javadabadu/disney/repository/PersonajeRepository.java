package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {
}
