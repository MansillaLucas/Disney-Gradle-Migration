package com.javadabadu.disney.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudioVisualRequestDTO {
    @JsonProperty(value = "genero")
    private GeneroRequestDTO genero;
    private String titulo;
    private String imagen;
}
