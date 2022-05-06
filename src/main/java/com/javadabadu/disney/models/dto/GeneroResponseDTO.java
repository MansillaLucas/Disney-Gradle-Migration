package com.javadabadu.disney.models.dto;

import com.javadabadu.disney.models.entity.AudioVisual;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GeneroResponseDTO {

    private String nombre;

    private String imagen;

    private Boolean alta;

    private List<AudioVisualResponseDTO> audioVisuals;
}
