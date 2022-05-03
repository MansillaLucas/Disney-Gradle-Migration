package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Personaje;

import java.util.List;

public interface PersonajeService {
    Personaje save (Personaje personaje);
    List<Personaje> findAll() throws ExceptionBBDD;
    Personaje findById(Integer id) throws ExceptionBBDD;
    String softDelete(Integer id) throws ExceptionBBDD;
    Boolean existsById(Integer id);
    Integer lastValueId() throws ExceptionBBDD;
    List<Personaje> filterCharacter(String name, Integer edad, Integer idMovie);
}
