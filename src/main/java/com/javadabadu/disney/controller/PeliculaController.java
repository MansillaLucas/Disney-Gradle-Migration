package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.entity.Personaje;
import com.javadabadu.disney.service.PeliculaService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        PeliculaResponseDTO peliculaResponseDTO = peliculaService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(peliculaResponseDTO, peliculaService.getSelfLink(id, request), peliculaService.getCollectionLink(request)));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<PeliculaResponseDTO> listPeliculaResponseDTO = peliculaService.findAll();
        List<EntityModel<PeliculaResponseDTO>> peliculas = new ArrayList<>();

        for (PeliculaResponseDTO pelicula : listPeliculaResponseDTO) {
            peliculas.add(EntityModel.of(pelicula, peliculaService.getSelfLink(pelicula.getId(), request)));
        }

        return ResponseEntity.ok().body(CollectionModel.of(peliculas, peliculaService.getCollectionLink(request)));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> lastId(HttpServletRequest request) throws ExceptionBBDD{
        return ResponseEntity.created(URI.create(request.getRequestURI() + peliculaService.lastValueId())).body(message.getMessage("new.register", null, Locale.US));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Pelicula pelicula, @PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        Pelicula source = peliculaService.getEntitySave(pelicula, id);
        return ResponseEntity.ok().body(EntityModel.of(peliculaService.save(source), peliculaService.getSelfLink(id, request), peliculaService.getCollectionLink(request)));
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        String body = peliculaService.softDelete(peliculaService.findById(id).getId());
        ResponseInfoDTO response = new ResponseInfoDTO(body, request.getRequestURI(), HttpStatus.OK.value());
        return ResponseEntity.ok().body(EntityModel.of(response, peliculaService.getCollectionLink(request)));
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody Map<String, Object> propiedades,
                                    HttpServletRequest request) throws ExceptionBBDD {

        Pelicula searchedPelicula = peliculaService.getEntity(id, propiedades);
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(peliculaService.save(searchedPelicula), peliculaService.getSelfLink(id, request)));
    }

}
