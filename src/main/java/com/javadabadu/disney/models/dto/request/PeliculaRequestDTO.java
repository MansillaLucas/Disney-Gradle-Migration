package com.javadabadu.disney.models.dto.request;

import com.javadabadu.disney.models.entity.CalificacionPelicula;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeliculaRequestDTO extends AudioVisualRequestDTO {
    private String duracion;
    private CalificacionPelicula calificacion;
}
