package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.*;
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
    public Personaje personajeResponseDTOtoPersonaje(PersonajeResponseDTO personajeDTO) {

        Personaje personaje = modelMapper.map(personajeDTO, Personaje.class);

        return personaje;
    }

    @Override
    public List<PersonajeResponseDTO> listPersonajeToResponseDTO(List<Personaje> listPersonaje) {
        return listPersonaje.stream()
                .map(this::personajeToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GeneroResponseDTO generoToResponseDTO(Genero genero) {
        GeneroResponseDTO generoDTO = modelMapper.map(genero, GeneroResponseDTO.class);
        return generoDTO;
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
    public List<AudioVisual> listAudiovisualResponseToAudiovisual(List<AudioVisualResponseDTO> listAudiovisual) {
        return listAudiovisual.stream()
                .map(this::audioVisualResponseToAudiovisual)
                .collect(Collectors.toList());
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

    public Usuario usuarioDTOToUsuario(UsuarioResponseDTO usuarioResponseDTO) {
        return modelMapper.map(usuarioResponseDTO, Usuario.class);
    }

    public UsuarioResponseDTO usuarioToUsuarioDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> listUsuarioToUsuarioDTO(List<Usuario> usuarioList) {
        return usuarioList.stream()
                .map(this::usuarioToUsuarioDTO)
                .collect(Collectors.toList());
    }

    public RolResponseDTO rolToRolDTO(Rol rol) {
        return modelMapper.map(rol, RolResponseDTO.class);
    }

    public Rol rolDTOtoRol(RolResponseDTO rolResponseDTO) {
        return modelMapper.map(rolResponseDTO, Rol.class);
    }

    public List<RolResponseDTO> listRolToRolDTO(List<Rol> rolList) {
        return rolList.stream()
                .map(this::rolToRolDTO)
                .collect(Collectors.toList());
    }

}
