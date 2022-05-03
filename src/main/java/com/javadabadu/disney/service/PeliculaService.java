package com.javadabadu.disney.service;

import com.javadabadu.disney.models.entity.Pelicula;

import java.util.List;

public interface PeliculaService {
    List<Pelicula> findAll();
    Pelicula findByID(Integer id);

}
