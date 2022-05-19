package com.javadabadu.disney.models.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SerieResponseDTO {
    @NonNull
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
    @NonNull
    private Byte temporadas;
    @NonNull
    private Byte capitulos;
    private boolean estado =true;


}
