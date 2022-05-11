package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.controller.SerieController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Pelicula;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        AudioVisual av = serieRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US),HttpStatus.NOT_FOUND));
        if (av instanceof Serie) {
            Serie s = (Serie) av;
            SerieResponseDTO serieResponseDTO = mm.serieToResponseDTO(s);
            return serieResponseDTO;
        }
        throw new ExceptionBBDD(message.getMessage("id.not.serie", new String[]{Integer.toString(id)}, Locale.US),HttpStatus.NOT_FOUND);
    }

    @Override
    public Boolean existsById(Integer id) {

        return serieRepository.existsById(id);
    }

    //TODO implementar metodo


    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        if (serieRepository.lastValueId()>=1){
            return serieRepository.lastValueId();
        }
        throw new ExceptionBBDD("Error en la transacción, contactese con el ADMIN", HttpStatus.BAD_REQUEST);
    }


    //TODO restan metodos de guardar y actualizar (agregar tambien in interfaz correspondiente)

    @Override
    public Serie getEntitySave(Serie entity, Integer id) throws ExceptionBBDD {
        Serie source = null;
        setGenero(entity);
        if (existsById(id)){
            source = mm.responseDtoToSerie(findById(id));
            entity.setId(id);
            source = entity;
            return  source;
        }
        return entity;
    }

    @Override
    public SerieResponseDTO save(Serie entity) {
       return mm.serieToResponseDTO(serieRepository.save(entity));
    }

    @Override
    public Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD {

        try{
            return linkTo(methodOn(SerieController.class).findById(id,request)).withSelfRel();
        }catch(ExceptionBBDD e){
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD {
        try{
            return linkTo(methodOn(SerieController.class).findAll(request)).withRel("Series");
        }catch(ExceptionBBDD e){
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {
        return null;
    }

    @Override
    public Serie getEntity(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        return null;
    }

    private void setGenero(Serie entity) throws ExceptionBBDD {
        Integer idGenero = entity.getGenero().getId();
        Genero genero = generoRepository.findById(idGenero).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.genero.not.exist", new String[]{Integer.toString(idGenero)}, Locale.US)));
        entity.setGenero(genero);
    }
}
