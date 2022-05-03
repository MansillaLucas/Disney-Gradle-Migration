package com.javadabadu.disney.controller;

import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.service.IAudioVisual;
import com.javadabadu.disney.service.PeliculaService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = Uri.PELICULAS)
public class PeliculaController {
    @Autowired
    PeliculaService peliculaService;

    @Autowired
    IAudioVisual service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) {
        AudioVisual pelicula = service.findByID(id);
        return ResponseEntity.ok().body(EntityModel.of(pelicula, linkTo(methodOn(PeliculaController.class).findById(id, request)).withSelfRel(), linkTo(methodOn(PeliculaController.class).findAll(request)).withRel("Peliculas:")));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        return ResponseEntity.ok("Peliculas");
    }

}
