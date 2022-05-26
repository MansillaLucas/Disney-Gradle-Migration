package com.javadabadu.disney.models.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeliculaResponseDTO extends AudioVisualResponseDTO {

   private String duracion;
   private int calificacion;
}