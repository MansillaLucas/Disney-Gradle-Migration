package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.entity.Serie;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperDTOImp implements ModelMapperDTO {
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public PeliculaResponseDTO peliculaToResponseDTO(Pelicula pelicula) {
        PeliculaResponseDTO peliculaDTO = modelMapper.map(pelicula, PeliculaResponseDTO.class);
        return peliculaDTO;
    }

    @Override
    public SerieResponseDTO serieToResponseDTO(Serie serie) {
        SerieResponseDTO serieDTO = modelMapper.map(serie, SerieResponseDTO.class);
        return serieDTO;
    }

    @Override
    public Pelicula responseDtoToPelicula(PeliculaResponseDTO peliculaResponseDTO) {
        return modelMapper.map(peliculaResponseDTO, Pelicula.class);
    }
}
