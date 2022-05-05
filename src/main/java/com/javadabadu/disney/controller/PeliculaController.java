package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.service.PeliculaService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = Uri.PELICULAS)
public class PeliculaController {
    @Autowired
    PeliculaService peliculaService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) {
        try {
            PeliculaResponseDTO peliculaResponseDTO = peliculaService.findById(id);
            return ResponseEntity.ok().body(peliculaResponseDTO);
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }


        @GetMapping("/")
        public ResponseEntity<?> findAll(HttpServletRequest request) {
            try {
                var respuesta = peliculaService.findAll();
                return ResponseEntity.ok().body(respuesta);
            } catch (ExceptionBBDD e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
            }
        }


}
