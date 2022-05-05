package com.javadabadu.disney.service;

import com.javadabadu.disney.models.entity.Serie;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface SerieService {
    ResponseEntity<?> findById(Integer id, HttpServletRequest request);

    ResponseEntity<?> save(Serie serie, HttpServletRequest resquest);

    ResponseEntity<?> findAll(HttpServletRequest request);

    ResponseEntity<?> update(Integer id, HttpServletRequest request);

    ResponseEntity<?> softDelete(Serie serie, Integer id, HttpServletRequest request);
}
