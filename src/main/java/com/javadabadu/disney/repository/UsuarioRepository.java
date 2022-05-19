package com.javadabadu.disney.repository;

import com.javadabadu.disney.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUsername(String username);

    @Query(value = "select * from fn_soft_delete_usuario(:id)", nativeQuery = true)
    boolean softDelete(Integer id);
}
