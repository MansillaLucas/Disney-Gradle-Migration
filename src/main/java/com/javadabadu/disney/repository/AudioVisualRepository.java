package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.AudioVisual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AudioVisualRepository extends JpaRepository<AudioVisual, Integer> {
    @Query(value = "select * from fn_last_value_idaudiovisual()", nativeQuery = true)
    Integer lastValueId();

    @Query(value = "select * from fn_soft_delete_audiovisual(:id) ", nativeQuery = true)
    boolean softDelete(Integer id);

    @Query(value ="SELECT *, 0 as clazz_ FROM audiovisual av JOIN serie s ON av.id = s.serie_id WHERE av.titulo LIKE %:titulo%", nativeQuery = true)
    List<AudioVisual> findByTituloSerie(String titulo);

    @Query(value = "SELECT *, 0 as clazz_ FROM audiovisual av " +
            "JOIN serie s ON av.id =s.serie_id WHERE av.fk_genero= :idGenero ", nativeQuery = true)
    List<AudioVisual> findByGeneroIdSerie(Integer idGenero);

}
