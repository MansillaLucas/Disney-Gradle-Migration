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

import java.util.List;

public interface ModelMapperDTO {
    PeliculaResponseDTO peliculaToResponseDTO(Pelicula pelicula);

    PersonajeResponseDTO personajeToResponseDTO(Personaje personaje);

    List<PersonajeResponseDTO> listPersonajeToResponseDTO(List<Personaje> listPersonaje);

    Personaje personajeRequestDtoToPersonaje(PersonajeRequestDTO personajeRequestDTO);

    GeneroResponseDTO generoToResponseDTO(Genero genero);

    Genero generoRequestDtoToPersonaje(GeneroRequestDTO generoRequestDTO);

    List<GeneroResponseDTO> listGeneroToResponseDTO(List<Genero> listGenero);

    List<AudioVisualResponseDTO> listAudiovisualToResponseDTO(List<AudioVisual> listAudiovisual);

    AudioVisualResponseDTO audioVisualToResponseDTO(AudioVisual audiovisual);

    AudioVisual audioVisualResponseToAudiovisual(AudioVisualResponseDTO audiovisualDTO);

    SerieResponseDTO serieToResponseDTO(Serie serie);

    Pelicula responseDtoToPelicula(PeliculaResponseDTO peliculaResponseDTO);

    Serie responseDtoToSerie(SerieResponseDTO serieResponseDTO);

    SerieDtoPatch seriePatchDto(Serie serie);

    PeliculaPatchDTO peliculaPatchDTO(Pelicula pelicula);

    Serie requestDtoToSerie(SerieRequestDTO serieRequestDTO);

    List<UsuarioResponseDTO> listUsuarioToUsuarioDTO(List<Usuario> usuarioList);

    UsuarioResponseDTO usuarioToUsuarioDTO(Usuario usuario);

    Usuario usuarioDTOToUsuario(UsuarioResponseDTO usuarioResponseDTO);

    RolResponseDTO rolToRolDTO(Rol rol);

    Rol rolDTOtoRol(RolResponseDTO rolResponseDTO);

    List<RolResponseDTO> listRolToRolDTO(List<Rol> rolList);

    Pelicula requestDtoToPelicula(PeliculaRequestDTO peliculaRequestDTO);

}
