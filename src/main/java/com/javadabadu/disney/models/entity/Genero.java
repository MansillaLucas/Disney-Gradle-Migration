package com.javadabadu.disney.models.entity;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "genero")
public class Genero extends RepresentationModel<Genero> {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;

     @Column(nullable = false, length = 32, unique = true)
     private String  nombre;

     @Column(nullable = false, length = 132)
     private String imagen;

     @Column(nullable = false )
     private Boolean alta ;

     @OneToMany(mappedBy = "genero")
     private List<Pelicula> peliculas = new ArrayList<>();
}
