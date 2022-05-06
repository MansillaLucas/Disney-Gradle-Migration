package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Personaje;

import java.util.List;

public interface PersonajeService extends BaseService<Personaje, Integer> {

    List<Personaje> filterCharacter(String name, Integer edad, Integer idMovie) throws ExceptionBBDD;

}
