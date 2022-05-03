package com.javadabadu.disney.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "personaje")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64)
    private String nombre;

    @Column(nullable = false)
    private int edad;

    @Column(nullable = false, length = 512)
    private String historia;

    @Column(nullable = false, length = 132)
    private String imagen;

    @Column(nullable = false)
    private float peso;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TipoPersonaje tipo;

    @Column(nullable = false)
    private boolean estado =true;

    @ManyToMany(mappedBy="personajes")
    private List<Pelicula> peliculas = new ArrayList<>();
}
