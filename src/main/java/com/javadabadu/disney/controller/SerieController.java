package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.repository.SerieRepository;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = Uri.SERIES)
public class SerieController {

    @Autowired
    SerieRepository serieRepository;

    @GetMapping("/")
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ExceptionBBDD {
        return ResponseEntity.ok().body(serieRepository.findAll());
    }

}
