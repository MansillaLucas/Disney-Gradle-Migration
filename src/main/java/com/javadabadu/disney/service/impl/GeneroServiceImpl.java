package com.javadabadu.disney.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.GeneroController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.request.GeneroRequestDTO;
import com.javadabadu.disney.models.dto.response.GeneroResponseDTO;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.mapped.ModelMapperDTO;
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

import static com.javadabadu.disney.util.MessageConstants.ADMIN_ERROR;
import static com.javadabadu.disney.util.MessageConstants.ID_NOT_FOUND;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private ModelMapperDTO mapperDTO;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private MessageSource message;

    @Override
    public GeneroResponseDTO save(Genero genero) throws ExceptionBBDD {
        try {
            return mapperDTO.generoToResponseDTO(genero);
        } catch (Exception ebd) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<GeneroResponseDTO> findAll() throws ExceptionBBDD {
        try {
            return mapperDTO.listGeneroToResponseDTO(generoRepository.findAll());
        } catch (Exception e) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public GeneroResponseDTO findById(Integer id) throws ExceptionBBDD {
        Genero genero = generoRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage(ID_NOT_FOUND, new String[]{Integer.toString(id)}, Locale.US), HttpStatus.NOT_FOUND));
        return mapperDTO.generoToResponseDTO(genero);
    }

    @Override
    public Boolean existsById(Integer id) throws ExceptionBBDD {
        try {
            return generoRepository.existsById(id);
        } catch (Exception e) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        try {
            return generoRepository.lastValueId();
        } catch (Exception e) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(GeneroController.class).findById(id, request)).withSelfRel();
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(GeneroController.class).findAll(request)).withRel("Generos:");
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {
        try {
            if (generoRepository.softDelete(id)) {
                return "Se elimino el genero seleccionado";
            } else {
                throw new ExceptionBBDD("");
            }
        } catch (ExceptionBBDD e) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public GeneroResponseDTO getPersistenceEntity(GeneroRequestDTO generoRequestDTO, Integer id) throws ExceptionBBDD {
        Genero genero = mapperDTO.generoRequestDtoToPersonaje(generoRequestDTO);
        try {
            if (id == null)
                id = generoRepository.lastValueId();
            genero.setId(id);
            return save(genero);
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public GeneroResponseDTO updatePartial(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GeneroResponseDTO searchedGeneroDTO = findById(id);
            Map<String, Object> searchedGeneroMap = mapper.convertValue(searchedGeneroDTO, Map.class);
            propiedades.forEach((k, v) -> {
                if (searchedGeneroMap.containsKey(k)) {
                    searchedGeneroMap.replace(k, searchedGeneroMap.get(k), v);
                }
            });
            return save(mapper.convertValue(searchedGeneroMap, Genero.class));
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD(message.getMessage(ADMIN_ERROR, null, Locale.US), HttpStatus.BAD_REQUEST);
        }
    }
}
