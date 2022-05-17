package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.GeneroRequestDTO;
import com.javadabadu.disney.models.dto.GeneroResponseDTO;
import com.javadabadu.disney.models.dto.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Genero;

import java.util.Map;

public interface GeneroService extends BaseServiceRead<GeneroResponseDTO, Integer>, BaseServiceWrite<Genero, GeneroResponseDTO, Integer> {
    GeneroResponseDTO getPersistenceEntity(GeneroRequestDTO generoRequestDTO, Integer id) throws ExceptionBBDD;
    GeneroResponseDTO updatePartial(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD ;

}
