package com.javadabadu.disney.models.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "peliculaId")
@Table(name = "pelicula")
public class Pelicula extends AudioVisual{

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private CalificacionPelicula calificacion;

    @Column(nullable = false)
    private String duracion;


}
