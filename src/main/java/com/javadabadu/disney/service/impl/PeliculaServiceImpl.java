package com.javadabadu.disney.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.PeliculaController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.patch.PeliculaPatchDTO;
import com.javadabadu.disney.models.dto.request.PeliculaRequestDTO;
import com.javadabadu.disney.models.dto.response.AudioVisualResponseDTO;
import com.javadabadu.disney.models.dto.response.PeliculaResponseDTO;
import com.javadabadu.disney.models.entity.*;
import com.javadabadu.disney.models.mapped.ModelMapperDTO;
import com.javadabadu.disney.repository.GeneroRepository;
import com.javadabadu.disney.repository.PeliculaRepository;
import com.javadabadu.disney.repository.PersonajeRepository;
import com.javadabadu.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PeliculaServiceImpl implements PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private PersonajeRepository personajeRepository;
    @Autowired
    private MessageSource message;
    @Autowired
    private ModelMapperDTO mm;

    @Override
    public List<PeliculaResponseDTO> findAll() throws ExceptionBBDD {
        try {
            return peliculaRepository.findAll().stream()
                    .filter(Pelicula.class::isInstance)
                    .map(audioVisual -> mm.peliculaToResponseDTO((Pelicula) audioVisual))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }
    }

    @Override
    public PeliculaResponseDTO findById(Integer id) throws ExceptionBBDD {
        return mm.peliculaToResponseDTO(findPelicula(id));
    }

    @Override
    public Boolean existsById(Integer id) {
        return peliculaRepository.existsById(id);
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        if (peliculaRepository.lastValueId() >= 1) {
            return peliculaRepository.lastValueId();
        } else {
            throw new ExceptionBBDD(message.getMessage("error.admin", null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    private void setGenero(Pelicula entity, Integer idGenero) throws ExceptionBBDD {
        Genero genero = generoRepository.findById(idGenero).
                orElseThrow(() -> new ExceptionBBDD
                        (message.getMessage("id.genero.not.exist", new String[]{Integer.toString(idGenero)}, Locale.US), HttpStatus.NOT_FOUND));
        entity.setGenero(genero);
    }

    @Override
    public PeliculaResponseDTO save(Pelicula entity) {
        return mm.peliculaToResponseDTO(peliculaRepository.save(entity));
    }

    @Override
    public Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(PeliculaController.class).findById(id, request)).withSelfRel();
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD(message.getMessage("error.admin", null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(PeliculaController.class).findAll(request)).withRel("Peliculas:");
        } catch (ExceptionBBDD ebd2) {
            throw new ExceptionBBDD(message.getMessage("error.admin", null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {
        try {
            if (peliculaRepository.softDelete(id)) {
                return message.getMessage("delete.success", null, Locale.US);
            } else {
                throw new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND);
            }
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD(message.getMessage("error.admin", null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    private PeliculaPatchDTO getPeliculaDtoToModify(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        Pelicula pelicula = findPelicula(id);

        if (propiedades.containsKey("genero")) {
            Map<String, Object> propID = (Map<String, Object>) propiedades.get("genero");
            Integer idGenero = (Integer) propID.get("id");
            setGenero(pelicula, idGenero);
        }

        PeliculaPatchDTO peliculaDTO = mm.peliculaPatchDTO(pelicula);

        peliculaDTO.setCalificacion(peliculaDTO.getCalificacion() - 1);

        return peliculaDTO;
    }

    public Pelicula findPelicula(Integer id) throws ExceptionBBDD {
        AudioVisual av = peliculaRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND));
        if (av instanceof Pelicula) {
            return (Pelicula) av;
        }
        throw new ExceptionBBDD(message.getMessage("id.not.movie", new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND);
    }

    @Override
    public PeliculaResponseDTO getPersistenceEntity(PeliculaRequestDTO peliculaRequestDTO, Integer id) throws ExceptionBBDD {
        Pelicula pelicula = mm.requestDtoToPelicula(peliculaRequestDTO);
        pelicula.setId(id);
        return save(pelicula);
    }

    @Override
    public PeliculaResponseDTO updatePartial(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        ObjectMapper mapper = new ObjectMapper();

        PeliculaPatchDTO peliculaDTO = getPeliculaDtoToModify(id, propiedades);
        Map<String, Object> searchedPeliculaMap = mapper.convertValue(peliculaDTO, Map.class);
        propiedades.forEach((k, v) -> {
            if (searchedPeliculaMap.containsKey(k)) {
                searchedPeliculaMap.replace(k, searchedPeliculaMap.get(k), v);
            }
        });
        Pelicula toPersist = mapper.convertValue(searchedPeliculaMap, Pelicula.class);
        return save(toPersist);
    }

    public AudioVisualResponseDTO joinPersonajes(Integer idPelicula, List<Integer> idPersonajes) throws ExceptionBBDD {
        Pelicula pelicula = findPelicula(idPelicula);
        pelicula.setPersonajes(personajeRepository.getByIdIn(idPersonajes));
        return mm.peliculaToResponseDTO(peliculaRepository.save(pelicula));
    }

    @Override
    public AudioVisualResponseDTO removePersonaje(Integer idPelicula, List<Integer> personajesToDelete) throws ExceptionBBDD {
        Pelicula pelicula = findPelicula(idPelicula);

        List<Personaje> personajeList = pelicula.getPersonajes(),
                personajesDeleted = personajeRepository.getByIdIn(personajesToDelete);

        if (!personajesDeleted.isEmpty()) {

            personajeList.removeAll(personajesDeleted);

            pelicula.setPersonajes(personajeList);

            return mm.peliculaToResponseDTO(peliculaRepository.save(pelicula));

        } else {
            throw new ExceptionBBDD("No se encontraron los personajes en la BBDD", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<AudioVisualResponseDTO> filterAudiovisual(String titulo, Integer idGenero, String order) throws ExceptionBBDD {
        List<AudioVisual> listaAv;
        if (titulo != null) {
            listaAv = peliculaRepository.findByTituloPelicula(titulo);
            return toOrderList(listaAv, order);
        } else if (idGenero != null) {
            listaAv = peliculaRepository.findByGeneroIdPelicula(idGenero);
            return toOrderList(listaAv, order);
        }
        throw new ExceptionBBDD(message.getMessage("filter.av.not.found", null, Locale.US), HttpStatus.NOT_FOUND);
    }

    private List<AudioVisualResponseDTO> toOrderList(List<AudioVisual> listaAv, String order) throws ExceptionBBDD{
        if (listaAv.size() > 0 && (order == null || order.equalsIgnoreCase("asc"))) {
            return mm.listSerieToResponseDTO(listaAv.stream().sorted(Comparator.comparing(AudioVisual::getFechaCreacion)).collect(Collectors.toList()));
        } else if (listaAv.size() > 0 && (order.equalsIgnoreCase("desc"))) {
            return mm.listSerieToResponseDTO(listaAv.stream().sorted(Comparator.comparing(AudioVisual::getFechaCreacion).reversed()).collect(Collectors.toList()));
        } else {
            throw new ExceptionBBDD(message.getMessage("filter.av.not.found", null, Locale.US), HttpStatus.NOT_FOUND);
        }
    }

}
