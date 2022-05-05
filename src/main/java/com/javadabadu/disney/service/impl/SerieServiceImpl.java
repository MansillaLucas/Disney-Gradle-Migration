package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.models.entity.Serie;
import com.javadabadu.disney.repository.SerieRepository;
import com.javadabadu.disney.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SerieServiceImpl implements SerieService {
    @Autowired
    SerieRepository serieRepository;
    @Autowired
    private MessageSource message;

    @Override
    public ResponseEntity<?> findById(Integer id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> save(Serie serie, HttpServletRequest resquest) {
        return null;
    }

    @Override
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Integer id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> softDelete(Serie serie, Integer id, HttpServletRequest request) {
        return null;
    }

}
