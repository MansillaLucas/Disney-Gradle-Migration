package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.entity.Personaje;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.service.PersonajeService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = Uri.PERSONAJES)
public class PersonajeController {

    @Autowired
    PersonajeService personajeService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        PersonajeResponseDTO personajeDTO = personajeService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request), personajeService.getCollectionLink(request)));
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<PersonajeResponseDTO> listPersonajeResponseDTO = personajeService.findAll();
        List<EntityModel<PersonajeResponseDTO>> personajes = new ArrayList<>();
        for (PersonajeResponseDTO personaje : listPersonajeResponseDTO) {
            personajes.add(EntityModel.of(personaje, personajeService.getSelfLink(personaje.getId(), request)));
        }
        return ResponseEntity.ok().body(CollectionModel.of(personajes, personajeService.getCollectionLink(request)));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> lastId(HttpServletRequest request) throws ExceptionBBDD {
        return ResponseEntity.created(URI.create(request.getRequestURI() + personajeService.lastValueId())).body("Se creo un registro");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Personaje personaje,
                                   @PathVariable Integer id,
                                   HttpServletRequest request) throws ExceptionBBDD {
        Personaje source = personajeService.getEntitySave(personaje, id);
        PersonajeResponseDTO personajeDTO = personajeService.save(source);
        return ResponseEntity.ok().body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request), personajeService.getCollectionLink(request)));

    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody Map<String, Object> propiedades,
                                    HttpServletRequest request) throws ExceptionBBDD {
        Personaje searchedPersonaje = personajeService.getEntity(id, propiedades);
        PersonajeResponseDTO personajeDTO = personajeService.save(searchedPersonaje);
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request)));

    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        String body = personajeService.softDelete(personajeService.findById(id).getId());
        ResponseInfoDTO response = new ResponseInfoDTO(body, request.getRequestURI(), HttpStatus.OK.value());
        return ResponseEntity.ok().body(EntityModel.of(response, personajeService.getCollectionLink(request)));

    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findAllFilter(@RequestParam(value = "name", required = false) String nombre,
                                           @RequestParam(value = "age", required = false) Integer edad,
                                           @RequestParam(value = "movies", required = false) Integer idPelicula,
                                           HttpServletRequest request) throws ExceptionBBDD {

        List<PersonajeResponseDTO> listPersonajeResponseDTO = personajeService.filterCharacter(nombre, edad, idPelicula);
        List<EntityModel<PersonajeResponseDTO>> personajes = new ArrayList<>();
        for (PersonajeResponseDTO personaje : listPersonajeResponseDTO) {
            personajes.add(EntityModel.of(personaje, personajeService.getSelfLink(personaje.getId(), request)));
        }
        return ResponseEntity.ok().body(CollectionModel.of(personajes, personajeService.getCollectionLink(request)));
    }

}
