package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {

    @Query(value = "select * from  fn_last_value_id_personaje()", nativeQuery = true)
    Integer lastValueId();

    @Query(value = "select * from fn_soft_delete_personaje(:id) ",nativeQuery = true)
    boolean softDelete(Integer id);

    @Query(value = "SELECT p FROM Personaje p WHERE p.nombre LIKE %:nombre%")
    List<Personaje> findByNombre(@Param("nombre") String nombre);

    List<Personaje> findByEdad(@Param("edad") Integer edad);

    @Query(value = "SELECT p FROM Personaje p WHERE p.nombre LIKE %:nombre% AND p.edad = :edad")
    List<Personaje> findByEdadYNombre(@Param("nombre") String nombre, @Param("edad") Integer edad);

}
