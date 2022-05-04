package com.javadabadu.disney.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;


@Entity
@PrimaryKeyJoinColumn(name = "serieId")
@Table(name = "serie")
@Getter
@Setter
public class Serie extends AudioVisual {

    @Column(nullable = false)
    private Byte temporadas;
    @Column(nullable = false)
    private Byte capitulos;

    public Serie(Integer id, String titulo, String imagen, Genero genero, List<Personaje> personajes, byte temporadas, byte capitulos) {
        super(id, titulo, imagen, genero, personajes);
        this.temporadas = temporadas;
        this.capitulos = capitulos;
    }

}
