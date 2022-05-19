package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.request.GeneroRequestDTO;
import com.javadabadu.disney.models.dto.response.GeneroResponseDTO;
import com.javadabadu.disney.models.dto.response.ResponseInfoDTO;
import com.javadabadu.disney.service.GeneroService;
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
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value = Uri.GENEROS)
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        GeneroResponseDTO generoDTO = generoService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(generoDTO, generoService.getSelfLink(id, request), generoService.getCollectionLink(request)));

    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<GeneroResponseDTO> listGeneroResponseDTO = generoService.findAll();
        List<EntityModel<GeneroResponseDTO>> generosEntity = new ArrayList<>();
        for (GeneroResponseDTO genero : listGeneroResponseDTO) {
            generosEntity.add(EntityModel.of(genero, generoService.getSelfLink(genero.getId(), request)));
        }

        return ResponseEntity.ok().body(CollectionModel.of(generosEntity, generoService.getCollectionLink(request)));
    }

    @PostMapping("/")
    public ResponseEntity<?> lastId(HttpServletRequest request) throws ExceptionBBDD {
        return ResponseEntity.created(URI.create(request.getRequestURI() + generoService.lastValueId())).body("Se creó un registro");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody GeneroRequestDTO generoRequestDTO, @PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        GeneroResponseDTO generoDTO = generoService.getPersistenceEntity(generoRequestDTO, id);
        return ResponseEntity.ok().body(EntityModel.of(generoDTO, generoService.getSelfLink(id, request), generoService.getCollectionLink(request)));

    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Map<String, Object> propiedades, HttpServletRequest request) throws ExceptionBBDD {

        GeneroResponseDTO generoDTO = generoService.updatePartial(id, propiedades);
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(generoDTO, generoService.getSelfLink(id, request)));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        String body = generoService.softDelete(generoService.findById(id).getId());
        ResponseInfoDTO response = new ResponseInfoDTO(body, request.getRequestURI(), HttpStatus.OK.value());
        return ResponseEntity.ok().body(EntityModel.of(response, generoService.getCollectionLink(request)));
    }

}
