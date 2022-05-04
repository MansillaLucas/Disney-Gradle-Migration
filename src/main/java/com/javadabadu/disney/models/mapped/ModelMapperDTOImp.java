package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;
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
}
