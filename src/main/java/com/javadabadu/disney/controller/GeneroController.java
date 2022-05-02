package com.javadabadu.disney.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.service.GeneroService;
import com.javadabadu.disney.util.Uri;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@CrossOrigin("*")
@RequestMapping(value = Uri.GENEROS)
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) {
        try {
            Genero genero = generoService.findById(id);

            return ResponseEntity.ok().body(EntityModel.of(genero, linkTo(methodOn(GeneroController.class).findById(id, request)).withSelfRel(),
                    linkTo(methodOn(GeneroController.class).findAll(request)).withRel("Generos:")));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        List<EntityModel<Genero>> generos = generoService.findAll().stream().map(genero -> EntityModel.of(genero,
                        linkTo(methodOn(GeneroController.class).findById(genero.getId(), request)).withSelfRel()))
                .collect(Collectors.toList());


        return ResponseEntity.ok().body(CollectionModel.of(generos, linkTo(methodOn(GeneroController.class).findAll(request)).withSelfRel()));
    }

    @PostMapping("/")
    public ResponseEntity<?> lastId(HttpServletRequest request) {
        return ResponseEntity.created(URI.create(request.getRequestURI() + generoService.lastValueId())).body("se creo un registro");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Genero genero, @PathVariable Integer id, HttpServletRequest request) {
        Genero source = null;
        try {
            source = generoService.findById(id);
            source.setNombre(genero.getNombre());
            source.setAlta(genero.getAlta());
            source.setImagen(genero.getImagen());
            source.setPeliculas(genero.getPeliculas());
            return ResponseEntity.ok().body(EntityModel.of(generoService.save(source), linkTo(methodOn(GeneroController.class).findById(id, request)).withSelfRel()));
        } catch (ExceptionBBDD e) {
            genero.setId(id);
            genero.setAlta(genero.getAlta());
            return ResponseEntity.ok().body(EntityModel.of(generoService.save(genero), linkTo(methodOn(GeneroController.class).findById(id, request)).withSelfRel()));

        }

    }

    @PatchMapping(path = "/{id}", consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody Map<String, Object> propiedades, HttpServletRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Genero searchedGenero = generoService.findById(id);
            Map<String, Object> searchedGeneroMap = mapper.convertValue(searchedGenero, Map.class);
            propiedades.forEach((k, v) -> {
                if (searchedGeneroMap.containsKey(k)) {
                    searchedGeneroMap.replace(k, searchedGeneroMap.get(k), v);
                }
            });
            searchedGenero = mapper.convertValue(searchedGeneroMap, Genero.class);
            return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(generoService.save(searchedGenero), linkTo(methodOn(GeneroController.class).findById(id, request)).withSelfRel()));
        } catch (ExceptionBBDD ebd) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(ebd.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok().body(generoService.softDelete(generoService.findById(id).getId()));

    }

}
