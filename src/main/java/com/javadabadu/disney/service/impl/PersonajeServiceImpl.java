package com.javadabadu.disney.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.PersonajeController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Personaje;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.PersonajeRepository;
import com.javadabadu.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    PersonajeRepository personajeRepository;

    @Autowired
    private ModelMapperDTOImp mapperDTO;

    @Override
    public Personaje getEntitySave(Personaje entity, Integer id) throws ExceptionBBDD {
        return null;
    }

    @Override
    public PersonajeResponseDTO save(Personaje personaje) throws ExceptionBBDD {
        try {
            return mapperDTO.personajeToResponseDTO(personajeRepository.save(personaje));
        } catch (Exception ebd) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<PersonajeResponseDTO> findAll() throws ExceptionBBDD {
        try {
            return mapperDTO.listPersonajeToResponseDTO(personajeRepository.findAll());
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transacción, contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public PersonajeResponseDTO findById(Integer id) throws ExceptionBBDD {
        Personaje personaje = personajeRepository.findById(id).orElseThrow(() -> new ExceptionBBDD("Id no válido", HttpStatus.NOT_FOUND));
        return mapperDTO.personajeToResponseDTO(personaje);
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {
        try {
            if (personajeRepository.softDelete(id)) {
                return "Se elimino el personaje seleccionado";
            } else {
                throw new ExceptionBBDD("Id no encontrado", HttpStatus.NOT_FOUND);
            }

        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Boolean existsById(Integer id) throws ExceptionBBDD {
        try {
            if (personajeRepository.existsById(id)) {
                return personajeRepository.existsById(id);
            } else {
                return false;
            }

        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {

        if (personajeRepository.lastValueId() >= 1) {
            return personajeRepository.lastValueId();
        } else {
            throw new ExceptionBBDD("Error en la transacción, contactese con el ADMIN", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<PersonajeResponseDTO> filterCharacter(String name, Integer edad, Integer idMovie) throws ExceptionBBDD {
        try {
            if (name != null && edad != null) {
                return mapperDTO.listPersonajeToResponseDTO(personajeRepository.findByEdadYNombre(name, edad));
            } else if (name != null) {
                return mapperDTO.listPersonajeToResponseDTO(personajeRepository.findByNombre(name));
            } else if (edad != null) {
                return mapperDTO.listPersonajeToResponseDTO(personajeRepository.findByEdad(edad));
            } else if (idMovie != null) {
                return mapperDTO.listPersonajeToResponseDTO(personajeRepository.findByMovieId(idMovie));
            } else {
                return mapperDTO.listPersonajeToResponseDTO(personajeRepository.findAll());
            }
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public PersonajeResponseDTO getSaveUpdateEntity(PersonajeRequestDTO personajeRequest, Integer id) throws ExceptionBBDD {
        Personaje personaje = mapperDTO.personajeRequestDtoToPersonaje(personajeRequest);
        try {
            if (!existsById(id)) {
                return save(personaje);
            }
            Personaje personajePorActualizar = personajeRepository.findById(id).orElseThrow(() -> new ExceptionBBDD("Id no válido"));
            personaje.setId(id);
            personajePorActualizar = personaje;
            return save(personajePorActualizar);
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public Personaje getEntity(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {

        ObjectMapper mapper = new ObjectMapper();

        try {
            PersonajeResponseDTO searchedPersonajeDTO = findById(id);

            Map<String, Object> searchedPersonajeMap = mapper.convertValue(searchedPersonajeDTO, Map.class);
            propiedades.forEach((k, v) -> {
                if (searchedPersonajeMap.containsKey(k)) {
                    searchedPersonajeMap.replace(k, searchedPersonajeMap.get(k), v);
                }
            });

            Personaje searchedPersonaje2 = mapper.convertValue(searchedPersonajeMap, Personaje.class);

            return searchedPersonaje2;
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public PersonajeResponseDTO updatePartiel(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {

        ObjectMapper mapper = new ObjectMapper();

        try {
            PersonajeResponseDTO searchedPersonajeDTO = findById(id);

            Map<String, Object> searchedPersonajeMap = mapper.convertValue(searchedPersonajeDTO, Map.class);
            propiedades.forEach((k, v) -> {
                if (searchedPersonajeMap.containsKey(k)) {
                    searchedPersonajeMap.replace(k, searchedPersonajeMap.get(k), v);
                }
            });

            Personaje searchedPersonaje2 = mapper.convertValue(searchedPersonajeMap, Personaje.class);

            return save(searchedPersonaje2);
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    public Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(PersonajeController.class).findAll(request)).withRel("Personajes:");
        } catch (ExceptionBBDD ebd2) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }

    }

    public Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(PersonajeController.class).findById(id, request)).withSelfRel();
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

}
