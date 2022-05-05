package com.javadabadu.disney.service;

import com.javadabadu.disney.models.entity.Pelicula;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


public interface PeliculaService {

<<<<<<< HEAD

=======
   ResponseEntity<?> findById(Integer id, HttpServletRequest request);

   ResponseEntity<?> save(Pelicula pelicula, HttpServletRequest resquest);

   ResponseEntity<?> findAll(HttpServletRequest request);

   ResponseEntity<?> update(Pelicula pelicula, Integer id, HttpServletRequest request);

   ResponseEntity<?> softDelete(Integer id, HttpServletRequest request);
>>>>>>> feature-find-audiovisual

}
