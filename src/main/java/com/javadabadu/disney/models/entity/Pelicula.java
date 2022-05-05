package com.javadabadu.disney.models.entity;

<<<<<<< HEAD
import lombok.Data;

import javax.persistence.*;
=======

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
>>>>>>> feature-find-audiovisual


@Entity
@PrimaryKeyJoinColumn(name = "peliculaId")
@Table(name = "pelicula")
@Getter
@Setter
@ToString
public class Pelicula extends AudioVisual {

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private CalificacionPelicula calificacion;

    @Column(nullable = false)
    private String duracion;

<<<<<<< HEAD
=======
    public Pelicula() {
    }

    public Pelicula(Integer id, String titulo, String imagen, Genero genero, List<Personaje> personajes, CalificacionPelicula calificacion, String duracion) {
        super(id, titulo, imagen, genero, personajes);
        this.calificacion = calificacion;
        this.duracion = duracion;
    }

    public int getCalificacion() {
        return calificacion.get();
    }
>>>>>>> feature-find-audiovisual
}
