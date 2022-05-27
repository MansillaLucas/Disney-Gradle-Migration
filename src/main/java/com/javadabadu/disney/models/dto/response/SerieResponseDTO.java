package com.javadabadu.disney.models.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SerieResponseDTO extends AudioVisualResponseDTO {
    /* @NonNull
     private Integer id;
     @NonNull
     private String titulo;
     @NonNull
     private String imagen;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     @NonNull
     private Calendar fechaCreacion;
     @NonNull
     @JsonProperty(value = "genero")
     private String generoNombre;
     */
    @NonNull
    private Byte temporadas;
    @NonNull
    private Byte capitulos;


}
