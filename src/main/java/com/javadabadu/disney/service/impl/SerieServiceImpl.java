package com.javadabadu.disney.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.SerieController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.patch.SerieDtoPatch;
import com.javadabadu.disney.models.dto.request.SerieRequestDTO;
import com.javadabadu.disney.models.dto.response.AudioVisualResponseDTO;
import com.javadabadu.disney.models.dto.response.SerieResponseDTO;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Personaje;
import com.javadabadu.disney.models.entity.Serie;
import com.javadabadu.disney.models.mapped.ModelMapperDTO;
import com.javadabadu.disney.repository.GeneroRepository;
import com.javadabadu.disney.repository.PersonajeRepository;
import com.javadabadu.disney.repository.SerieRepository;
import com.javadabadu.disney.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.javadabadu.disney.util.MessageConstants.ADMIN_ERROR;
import static com.javadabadu.disney.util.MessageConstants.ID_NOT_FOUND;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SerieServiceImpl implements SerieService {
    @Autowired
    private SerieRepository serieRepository;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private MessageSource message;
    @Autowired
    private ModelMapperDTO mm;

    @Override
    public List<SerieResponseDTO> findAll() throws ExceptionBBDD {
        try {
            return serieRepository.findAll().stream().filter(Serie.class::isInstance).map(audioVisual -> mm.serieToResponseDTO((Serie) audioVisual)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public SerieResponseDTO findById(Integer id) throws ExceptionBBDD {
        return mm.serieToResponseDTO(findSerie(id));
    }

    @Override
    public Boolean existsById(Integer id) {

        return serieRepository.existsById(id);
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        if (serieRepository.lastValueId() >= 1) {
            return serieRepository.lastValueId();
        }
        throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
    }

    @Override
    public SerieResponseDTO save(Serie entity) {
        var l = serieRepository.save(entity);
        return mm.serieToResponseDTO(l);
    }

    @Override
    public Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD {

        try {
            return linkTo(methodOn(SerieController.class).findById(id, request)).withSelfRel();
        } catch (ExceptionBBDD e) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(SerieController.class).findAll(request)).withRel("Series");
        } catch (ExceptionBBDD e) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {
        try {
            if (serieRepository.softDelete(id)) {
                return "Se elimino la serie seleccionada";
            } else {
                throw new ExceptionBBDD("Id no encontrado", HttpStatus.NOT_FOUND);
            }

        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    private void setGenero(Serie entity, Integer idGenero) throws ExceptionBBDD {
        Genero genero = generoRepository.findById(idGenero).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.genero.not.exist", new String[]{Integer.toString(idGenero)}, Locale.US), HttpStatus.NOT_FOUND));
        entity.setGenero(genero);
    }

    public Serie findSerie(Integer id) throws ExceptionBBDD {
        AudioVisual av = serieRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage(ID_NOT_FOUND, new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND));

        if (av instanceof Serie) {
            return (Serie) av;
        }
        throw new ExceptionBBDD(message.getMessage("id.not.serie", new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND);
    }


    private SerieDtoPatch getSerieToModify(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        Serie serie = findSerie(id);
        if (propiedades.containsKey("genero")) {
            Map<String, Object> propGenId = (Map<String, Object>) propiedades.get("genero");
            Integer idGenero = (Integer) propGenId.get("id");
            setGenero(serie, idGenero);
        }
        return mm.seriePatchDto(serie);
    }

    @Override
    public SerieResponseDTO getPersistenceEntity(SerieRequestDTO serieRequestDTO, Integer id) throws ExceptionBBDD {
        Serie serie = mm.requestDtoToSerie(serieRequestDTO);
        serie.setId(id);
        return save(serie);
    }

    @Override
    public SerieResponseDTO updatePartial(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        ObjectMapper mapper = new ObjectMapper();
        SerieDtoPatch serieDtoP = getSerieToModify(id, propiedades);
        Map<String, Object> searchedSerieMap = mapper.convertValue(serieDtoP, Map.class);
        propiedades.forEach((k, v) -> {
            if (searchedSerieMap.containsKey(k)) {
                searchedSerieMap.replace(k, searchedSerieMap.get(k), v);
            }
        });
        Serie searchedSerieMap2 = mapper.convertValue(searchedSerieMap, Serie.class);
        return save(searchedSerieMap2);
    }

   @Override
    public AudioVisualResponseDTO joinPersonajes(Integer idAudioVisual, List<Integer> idPersonajes) throws ExceptionBBDD {
        Serie serie = findSerie(idAudioVisual);
        if (!personajeRepository.getByIdIn(idPersonajes).isEmpty()) {

            serie.setPersonajes(personajeRepository.getByIdIn(idPersonajes));
            return mm.serieToResponseDTO(serieRepository.save(serie));
        } else {
            throw new ExceptionBBDD("No se encontraron los personajes en la BBDD", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public AudioVisualResponseDTO removePersonaje(Integer idSerie, List<Integer> personajesToDelete) throws ExceptionBBDD {
        Serie serie = findSerie(idSerie);

        List<Personaje> personajeList = serie.getPersonajes();
        List<Personaje> personajesDeleted = personajeRepository.getByIdIn(personajesToDelete);

        if (!personajesDeleted.isEmpty()) {

            if ( personajeList.removeAll(personajesDeleted)){

                serie.setPersonajes(personajeList);

                return mm.serieToResponseDTO(serieRepository.save(serie));
            }else{
                throw new ExceptionBBDD("El personaje seleccionado no pertenece a esta pelicula", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ExceptionBBDD("No se encontraron los personajes en la BBDD", HttpStatus.NOT_FOUND);
        }
    }

}