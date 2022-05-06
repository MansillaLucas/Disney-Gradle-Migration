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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = Uri.PERSONAJES)
public class PersonajeController {

    @Autowired
    PersonajeService personajeService;

    @Autowired
    ModelMapperDTOImp mapperDTO;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) {
        try {
            PersonajeResponseDTO personajeDTO = personajeService.findById(id);
            return ResponseEntity.ok().body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request), personajeService.getCollectionLink(request)));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        try {
            List<PersonajeResponseDTO> listPersonajeResponseDTO = personajeService.findAll();

            List<EntityModel<PersonajeResponseDTO>> personajes = listPersonajeResponseDTO.stream()
                    .map(personaje -> EntityModel.of(personaje, personajeService.getSelfLink(personaje.getId(), request)))
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(CollectionModel.of(personajes, personajeService.getCollectionLink(request)));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> lastId(HttpServletRequest request) {
        try {
            return ResponseEntity.created(URI.create(request.getRequestURI() + personajeService.lastValueId())).body("se creo un registro");
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Personaje personaje, @PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {

        try {
            Personaje source = personajeService.getEntity(personaje, id);
            PersonajeResponseDTO personajeDTO = personajeService.save(source);
            return ResponseEntity.ok().body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request), personajeService.getCollectionLink(request)));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody Map<String, Object> propiedades,
                                    HttpServletRequest request) {
        try {
            Personaje searchedPersonaje = personajeService.getEntity(id, propiedades);
            PersonajeResponseDTO personajeDTO = personajeService.save(searchedPersonaje);
            return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request)));
        } catch (ExceptionBBDD ebd) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(ebd.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        try {
            String body = personajeService.softDelete(personajeService.findById(id).getId());
            ResponseInfoDTO response = new ResponseInfoDTO(body, request.getRequestURI(), HttpStatus.OK.value());
            return ResponseEntity.ok().body(EntityModel.of(response, personajeService.getCollectionLink(request)));
        } catch (ExceptionBBDD ebd) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(ebd.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));

        }
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllFilter(@RequestParam(value = "name", required = false) String nombre,
                                           @RequestParam(value = "age", required = false) Integer edad,
                                           @RequestParam(value = "movies", required = false) Integer idPelicula,
                                           HttpServletRequest request) {
        try {
            List<PersonajeResponseDTO> listPersonajeResponseDTO = personajeService.filterCharacter(nombre, edad, idPelicula);

            List<EntityModel<PersonajeResponseDTO>> personajes = listPersonajeResponseDTO.stream()
                    .map(personaje -> EntityModel.of(personaje, personajeService.getSelfLink(personaje.getId(), request)))
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(CollectionModel.of(personajes, personajeService.getCollectionLink(request)));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
