package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Genero;

import java.util.List;

public interface GeneroService {
    Genero findById(Integer id) throws ExceptionBBDD;
    List<Genero> findAll();
    Genero save(Genero genero);
    Integer lastValueId();
    Genero update(Genero genero, Integer id);
    String softDelete(Integer id);

    Boolean existsById(Integer id);
}
