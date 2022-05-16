package com.javadabadu.disney.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.SerieController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.SerieDtoPatch;
import com.javadabadu.disney.models.dto.SerieRequestDTO;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Serie;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.GeneroRepository;
import com.javadabadu.disney.repository.SerieRepository;
import com.javadabadu.disney.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SerieServiceImpl implements SerieService {
    @Autowired
    SerieRepository serieRepository;
    @Autowired
    GeneroRepository generoRepository;
    @Autowired
    private MessageSource message;
    @Autowired
    private ModelMapperDTOImp mm;

    @Override
    public List<SerieResponseDTO> findAll() throws ExceptionBBDD {
        List<SerieResponseDTO> serieResponseDTO = new ArrayList<>();
        try {
            serieRepository.findAll().stream()
                    .filter(audioVisual -> audioVisual instanceof Serie)
                    .forEach(audioVisual -> serieResponseDTO.add(mm.serieToResponseDTO((Serie) audioVisual)));
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }

        return serieResponseDTO;
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
        throw new ExceptionBBDD("Error en la transacción, contactese con el ADMIN", HttpStatus.BAD_REQUEST);
    }

    @Override
    public Serie getEntitySave(Serie entity, Integer id) throws ExceptionBBDD {
        Serie source = null;
        setGenero(entity, entity.getGenero().getId());
        if (existsById(id)) {
            source = mm.responseDtoToSerie(findById(id));
            entity.setId(id);
            source = entity;
            return source;
        }
        return entity;
    }

    @Override
    public SerieResponseDTO save(Serie entity) {
        return mm.serieToResponseDTO(serieRepository.save(entity));
    }

    @Override
    public Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD {

        try {
            return linkTo(methodOn(SerieController.class).findById(id, request)).withSelfRel();
        } catch (ExceptionBBDD e) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(SerieController.class).findAll(request)).withRel("Series");
        } catch (ExceptionBBDD e) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
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
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Serie getEntity(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        ObjectMapper mapper = new ObjectMapper();

        SerieDtoPatch serieDtoP = getSerieToModify(id, propiedades);
        Map<String, Object> searchedSerieMap = mapper.convertValue(serieDtoP, Map.class);

        propiedades.forEach((k, v) -> {
            if (searchedSerieMap.containsKey(k)) {
                searchedSerieMap.replace(k, searchedSerieMap.get(k), v);
            }
        });
        Serie searchedSerieMap2 = mapper.convertValue(searchedSerieMap, Serie.class);
        return searchedSerieMap2;
    }

    private void setGenero(Serie entity, Integer idGenero) throws ExceptionBBDD {
        Genero genero = generoRepository.findById(idGenero).
                orElseThrow(() -> new ExceptionBBDD
                        (message.getMessage("id.genero.not.exist", new String[]{Integer.toString(idGenero)}, Locale.US), HttpStatus.NOT_FOUND));
        entity.setGenero(genero);
    }

    public Serie findSerie(Integer id) throws ExceptionBBDD {
        AudioVisual av = serieRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND));

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
        SerieDtoPatch serieDtoP = mm.seriePatchDto(serie);
        return serieDtoP;
    }

    @Override
    public Serie getSaveEntity(SerieRequestDTO serieRequestDTO, Integer id) throws ExceptionBBDD {
        Serie source = null;
        setGeneroForRequest(serieRequestDTO, serieRequestDTO.getGenero().getId());
        if (existsById(id)) {
            source = mm.requestDtoToSerie(serieRequestDTO);
            source.setId(id);
            return source;
        }
        return mm.requestDtoToSerie(serieRequestDTO);
    }

    private void setGeneroForRequest(SerieRequestDTO serieRequestDTO, Integer idGenero) throws ExceptionBBDD {
        Genero genero = generoRepository.findById(idGenero).
                orElseThrow(() -> new ExceptionBBDD
                        (message.getMessage("id.genero.not.exist", new String[]{Integer.toString(idGenero)}, Locale.US), HttpStatus.NOT_FOUND));
        serieRequestDTO.setGenero(mm.generoToResponseDTO(genero));
    }

}