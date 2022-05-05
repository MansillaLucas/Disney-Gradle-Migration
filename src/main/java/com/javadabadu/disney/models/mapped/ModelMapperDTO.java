package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.entity.Personaje;

import java.util.List;

public interface ModelMapperDTO {
    PeliculaResponseDTO peliculaToResponseDTO(Pelicula pelicula);
    PersonajeResponseDTO personajeToResponseDTO(Personaje personaje);
    List<PersonajeResponseDTO> listPersonajeToResponseDTO(List<Personaje> listPersonaje);
}
