package com.javadabadu.disney.controller;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class MainController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> mainMethod(HttpServletRequest request) {

        Link selfLink = linkTo(methodOn(MainController.class).mainMethod(request)).withSelfRel(),
                generoLink = linkTo(methodOn(GeneroController.class).findAll(request)).withRel("Genero:"),
                characterLink = linkTo(methodOn(PersonajeController.class).findAll(request)).withRel("Character:"), //Cambiar
                movieLink = linkTo(methodOn(PeliculaController.class).findAll(request)).withRel("Movie:"); //Cambiar


        return ResponseEntity.ok(EntityModel.of(selfLink, generoLink, characterLink, movieLink));
    }
}
