package com.javadabadu.disney.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "audiovisual")
@Getter
@Setter

public class AudioVisual {
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
    private Calendar fechaCreacion = Calendar.getInstance();

    @ManyToOne
    @JoinColumn(name = "fk_genero" ,nullable = false)
    private Genero genero;


    @ManyToMany
    @JoinTable(name = "personajes_por_audiovisual",
            joinColumns = {
                    @JoinColumn(name = "id_audiovisual")},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_personaje")})
    private List<Personaje> personajes = new ArrayList<>();

    public AudioVisual() {
    }

    public AudioVisual(Integer id, String titulo, String imagen, Genero genero, List<Personaje> personajes) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
        this.genero = genero;
        this.personajes = personajes;
    }
}
