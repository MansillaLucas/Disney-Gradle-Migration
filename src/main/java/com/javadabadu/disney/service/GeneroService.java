package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.GeneroRequestDTO;
import com.javadabadu.disney.models.dto.GeneroResponseDTO;
import com.javadabadu.disney.models.dto.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Genero;

import java.util.Map;

public interface GeneroService extends BaseServiceRead<GeneroResponseDTO, Integer>, BaseServiceWrite<Genero, GeneroResponseDTO, Integer>,
        BaseServiceRequest<GeneroRequestDTO, GeneroResponseDTO, Integer>{


}
