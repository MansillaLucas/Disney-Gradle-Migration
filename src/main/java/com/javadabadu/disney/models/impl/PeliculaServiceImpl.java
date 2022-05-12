package com.javadabadu.disney.models.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.PeliculaController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PeliculaPatchDTO;
import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.GeneroRepository;
import com.javadabadu.disney.repository.PeliculaRepository;
import com.javadabadu.disney.service.PeliculaService;
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
public class PeliculaServiceImpl implements PeliculaService {
    @Autowired
    PeliculaRepository peliculaRepository;
    @Autowired
    GeneroRepository generoRepository;
    @Autowired
    private MessageSource message;
    @Autowired
    private ModelMapperDTOImp mm;

    @Override
    public List<PeliculaResponseDTO> findAll() throws ExceptionBBDD {
        List<PeliculaResponseDTO> peliculaResponseDTO = new ArrayList<>();
        peliculaRepository.findAll().stream()
                .filter(audioVisual -> audioVisual instanceof Pelicula)
                .forEach(audioVisual -> peliculaResponseDTO.add(mm.peliculaToResponseDTO((Pelicula) audioVisual)));
        return peliculaResponseDTO;
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

    public Pelicula getEntitySave(Pelicula entity, Integer id) throws ExceptionBBDD {
        Pelicula source = null;
        setGenero(entity, entity.getGenero().getId());
        try {
            if (existsById(id)) {
                source = mm.responseDtoToPelicula(findById(id));
                entity.setId(id);
                source = entity;
                return source;
            } else {
                return entity;
            }
        }  catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD(message.getMessage("error.admin", null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    private void setGenero(Pelicula entity, Integer idGenero) throws ExceptionBBDD {
        Genero genero = generoRepository.findById(idGenero).
                orElseThrow(() -> new ExceptionBBDD
                        (message.getMessage("id.genero.not.exist", new String[]{Integer.toString(idGenero)}, Locale.US),HttpStatus.NOT_FOUND));
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
                    throw new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US),HttpStatus.NOT_FOUND);
                }
            } catch (ExceptionBBDD ebd) {
                throw new ExceptionBBDD(message.getMessage("error.admin", null, Locale.US), HttpStatus.BAD_REQUEST);
            }
    }

    @Override
    public Pelicula getEntity(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        ObjectMapper mapper = new ObjectMapper();

        PeliculaPatchDTO peliculaDTO = getPeliculaDtoToModify(id, propiedades);
        Map<String, Object> searchedPeliculaMap = mapper.convertValue(peliculaDTO, Map.class);
        propiedades.forEach((k, v) -> {
            if (searchedPeliculaMap.containsKey(k)) {
                searchedPeliculaMap.replace(k, searchedPeliculaMap.get(k), v);
            }
        });

        Pelicula toPersist = mapper.convertValue(searchedPeliculaMap, Pelicula.class);

        return toPersist;
    }

    private PeliculaPatchDTO getPeliculaDtoToModify(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        Pelicula pelicula = findPelicula(id);

        if(propiedades.containsKey("genero")){
            Map<String, Object> propID = (Map<String, Object>) propiedades.get("genero");
            Integer idGenero = (Integer) propID.get("id");
            setGenero(pelicula,idGenero);
        }

        PeliculaPatchDTO peliculaDTO = mm.peliculaPatchDTO(pelicula);

        peliculaDTO.setCalificacion(peliculaDTO.getCalificacion()-1);

        return peliculaDTO;
    }

    public Pelicula findPelicula(Integer id) throws ExceptionBBDD{
        AudioVisual av = peliculaRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US),HttpStatus.NOT_FOUND));
        if (av instanceof Pelicula) {
            return (Pelicula) av;
        }
        throw new ExceptionBBDD(message.getMessage("id.not.movie", new String[]{Integer.toString(id)}, Locale.US),HttpStatus.NOT_FOUND);
    }
}
