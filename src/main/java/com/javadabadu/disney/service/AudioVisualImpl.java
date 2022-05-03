package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.repository.AudioVisualRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class AudioVisualImpl implements IAudioVisual{
    @Autowired
    AudioVisualRepository repository;

    @Autowired
    MessageSource message;

    @Override
    public List<AudioVisual> findAll() {
        return null;
    }

    @Override
    public AudioVisual findByID(Integer id) {
        var audioVisual = repository.encontrarPorID(id);




        try {
            repository.findById(id).orElseThrow(()-> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
        } catch (ExceptionBBDD e) {
            e.printStackTrace();
        }
        return null;
    }
}
