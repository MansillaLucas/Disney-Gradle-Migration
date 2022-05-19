package com.javadabadu.disney.service;

import com.javadabadu.disney.models.dto.request.PeliculaRequestDTO;
import com.javadabadu.disney.models.dto.response.PeliculaResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;


public interface PeliculaService extends BaseServiceRead<PeliculaResponseDTO, Integer>, BaseServiceWrite<Pelicula, PeliculaResponseDTO, Integer>,
        BaseServiceRequest<PeliculaRequestDTO, PeliculaResponseDTO, Integer>{
}
