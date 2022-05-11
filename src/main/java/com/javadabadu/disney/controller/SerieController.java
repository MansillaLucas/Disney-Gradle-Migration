package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.Serie;
import com.javadabadu.disney.service.SerieService;
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
@RequestMapping(value = Uri.SERIES)
public class SerieController {

    @Autowired
    SerieService serieService;

    @GetMapping("/")
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<SerieResponseDTO> serieResponseDTOList = serieService.findAll();
        List<EntityModel<SerieResponseDTO>> entityModelList = new ArrayList<>();
        for (SerieResponseDTO serieResponseDTO1 : serieResponseDTOList) {
            entityModelList.add(EntityModel.of(serieResponseDTO1, serieService.getSelfLink(serieResponseDTO1.getId(), request)));
        }
        return ResponseEntity.ok().body(CollectionModel.of(entityModelList, serieService.getCollectionLink(request)));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        SerieResponseDTO serieDTO = serieService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(serieDTO, serieService.getSelfLink(id, request),
                serieService.getCollectionLink(request)));
    }

    @PostMapping("/")
    public ResponseEntity<?> lastId(HttpServletRequest request) {
        try {
            return ResponseEntity.created(URI.create(request.getRequestURI()
                    + serieService.lastValueId())).body("Se creo un registro");
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Serie serie, @PathVariable Integer id, HttpServletRequest request) {
        try {
            Serie source = serieService.getEntitySave(serie, id);
            return ResponseEntity.ok().body(EntityModel.of(serieService.save(source)
                    , serieService.getSelfLink(id, request)
                    , serieService.getCollectionLink(request)));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}