package com.javadabadu.disney.models.dto.request;

import lombok.Data;

@Data
public class SerieRequestDTO extends AudioVisualRequestDTO {
    private Byte temporadas;
    private Byte capitulos;

}
