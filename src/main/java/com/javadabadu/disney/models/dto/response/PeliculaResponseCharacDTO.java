package com.javadabadu.disney.models.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javadabadu.disney.models.entity.Personaje;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
public class PeliculaResponseCharacDTO {
    private Integer id;
    private String titulo;
    private String imagen;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar fechaCreacion;
    @JsonProperty(value = "genero")
    private String generoNombre;
    private String duracion;
    private int calificacion;
    private List<PersonajeResponseCharacDTO> personajes;
}