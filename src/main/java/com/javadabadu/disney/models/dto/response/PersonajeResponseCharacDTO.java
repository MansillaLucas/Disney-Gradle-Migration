package com.javadabadu.disney.models.dto.response;

import com.javadabadu.disney.models.entity.TipoPersonaje;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonajeResponseCharacDTO  {
    private Integer id;
    private String nombre;
    private int edad;
    private String historia;
    private String imagen;
    private float peso;
    private TipoPersonaje tipo;
    private boolean estado;

    public boolean getEstado (){
        return this.estado;
    }
}
