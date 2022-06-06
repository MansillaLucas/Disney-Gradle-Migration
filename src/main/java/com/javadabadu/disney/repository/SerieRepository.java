package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.AudioVisual;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerieRepository extends AudioVisualRepository{
    @Query(value ="SELECT *, 0 as clazz_ FROM audiovisual av JOIN serie s ON av.id = s.serie_id WHERE av.titulo LIKE %:titulo%", nativeQuery = true)
    List<AudioVisual> findByTituloSerie(String titulo);

    @Query(value = "SELECT *, 0 as clazz_ FROM audiovisual av " +
            "JOIN serie s ON av.id =s.serie_id WHERE av.fk_genero= :idGenero ", nativeQuery = true)
    List<AudioVisual> findByGeneroIdSerie(Integer idGenero);

}
