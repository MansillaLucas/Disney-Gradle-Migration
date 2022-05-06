package com.javadabadu.disney.models.dto;

import com.javadabadu.disney.models.entity.TipoPersonaje;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonajeResponseDTO {
    private Integer id;
    private String nombre;
    private int edad;
    private String historia;
    private String imagen;
    private float peso;
    private TipoPersonaje tipo;
    private boolean estado;
}
