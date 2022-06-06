package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.AudioVisual;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends AudioVisualRepository {
    @Query(value ="SELECT *, 0 as clazz_ FROM audiovisual av JOIN pelicula p ON av.id = p.pelicula_id WHERE av.titulo LIKE %:titulo%", nativeQuery = true)
    List<AudioVisual> findByTituloPelicula(String titulo);

    @Query(value = "SELECT *, 0 as clazz_ FROM audiovisual av JOIN pelicula pe ON av.id =pe.pelicula_id WHERE av.fk_genero= :idGenero ", nativeQuery = true)
    List<AudioVisual> findByGeneroIdPelicula(Integer idGenero);
}
