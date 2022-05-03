package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class PeliculaServiceImpl implements PeliculaService{
    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    MessageSource message;

    @Override
    public List<Pelicula> findAll() {
        return peliculaRepository.findAll();
    }

    @Override
    public Pelicula findByID(Integer id) {
        try {
            peliculaRepository.findById(id).orElseThrow(()-> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
        } catch (ExceptionBBDD e) {
            e.printStackTrace();
        }
        return null;
    }
}
