package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.AudioVisualResponseDTO;
import com.javadabadu.disney.models.dto.GeneroResponseDTO;
import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.entity.Personaje;

import java.util.List;

public interface ModelMapperDTO {
    PeliculaResponseDTO peliculaToResponseDTO(Pelicula pelicula);
    PersonajeResponseDTO personajeToResponseDTO(Personaje personaje);
    List<PersonajeResponseDTO> listPersonajeToResponseDTO(List<Personaje> listPersonaje);
    Personaje personajeResponseDTOtoPersonaje(PersonajeResponseDTO personajeDTO);
    GeneroResponseDTO generoToResponseDTO(Genero genero);
    List<GeneroResponseDTO> listGeneroToResponseDTO(List<Genero> listGenero);
    List<AudioVisualResponseDTO> listAudiovisualToResponseDTO(List<AudioVisual> listAudiovisual);
    AudioVisualResponseDTO audioVisualToResponseDTO(AudioVisual audiovisual);


    AudioVisual audioVisualResponseToAudiovisual(AudioVisualResponseDTO audiovisualDTO);
    List<AudioVisual> listAudiovisualResponseToAudiovisual(List<AudioVisualResponseDTO> listAudiovisual);

}
