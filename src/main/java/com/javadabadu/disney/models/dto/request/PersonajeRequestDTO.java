package com.javadabadu.disney.models.dto.request;

import com.javadabadu.disney.models.entity.TipoPersonaje;

import java.util.HashMap;
import java.util.List;

public class PersonajeRequestDTO extends HashMap<String, Object> {

    private String nombre;
    private int edad;
    private String historia;
    private String imagen;
    private float peso;
    private TipoPersonaje tipo;

}
