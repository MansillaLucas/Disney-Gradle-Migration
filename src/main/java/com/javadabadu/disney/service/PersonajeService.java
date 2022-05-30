package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.request.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.response.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Personaje;

import java.util.List;

public interface PersonajeService extends BaseServiceRead<PersonajeResponseDTO, Integer>, BaseServiceWrite<Personaje, PersonajeResponseDTO, Integer>,
        BaseServicePatch<PersonajeRequestDTO, PersonajeResponseDTO, Integer> {
    List<PersonajeResponseDTO> filterCharacter(String titulo, Integer edad, Integer idMovie) throws ExceptionBBDD;

}
