package com.javadabadu.disney.service;

import com.javadabadu.disney.models.dto.request.GeneroRequestDTO;
import com.javadabadu.disney.models.dto.response.GeneroResponseDTO;
import com.javadabadu.disney.models.entity.Genero;

public interface GeneroService extends BaseServiceRead<GeneroResponseDTO, Integer>, BaseServiceWrite<Genero, GeneroResponseDTO, Integer>,
        BaseServicePatch<GeneroRequestDTO, GeneroResponseDTO, Integer> {


}
