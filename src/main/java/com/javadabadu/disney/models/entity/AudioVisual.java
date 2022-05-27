package com.javadabadu.disney.models.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Data

@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "audiovisual")
@Getter
@Setter
public class AudioVisual {
    @Id
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

    //@Column(nullable = false)
    //private boolean estado = true;

    @ManyToMany
    @JoinTable(name = "personajes_por_audiovisual",
            joinColumns = {
                    @JoinColumn(name = "id_audiovisual")},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_personaje")})
    private List<Personaje> personajes = new ArrayList<>();

    public AudioVisual() {
    }

    public void setPersonajes(List<Personaje> personajes) {
        if (this.personajes.size() == 0) {
            this.personajes = personajes;
        } else {
            for (Personaje personaje : personajes) {
                if (this.personajes.contains(personaje)) {

                } else {

                }

            }
        }
    }

    public AudioVisual(Integer id, String titulo, String imagen, Genero genero, List<Personaje> personajes) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
        this.genero = genero;
        this.personajes = personajes;
    }
}
