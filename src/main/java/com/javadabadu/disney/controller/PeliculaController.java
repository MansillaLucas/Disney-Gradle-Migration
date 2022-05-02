package com.javadabadu.disney.controller;

import com.javadabadu.disney.util.Uri;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = Uri.PELICULAS)
public class PeliculaController {

    @GetMapping("/")
    public ResponseEntity<?> findAll (HttpServletRequest request){
        return ResponseEntity.ok("Peliculas");
    }

}
