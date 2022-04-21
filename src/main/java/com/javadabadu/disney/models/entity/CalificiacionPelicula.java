package com.javadabadu.disney.models.entity;

public enum CalificiacionPelicula {
    UNO(1),
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5);

    private int puntaje;

    private CalificiacionPelicula(int puntaje){
        this.puntaje = puntaje;
    }

    public int get() {
        return puntaje;
    }
}
