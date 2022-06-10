package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.RolResponseDTO;
import com.javadabadu.disney.models.dto.UsuarioResponseDTO;
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
        return modelMapper.map(pelicula, PeliculaResponseDTO.class);
    }

    @Override
    public PersonajeResponseDTO personajeToResponseDTO(Personaje personaje) {
        return modelMapper.map(personaje, PersonajeResponseDTO.class);
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
        return modelMapper.map(genero, GeneroResponseDTO.class);
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
        return modelMapper.map(audiovisual, AudioVisualResponseDTO.class);
    }

    @Override
    public List<AudioVisualResponseDTO> listAudiovisualToResponseDTO(List<AudioVisual> listAudiovisual) {
        return listAudiovisual.stream()
                .map(this::audioVisualToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AudioVisual audioVisualResponseToAudiovisual(AudioVisualResponseDTO audiovisualDTO) {
        return modelMapper.map(audiovisualDTO, AudioVisual.class);
    }

    @Override
    public SerieResponseDTO serieToResponseDTO(Serie serie) {
        return modelMapper.map(serie, SerieResponseDTO.class);
    }

    @Override
    public Serie responseDtoToSerie(SerieResponseDTO serieResponseDTO) {
        return modelMapper.map(serieResponseDTO, Serie.class);
    }

    @Override
    public SerieDtoPatch seriePatchDto(Serie serie) {
        return modelMapper.map(serie, SerieDtoPatch.class);
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

    public Pelicula requestDtoToPelicula(PeliculaRequestDTO peliculaRequestDTO) {
        return modelMapper.map(peliculaRequestDTO, Pelicula.class);

    }

    @Override
    public List<AudioVisualResponseDTO> listSerieToResponseDTO(List<AudioVisual> listSerie) {
         return listSerie.stream()
                .map(this::audioVisualToResponseDTO)
                .collect(Collectors.toList());
    }

}
