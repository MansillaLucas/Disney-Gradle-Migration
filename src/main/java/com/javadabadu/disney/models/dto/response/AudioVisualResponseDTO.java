package com.javadabadu.disney.models.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.List;

@Getter
@Setter
public class AudioVisualResponseDTO {
    private Integer id;
    private String titulo;
    private String imagen;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar fechaCreacion;
    @JsonProperty(value = "genero")
    private String generoNombre;
    private boolean estado;
    @JsonProperty(value = "personajes")
    private List<PersonajeResponseCharacDTO> personajes;

    public void setPersonajes(List<PersonajeResponseCharacDTO> personajes) {
        this.personajes = personajes;
    }
}
