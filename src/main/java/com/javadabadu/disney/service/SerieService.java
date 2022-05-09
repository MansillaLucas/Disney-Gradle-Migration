package com.javadabadu.disney.service;

import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.Serie;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface SerieService extends BaseServiceRead<SerieResponseDTO, Integer>  {

}
