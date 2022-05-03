package com.javadabadu.disney.service;

import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Pelicula;

import java.util.List;

public interface IAudioVisual {
    List<AudioVisual> findAll();
    AudioVisual findByID(Integer id);
}
