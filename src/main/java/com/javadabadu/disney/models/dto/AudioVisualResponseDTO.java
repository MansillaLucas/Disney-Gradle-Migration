package com.javadabadu.disney.models.dto;

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
    private String titulo;

    private String imagen;

    private Calendar fechaCreacion;

    private String generoNombre;
}
