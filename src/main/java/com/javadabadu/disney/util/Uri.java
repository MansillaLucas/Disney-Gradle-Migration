package com.javadabadu.disney.util;

public class Uri {

    private Uri() {
        throw new IllegalStateException("Utility class (Uri) no puede ser instanciada");
    }

    public static final String PATH_BASE = "/api/v1";
    public static final String GENEROS = PATH_BASE + "/generos";
    public static final String PELICULAS = PATH_BASE + "/movies";
    public static final String PERSONAJES = PATH_BASE + "/characters";
    public static final String SERIES = PATH_BASE + "/series";
    public final static String AUTENTICACION = PATH_BASE + "/auth";
}
