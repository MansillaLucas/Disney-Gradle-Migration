package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Personaje;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PersonajeService {
    Personaje save (Personaje personaje);
    List<Personaje> findAll() throws ExceptionBBDD;
    Personaje findById(Integer id) throws ExceptionBBDD;
    String softDelete(Integer id) throws ExceptionBBDD;
    Boolean existsById(Integer id);
    Integer lastValueId() throws ExceptionBBDD;
    List<Personaje> filterCharacter(String name, Integer edad, Integer idMovie) throws ExceptionBBDD;
    Personaje getPersonaje(Personaje personaje, Integer id) throws ExceptionBBDD;
    Personaje getPersonaje(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD;
    Link getSelfLink(Integer id, HttpServletRequest request);
    Link getCollectionLink(HttpServletRequest request);
}
