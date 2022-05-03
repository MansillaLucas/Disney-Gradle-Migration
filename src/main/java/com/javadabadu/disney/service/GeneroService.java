package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Personaje;

import java.util.List;

public interface GeneroService {
    Genero findById(Integer id) throws ExceptionBBDD;
    List<Genero> findAll();
    Genero save(Genero genero,Integer id) throws ExceptionBBDD;
    Integer lastValueId();
    Genero update(Genero genero) throws ExceptionBBDD;
    String softDelete(Integer id);
    void responseBBDD(String response, Integer id)throws ExceptionBBDD;
    Boolean existsById(Integer id);
}
