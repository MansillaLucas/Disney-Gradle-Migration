package com.javadabadu.disney.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.GeneroController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.GeneroResponseDTO;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.GeneroRepository;
import com.javadabadu.disney.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private MessageSource message;

    @Autowired
    ModelMapperDTOImp mapperDTO;

    @Override
    public GeneroResponseDTO save(Genero genero) throws ExceptionBBDD {
        try {
            return mapperDTO.generoToResponseDTO(generoRepository.save(genero));
        } catch (Exception ebd) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<GeneroResponseDTO> findAll() throws ExceptionBBDD {
        try {
            return mapperDTO.listGeneroToResponseDTO(generoRepository.findAll());
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transacción, contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public GeneroResponseDTO findById(Integer id) throws ExceptionBBDD {
        Genero genero = generoRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND));
        return mapperDTO.generoToResponseDTO(genero);
    }

    @Override
    public Boolean existsById(Integer id) throws ExceptionBBDD {
        try {
            if (generoRepository.existsById(id)) {
                return generoRepository.existsById(id);
            } else {
                return false;
            }

        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        if (generoRepository.lastValueId() >= 1) {
            return generoRepository.lastValueId();
        } else {
            throw new ExceptionBBDD("Error en la transacción, contactese con el ADMIN", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Genero getEntitySave(Genero genero, Integer id) throws ExceptionBBDD {
        try {
            if (!existsById(id)) {
                return genero;
            }
            Genero source = generoRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US), HttpStatus.BAD_REQUEST));
            genero.setId(id);
            source = genero;
            return source;

        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(GeneroController.class).findById(id, request)).withSelfRel();
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(GeneroController.class).findAll(request)).withRel("Generos:");
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {
        try {
            if (generoRepository.softDelete(id)) {
                return "Se elimino el genero seleccionado";
            } else {
                throw new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND);
            }

        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Genero getEntity(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        ObjectMapper mapper = new ObjectMapper();

        try {
            GeneroResponseDTO searchedGeneroDTO = findById(id);

            Map<String, Object> searchedGeneroMap = mapper.convertValue(searchedGeneroDTO, Map.class);
            propiedades.forEach((k, v) -> {
                if (searchedGeneroMap.containsKey(k)) {
                    searchedGeneroMap.replace(k, searchedGeneroMap.get(k), v);
                }
            });

            Genero searchedGenero2 = mapper.convertValue(searchedGeneroMap, Genero.class);

            return searchedGenero2;
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }
}
