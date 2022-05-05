package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.entity.Personaje;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapperDTOImp implements ModelMapperDTO {
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public PeliculaResponseDTO peliculaToResponseDTO(Pelicula pelicula) {
        PeliculaResponseDTO peliculaDTO = modelMapper.map(pelicula, PeliculaResponseDTO.class);
        return peliculaDTO;
    }

    @Override
    public PersonajeResponseDTO personajeToResponseDTO(Personaje personaje) {
        PersonajeResponseDTO personajeDTO = modelMapper.map(personaje, PersonajeResponseDTO.class);
        return personajeDTO;
    }

    @Override
    public List<PersonajeResponseDTO> listPersonajeToResponseDTO(List<Personaje> listPersonaje) {
        return listPersonaje.stream()
                .map(this::personajeToResponseDTO)
                .collect(Collectors.toList());
    }
}
