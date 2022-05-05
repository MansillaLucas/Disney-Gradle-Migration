package com.javadabadu.disney.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.PersonajeController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Personaje;
import com.javadabadu.disney.repository.PersonajeRepository;
import com.javadabadu.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    PersonajeRepository personajeRepository;

    @Override
    public Personaje save(Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    @Override
    public List<Personaje> findAll() throws ExceptionBBDD {
        try {
            return personajeRepository.findAll();
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }
    }

    @Override
    public Personaje findById(Integer id) throws ExceptionBBDD {
        return personajeRepository.findById(id).orElseThrow(() -> new ExceptionBBDD("Id no válido"));
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {

        if (personajeRepository.softDelete(id)) {
            return "Se elimino el genero seleccionado";
        } else {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }

    }

    @Override
    public Boolean existsById(Integer id) {
        return personajeRepository.existsById(id);
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        if (personajeRepository.lastValueId() >= 1) {
            return personajeRepository.lastValueId();
        } else {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }

    }

    @Override
    public List<Personaje> filterCharacter(String name, Integer edad, Integer idMovie) throws ExceptionBBDD {

        try {
            if (name != null && edad != null) {
                return personajeRepository.findByEdadYNombre(name, edad);
            } else if (name != null) {
                return personajeRepository.findByNombre(name);
            } else if (edad != null) {
                return personajeRepository.findByEdad(edad);
            }else if (idMovie != null){
                return personajeRepository.findByMovieId(idMovie);
            } else {
                return personajeRepository.findAll();
            }
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }
    }

    public Personaje getPersonaje(Personaje personaje, Integer id) throws ExceptionBBDD {
        Personaje source = null;
        if (existsById(id)) {
            source = findById(id);
            personaje.setId(id);
            source = personaje;
            return source;
        } else {
            return personaje;
        }
    }

    public Personaje getPersonaje(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {

        ObjectMapper mapper = new ObjectMapper();
        Personaje searchedPersonaje = findById(id);

        Map<String, Object> searchedPersonajeMap = mapper.convertValue(searchedPersonaje, Map.class);
        propiedades.forEach((k, v) -> {
            if (searchedPersonajeMap.containsKey(k)) {
                searchedPersonajeMap.replace(k, searchedPersonajeMap.get(k), v);
            }
        });
        searchedPersonaje = mapper.convertValue(searchedPersonajeMap, Personaje.class);
        return searchedPersonaje;
    }

    public Link getCollectionLink(HttpServletRequest request) {
        return linkTo(methodOn(PersonajeController.class).findAll(request)).withRel("Personajes:");
    }

    public Link getSelfLink(Integer id, HttpServletRequest request) {
        return linkTo(methodOn(PersonajeController.class).findById(id, request)).withSelfRel();
    }
}
