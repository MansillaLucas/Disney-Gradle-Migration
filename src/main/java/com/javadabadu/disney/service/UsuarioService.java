package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UsuarioService {
    Usuario saveUser(Usuario user, HttpServletRequest request) throws AuthenticationException;

    Rol saveRole(Rol role);

    void addRoleToUser(String username, String rolename);

    Usuario getUser(String username);

    List<Usuario> findAll();

    Usuario findById(Integer id) throws ExceptionBBDD;

    void generateRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthenticationException;
}
