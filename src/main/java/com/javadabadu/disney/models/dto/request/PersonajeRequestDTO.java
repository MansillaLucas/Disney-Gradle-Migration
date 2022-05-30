package com.javadabadu.disney.models.dto.request;

import com.javadabadu.disney.models.entity.TipoPersonaje;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class PersonajeRequestDTO extends HashMap<String, Object> {
    private String nombre;
    private int edad;
    private String historia;
    private String imagen;
    private float peso;
    private TipoPersonaje tipo;

}
