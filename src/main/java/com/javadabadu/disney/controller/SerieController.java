package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.service.SerieService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = Uri.SERIES)
public class SerieController {

    @Autowired
    SerieService serieService;

    @GetMapping("/")
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<EntityModel<SerieResponseDTO>> serieResponseDTO = serieService.findAll()
                .stream().map(sResponseDTO -> {
                            EntityModel<SerieResponseDTO> variable = null;
                            try {
                                variable = EntityModel.of(sResponseDTO,
                                        linkTo(methodOn(SerieController.class)
                                                .findById(sResponseDTO.getId(), request))
                                                .withSelfRel());
                            } catch (ExceptionBBDD e) {
                                e.getStackTrace();
                            }
                            return variable;
                        }
                ).collect(Collectors.toList());
        return ResponseEntity.ok().body(CollectionModel.of(serieResponseDTO, linkTo(methodOn(SerieController.class).findAll(request)).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        SerieResponseDTO serieDTO = serieService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(serieDTO,
                linkTo(methodOn(SerieController.class).findById(id, request)).withSelfRel(),
                linkTo(methodOn(SerieController.class).findAll(request)).withRel("Series")));

    }

}


