package com.javadabadu.disney.models.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
public class AudioVisualResponseDTO {
    private Integer id;

    private String titulo;

    private String imagen;

    private Calendar fechaCreacion;

    @JsonProperty(value="genero")
    private GeneroResponseDTO genero;
}
