package com.javadabadu.disney.models.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeliculaResponseDTO extends AudioVisualResponseDTO {
   /*private Integer id;
   private String titulo;
   private String imagen;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Calendar fechaCreacion;
   @JsonProperty(value = "genero")
   private String generoNombre;

    */
   private String duracion;
   private int calificacion;
}