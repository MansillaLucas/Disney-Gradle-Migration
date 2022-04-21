package com.javadabadu.disney.service;

import com.javadabadu.disney.models.entity.Genero;

import java.util.List;
import java.util.Optional;

public interface GeneroService {
    Optional<Genero> findById(Integer id);
    List<Genero> findAll();
    Genero save(Genero genero);
    Integer lastValueId();
    Genero update(Genero genero, Integer id);
    Boolean softDelete(Integer id);
}
