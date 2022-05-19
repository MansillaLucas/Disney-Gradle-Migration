package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.patch.PeliculaPatchDTO;
import com.javadabadu.disney.models.dto.patch.SerieDtoPatch;
import com.javadabadu.disney.models.dto.request.GeneroRequestDTO;
import com.javadabadu.disney.models.dto.request.PeliculaRequestDTO;
import com.javadabadu.disney.models.dto.request.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.request.SerieRequestDTO;
import com.javadabadu.disney.models.dto.response.*;
import com.javadabadu.disney.models.entity.*;
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

    @Override
    public Personaje personajeRequestDtoToPersonaje(PersonajeRequestDTO personajeRequestDTO) {
        return modelMapper.map(personajeRequestDTO, Personaje.class);
    }

    @Override
    public GeneroResponseDTO generoToResponseDTO(Genero genero) {
        GeneroResponseDTO generoDTO = modelMapper.map(genero, GeneroResponseDTO.class);
        return generoDTO;
    }

    @Override
    public Genero generoRequestDtoToPersonaje(GeneroRequestDTO generoRequestDTO) {
        return modelMapper.map(generoRequestDTO, Genero.class);
    }

    @Override
    public List<GeneroResponseDTO> listGeneroToResponseDTO(List<Genero> listGenero) {
        return listGenero.stream()
                .map(this::generoToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AudioVisualResponseDTO audioVisualToResponseDTO(AudioVisual audiovisual) {
        AudioVisualResponseDTO audioVisualRespondeDTO = modelMapper.map(audiovisual, AudioVisualResponseDTO.class);
        return audioVisualRespondeDTO;
    }

    @Override
    public List<AudioVisualResponseDTO> listAudiovisualToResponseDTO(List<AudioVisual> listAudiovisual) {
        return listAudiovisual.stream()
                .map(this::audioVisualToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AudioVisual audioVisualResponseToAudiovisual(AudioVisualResponseDTO audiovisualDTO) {
        AudioVisual audiovisual = modelMapper.map(audiovisualDTO, AudioVisual.class);
        return audiovisual;
    }

     @Override
    public SerieResponseDTO serieToResponseDTO(Serie serie) {
        SerieResponseDTO serieDTO = modelMapper.map(serie, SerieResponseDTO.class);
        return serieDTO;
    }
    @Override
    public Serie responseDtoToSerie(SerieResponseDTO serieResponseDTO) {
       return modelMapper.map(serieResponseDTO, Serie.class);
    }

    @Override
    public SerieDtoPatch seriePatchDto(Serie serie) {
        SerieDtoPatch serieDTO = modelMapper.map(serie, SerieDtoPatch.class);
        return serieDTO;
    }

    @Override
    public Pelicula responseDtoToPelicula(PeliculaResponseDTO peliculaResponseDTO) {
        return modelMapper.map(peliculaResponseDTO, Pelicula.class);
    }

    @Override
    public PeliculaPatchDTO peliculaPatchDTO(Pelicula pelicula) {
        return modelMapper.map(pelicula, PeliculaPatchDTO.class);
    }

    @Override
    public Serie requestDtoToSerie(SerieRequestDTO serieRequestDTO) {
        return modelMapper.map(serieRequestDTO, Serie.class);
    }

    @Override
    public Pelicula requestDtoToPelicula(PeliculaRequestDTO peliculaRequestDTO) {
        return modelMapper.map(peliculaRequestDTO, Pelicula.class);
    }

}
