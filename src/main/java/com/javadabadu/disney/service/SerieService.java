package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.request.SerieRequestDTO;
import com.javadabadu.disney.models.dto.response.PersonajeResponseDTO;
import com.javadabadu.disney.models.dto.response.SerieResponseDTO;
import com.javadabadu.disney.models.entity.Serie;

import java.util.List;

public interface SerieService extends BaseServiceRead<SerieResponseDTO, Integer>,
        BaseServiceWrite<Serie, SerieResponseDTO, Integer>,
        BaseServicePatch<SerieRequestDTO, SerieResponseDTO, Integer>, AudioVisualService {


}
