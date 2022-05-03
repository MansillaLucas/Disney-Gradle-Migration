package com.javadabadu.disney.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "serieId")
@Table(name = "serie")
public class Serie extends AudioVisual{

    @Column(nullable = false)
    private Byte temporadas;
    @Column(nullable = false)
    private Byte capitulos;

}
