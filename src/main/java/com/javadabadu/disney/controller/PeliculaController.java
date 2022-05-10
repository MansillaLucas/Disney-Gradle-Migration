package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.service.PeliculaService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = Uri.PELICULAS)
public class PeliculaController {

    @Autowired
    PeliculaService peliculaService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        PeliculaResponseDTO peliculaResponseDTO = peliculaService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(peliculaResponseDTO, peliculaService.getSelfLink(id, request), peliculaService.getCollectionLink(request)));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<PeliculaResponseDTO> listPeliculaResponseDTO = peliculaService.findAll();
        List<EntityModel<PeliculaResponseDTO>> peliculas = new ArrayList<>();

        for (PeliculaResponseDTO pelicula : listPeliculaResponseDTO) {
            peliculas.add(EntityModel.of(pelicula, peliculaService.getSelfLink(pelicula.getId(), request)));
        }

        return ResponseEntity.ok().body(CollectionModel.of(peliculas, peliculaService.getCollectionLink(request)));
    }

    @PostMapping("/")
    public ResponseEntity<?> lastId(HttpServletRequest request) {
        try {
            return ResponseEntity.created(URI.create(request.getRequestURI() + peliculaService.lastValueId())).body("se creo un registro");
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Pelicula pelicula, @PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {

        try {
            Pelicula source = peliculaService.getEntitySave(pelicula, id);
            return ResponseEntity.ok().body(EntityModel.of(peliculaService.save(source), peliculaService.getSelfLink(id, request), peliculaService.getCollectionLink(request)));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
        }
    }



}
