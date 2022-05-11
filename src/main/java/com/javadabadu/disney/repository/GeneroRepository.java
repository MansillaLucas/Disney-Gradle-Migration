package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

    @Query(value = "select * from fn_last_value_id()", nativeQuery = true)
    Integer lastValueId();

    @Query(value = "select * from fn_soft_delete_genero(:id)", nativeQuery = true)
    boolean softDelete(Integer id);
}
