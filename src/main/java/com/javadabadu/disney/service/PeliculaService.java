package com.javadabadu.disney.service;

import com.javadabadu.disney.models.dto.PeliculaRequestDTO;
import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;


public interface PeliculaService extends BaseServiceRead<PeliculaResponseDTO, Integer>, BaseServiceWrite<Pelicula, PeliculaResponseDTO, Integer>,
        BaseServiceRequest<PeliculaRequestDTO, PeliculaResponseDTO, Integer>{
}
