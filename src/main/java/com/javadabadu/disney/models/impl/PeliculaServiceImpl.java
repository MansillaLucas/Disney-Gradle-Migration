package com.javadabadu.disney.models.impl;

import com.javadabadu.disney.controller.PeliculaController;
import com.javadabadu.disney.exception.ExceptionBBDD;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        AudioVisual av = peliculaRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
        if (av instanceof Pelicula) {
            Pelicula p = (Pelicula) av;
            PeliculaResponseDTO peliculaResponseDTO = mm.peliculaToResponseDTO(p);
            return peliculaResponseDTO;
        }
        throw new ExceptionBBDD(message.getMessage("id.not.movie", new String[]{Integer.toString(id)}, Locale.US));
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
            throw new ExceptionBBDD(message.getMessage("error.admin", null, Locale.US));
        }
    }

    public Pelicula getEntitySave(Pelicula entity, Integer id) throws ExceptionBBDD {
        Pelicula source = null;
        setGenero(entity);
        if (existsById(id)) {
            source = mm.responseDtoToPelicula(findById(id));
            entity.setId(id);
            source = entity;
            return source;
        } else {
            return entity;
        }
    }

    private void setGenero(Pelicula entity) throws ExceptionBBDD {
        Integer idGenero = entity.getGenero().getId();
        Genero genero = generoRepository.findById(idGenero).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.genero.not.exist", new String[]{Integer.toString(idGenero)}, Locale.US)));
        entity.setGenero(genero);
    }

    @Override
    public PeliculaResponseDTO save(Pelicula entity) {
        return mm.peliculaToResponseDTO(peliculaRepository.save(entity));
    }

    @Override
    public Link getSelfLink(Integer id, HttpServletRequest request)  {
        return null /*linkTo(methodOn(PeliculaController.class).findById(id, request)).withSelfRel()*/;
    }

    @Override
    public Link getCollectionLink(HttpServletRequest request) {
        return null/* linkTo(methodOn(PeliculaController.class).findAll(request)).withRel("Peliculas:")*/;
    }

    @Override
    public String softDelete(Integer id)  {
        return null;
    }

    @Override
    public Pelicula getEntity(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        return null;
    }


    //TODO restan metodos de guardar y actualizar (agregar tambien in interfaz correspondiente)

}
