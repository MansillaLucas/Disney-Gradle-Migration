package com.javadabadu.disney.service;

import com.javadabadu.disney.models.entity.Pelicula;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


public interface PeliculaService {

   ResponseEntity<?> findById(Integer id, HttpServletRequest request);

   ResponseEntity<?> save(Pelicula pelicula, HttpServletRequest resquest);


}
