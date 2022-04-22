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
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 128)
    private String titulo;

    @Column(nullable = false, length = 132)
    private String imagen;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Calendar fechaCreacion;

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private CalificacionPelicula calificacion;

    @ManyToOne
    @JoinColumn(name = "fk_genero")
    private Genero genero;

    @ManyToMany
    @JoinTable(name = "personajes_por_pelicula",
            joinColumns = {
                    @JoinColumn(name = "id_personaje")},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_pelicula")})
    private List<Personaje> personajes = new ArrayList<>();
}
