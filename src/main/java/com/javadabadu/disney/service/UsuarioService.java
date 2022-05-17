package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.RolResponseDTO;
import com.javadabadu.disney.models.dto.UsuarioResponseDTO;
import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO saveUser(Usuario user, HttpServletRequest request) throws AuthenticationException, ExceptionBBDD;

    RolResponseDTO saveRole(Rol role) throws ExceptionBBDD;

    Usuario getUser(String username);

    List<UsuarioResponseDTO> findAll() throws ExceptionBBDD;

    UsuarioResponseDTO findById(Integer id) throws ExceptionBBDD;

    void generateRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthenticationException;
}
