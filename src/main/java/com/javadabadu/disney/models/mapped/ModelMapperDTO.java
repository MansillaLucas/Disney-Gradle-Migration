package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.entity.Serie;

public interface ModelMapperDTO {
    PeliculaResponseDTO peliculaToResponseDTO(Pelicula pelicula);
    SerieResponseDTO serieToResponseDTO(Serie serie);
}
