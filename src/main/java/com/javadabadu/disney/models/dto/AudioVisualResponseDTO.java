package com.javadabadu.disney.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Personaje;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
public class AudioVisualResponseDTO {
    private Integer id;

    private String titulo;

    private String imagen;

    private Calendar fechaCreacion;

    @JsonProperty(value="genero")
    private GeneroResponseDTO generoNombre;
}
