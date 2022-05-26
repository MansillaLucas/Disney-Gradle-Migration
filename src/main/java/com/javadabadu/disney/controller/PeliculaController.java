package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.request.PeliculaRequestDTO;
import com.javadabadu.disney.models.dto.response.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.response.ResponseInfoDTO;
import com.javadabadu.disney.service.PeliculaService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping(value = Uri.PELICULAS)
public class PeliculaController {

    @Autowired
    PeliculaService peliculaService;

    @Autowired
    MessageSource message;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PeliculaResponseDTO>> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        PeliculaResponseDTO peliculaResponseDTO = peliculaService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(peliculaResponseDTO, peliculaService.getSelfLink(id, request), peliculaService.getCollectionLink(request)));
    }

    @GetMapping("/")
    public ResponseEntity<CollectionModel<EntityModel<PeliculaResponseDTO>>> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<PeliculaResponseDTO> listPeliculaResponseDTO = peliculaService.findAll();
        List<EntityModel<PeliculaResponseDTO>> peliculas = new ArrayList<>();

        for (PeliculaResponseDTO pelicula : listPeliculaResponseDTO) {
            peliculas.add(EntityModel.of(pelicula, peliculaService.getSelfLink(pelicula.getId(), request)));
        }

        return ResponseEntity.ok().body(CollectionModel.of(peliculas, peliculaService.getCollectionLink(request)));
    }

    @PostMapping("/")
    public ResponseEntity<String> lastId(HttpServletRequest request) throws ExceptionBBDD{
        return ResponseEntity.created(URI.create(request.getRequestURI() + peliculaService.lastValueId())).body(message.getMessage("new.register", null, Locale.US));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PeliculaResponseDTO>> crear(@RequestBody PeliculaRequestDTO pelicula, @PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        return ResponseEntity.ok().body(EntityModel.of(peliculaService.getPersistenceEntity(pelicula, id), peliculaService.getSelfLink(id, request), peliculaService.getCollectionLink(request)));
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ResponseInfoDTO>> delete(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        String body = peliculaService.softDelete(peliculaService.findById(id).getId());
        ResponseInfoDTO response = new ResponseInfoDTO(body, request.getRequestURI(), HttpStatus.OK.value());
        return ResponseEntity.ok().body(EntityModel.of(response, peliculaService.getCollectionLink(request)));
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PeliculaResponseDTO>> update(@PathVariable Integer id, @RequestBody Map<String, Object> propiedades, HttpServletRequest request) throws ExceptionBBDD {
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(peliculaService.updatePartial(id, propiedades), peliculaService.getSelfLink(id, request)));
    }

    @PatchMapping(path = "/join/{id}")
    public ResponseEntity<EntityModel<PeliculaResponseDTO>> joinPersonajes(@PathVariable Integer id, @RequestBody List<Integer> idPersonajes, HttpServletRequest request) throws ExceptionBBDD {
        PeliculaResponseDTO response = peliculaService.joinPersonajes(id, idPersonajes);
        return ResponseEntity.ok().body(EntityModel.of(response, peliculaService.getCollectionLink(request)));
    }

    @PatchMapping(path = "/remove/{id}")
    public ResponseEntity<EntityModel<PeliculaResponseDTO>> removePersonaje(@PathVariable Integer id, @RequestBody Integer idPersonaje, HttpServletRequest request) throws ExceptionBBDD {
        PeliculaResponseDTO response = peliculaService.removePersonaje(id, idPersonaje);
        return ResponseEntity.ok().body(EntityModel.of(response, peliculaService.getCollectionLink(request)));
    }

}
