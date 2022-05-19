package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.RolResponseDTO;
import com.javadabadu.disney.models.dto.UsuarioResponseDTO;
import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;
import org.springframework.hateoas.Link;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface UsuarioService extends BaseServiceRead<UsuarioResponseDTO, Integer> {

    UsuarioResponseDTO save(Usuario user) throws AuthenticationException, ExceptionBBDD;

    RolResponseDTO save(Rol role) throws ExceptionBBDD;

    Usuario getUser(String username);

    Integer getUserIdByUsername(String username);

    void generateRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthenticationException;

    Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD;

    Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD;

    UsuarioResponseDTO updatePartial(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD;

    String softDelete(Integer id) throws ExceptionBBDD;
}
