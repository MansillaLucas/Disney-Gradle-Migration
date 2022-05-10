package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.GeneroResponseDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Personaje;

import java.util.List;

public interface GeneroService extends BaseServiceRead<GeneroResponseDTO, Integer>, BaseServiceWrite<Genero, GeneroResponseDTO, Integer> {
}
