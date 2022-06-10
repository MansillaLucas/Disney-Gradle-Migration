package com.javadabadu.disney.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false)
    private boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "fk_rol", nullable = false)
    private Rol rol;

}
