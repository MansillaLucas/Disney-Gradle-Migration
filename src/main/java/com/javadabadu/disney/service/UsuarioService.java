package com.javadabadu.disney.service;

import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario saveUser(Usuario user);

    Rol saveRole(Rol role);

    void addRoleToUser(String username, String rolename);

    Usuario getUser(String username);

    List<Usuario> findAll();
}
