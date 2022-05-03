package com.javadabadu.disney.models.entity;

import lombok.Data;

import javax.persistence.*;

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
