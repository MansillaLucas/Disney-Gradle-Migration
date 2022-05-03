package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    @Query(value = "select * from fn_last_value_id()", nativeQuery = true)
    Integer lastValueId();

    @Query(value = "select * from fn_soft_delete_genero(:id)",nativeQuery = true)
    boolean softDelete(Integer id);
    @Query(value = "select * from fn_busqueda5_id(:id)",nativeQuery = true)
    Genero busquedaPorId(Integer id);

    @Query(value = "select * from fn_update_genero(:id, :nombre ,:imagen)",nativeQuery = true)
    String update(Integer id,String nombre, String imagen);

    @Query(value = "select * from fn_create_genero(:id,:nombre ,:imagen)",nativeQuery = true)
    String create(Integer id,String nombre, String imagen);
}
