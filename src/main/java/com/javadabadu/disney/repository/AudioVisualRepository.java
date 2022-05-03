package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.AudioVisual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudioVisualRepository extends JpaRepository<AudioVisual, Integer> {

    @Query(value = "select * from audiovisual av where av.id=:id", nativeQuery = true)
    AudioVisual encontrarPorID(Integer id);
}
