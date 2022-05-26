package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.request.PeliculaRequestDTO;
import com.javadabadu.disney.models.dto.response.PeliculaResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;

import java.util.List;


public interface PeliculaService extends BaseServiceRead<PeliculaResponseDTO, Integer>, BaseServiceWrite<Pelicula, PeliculaResponseDTO, Integer>,
        BaseServicePatch<PeliculaRequestDTO, PeliculaResponseDTO, Integer> {
    PeliculaResponseDTO joinPersonajes(Integer idPelicula, List<Integer> idPersonajes) throws ExceptionBBDD;
    PeliculaResponseDTO removePersonaje(Integer idPelicula, Integer idPersonaje) throws ExceptionBBDD;

}
