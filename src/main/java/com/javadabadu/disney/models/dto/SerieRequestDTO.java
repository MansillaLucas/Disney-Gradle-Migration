package com.javadabadu.disney.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerieRequestDTO extends AudioVisualRequestDTO{

    private Byte temporadas;
    private Byte capitulos;

}
