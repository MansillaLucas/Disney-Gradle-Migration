package com.javadabadu.disney.models.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SerieResponseDTO extends AudioVisualResponseDTO {
    @NonNull
    private Byte temporadas;
    @NonNull
    private Byte capitulos;
}
