package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Personaje;

import java.util.List;
import java.util.Map;

public interface PersonajeService extends BaseServiceRead<PersonajeResponseDTO, Integer>, BaseServiceWrite<Personaje, PersonajeResponseDTO, Integer>{
    List<PersonajeResponseDTO> filterCharacter(String name, Integer edad, Integer idMovie) throws ExceptionBBDD;
    PersonajeResponseDTO getPersistenceEntity(PersonajeRequestDTO personajeRequest, Integer id) throws ExceptionBBDD;
    PersonajeResponseDTO updatePartial(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD ;

}
