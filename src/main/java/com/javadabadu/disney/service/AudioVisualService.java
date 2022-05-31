package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.response.AudioVisualResponseDTO;
import com.javadabadu.disney.models.dto.response.SerieResponseDTO;

import java.util.List;

public interface AudioVisualService {
    AudioVisualResponseDTO joinPersonajes(Integer idAudioVisual, List<Integer> idPersonajes) throws ExceptionBBDD;
    AudioVisualResponseDTO removePersonaje(Integer idPelicula, List<Integer> personajesToDelete) throws ExceptionBBDD;
    List<AudioVisualResponseDTO> filterAudiovisual (String titulo, Integer idGenero,String order) throws ExceptionBBDD;
}
