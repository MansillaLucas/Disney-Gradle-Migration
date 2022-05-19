package com.javadabadu.disney.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class GeneroRequestDTO {

    private String nombre;

    private String imagen;
}
