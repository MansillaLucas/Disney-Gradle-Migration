package com.javadabadu.disney.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;

@Getter
@Setter
public class PeliculaResponseDTO {
   private Integer id;
   private String titulo;
   private String imagen;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Calendar fechaCreacion;
   private String genero;
   private String duracion;
   private int calificacion;

   public void setGenero(String genero) {
      this.genero = genero;
   }
}
