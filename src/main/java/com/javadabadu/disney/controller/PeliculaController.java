package com.javadabadu.disney.controller;

import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.service.PeliculaService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = Uri.PELICULAS)
public class PeliculaController {
    @Autowired
    PeliculaService peliculaService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) {
        return peliculaService.findById(id, request);
    }

    /*
        @GetMapping("/")
        public ResponseEntity<?> findAll(HttpServletRequest request) {
            return ResponseEntity.ok(peliculaService.findAll());
        }

     */
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Pelicula pelicula, HttpServletRequest request) {
        return peliculaService.save(pelicula, request);
    }


}
