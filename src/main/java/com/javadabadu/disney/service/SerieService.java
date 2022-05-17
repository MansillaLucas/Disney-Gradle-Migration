package com.javadabadu.disney.service;

import com.javadabadu.disney.models.dto.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.dto.SerieRequestDTO;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.Serie;

public interface SerieService extends BaseServiceRead<SerieResponseDTO, Integer>,
        BaseServiceWrite<Serie, SerieResponseDTO, Integer>,
        BaseServiceRequest<SerieRequestDTO, SerieResponseDTO, Integer>{

}
