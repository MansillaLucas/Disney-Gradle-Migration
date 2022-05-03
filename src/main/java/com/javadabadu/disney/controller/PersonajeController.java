package com.javadabadu.disney.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Personaje;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = Uri.PERSONAJES)
public class PersonajeController {
    @Autowired
    PersonajeService personajeService;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) {
        try {
            Personaje personaje = personajeService.findById(id);
            return ResponseEntity.ok().body(EntityModel.of(personaje, linkTo(methodOn(PersonajeController.class).findById(id, request)).withSelfRel(), linkTo(methodOn(PersonajeController.class).findAll(request)).withRel("Personajes:")));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        try {
            List<EntityModel<Personaje>> personajes = personajeService.findAll().stream().map(personaje -> EntityModel.of(personaje, linkTo(methodOn(PersonajeController.class).findById(personaje.getId(), request)).withSelfRel())).collect(Collectors.toList());
            return ResponseEntity.ok().body(CollectionModel.of(personajes, linkTo(methodOn(PersonajeController.class).findAll(request)).withSelfRel()));
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
            Personaje source = getPersonaje(personaje, id);
            return ResponseEntity.ok().body(EntityModel.of(personajeService.save(source), linkTo(methodOn(PersonajeController.class).findById(id, request)).withSelfRel(), linkTo(methodOn(PersonajeController.class).findAll(request)).withRel("Personajes:")));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @PatchMapping(path = "/{id}", consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody Map<String, Object> propiedades, HttpServletRequest request) {
        try {
            Personaje searchedPersonaje = getPersonaje(id, propiedades);

            return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(personajeService.save(searchedPersonaje), linkTo(methodOn(PersonajeController.class).findById(id, request)).withSelfRel()));

        } catch (ExceptionBBDD ebd) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(ebd.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        try {
            String body = personajeService.softDelete(personajeService.findById(id).getId());
            ResponseInfoDTO response = new ResponseInfoDTO(body, request.getRequestURI(), HttpStatus.OK.value());
            return ResponseEntity.ok().body(EntityModel.of(response, linkTo(methodOn(PersonajeController.class).findAll(request)).withRel("Personajes:")));
        } catch (ExceptionBBDD ebd) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(ebd.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));

        }
    }

    private Personaje getPersonaje(Personaje personaje, Integer id) throws ExceptionBBDD {
        Personaje source = null;
        if (personajeService.existsById(id)) {
            source = personajeService.findById(id);
            personaje.setId(id);
            source = personaje;
            return source;
        } else {
            return personaje;
        }
    }

    private Personaje getPersonaje(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {

        ObjectMapper mapper = new ObjectMapper();
        Personaje searchedPersonaje = personajeService.findById(id);


        Map<String, Object> searchedPersonajeMap = mapper.convertValue(searchedPersonaje, Map.class);
        propiedades.forEach((k, v) -> {
            if (searchedPersonajeMap.containsKey(k)) {
                searchedPersonajeMap.replace(k, searchedPersonajeMap.get(k), v);
            }
        });
        searchedPersonaje = mapper.convertValue(searchedPersonajeMap, Personaje.class);
        return searchedPersonaje;
    }


/*    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String nombre,
                                     @RequestParam(value = "age", required = false) Integer edad,
                                     @RequestParam(value = "movies", required = false) Integer idPelicula,
                                     HttpServletRequest request) {
        try {
            List<EntityModel<Personaje>> personajes = personajeService.findAll().stream()
                    .map(personaje -> EntityModel.of(personaje, linkTo(methodOn(PersonajeController.class).findById(personaje.getId(), request)).withSelfRel()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(CollectionModel.of(personajes, linkTo(methodOn(PersonajeController.class).findAll(request)).withSelfRel()));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
        }
    }*/
}
