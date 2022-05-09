package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.AudioVisual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AudioVisualRepository extends JpaRepository<AudioVisual, Integer> {
    @Query(value = "select * from fn_last_value_idaudiovisual()", nativeQuery = true)
    Integer lastValueId();
}
