package com.javadabadu.disney.controller;


import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.RolResponseDTO;
import com.javadabadu.disney.models.dto.UsuarioResponseDTO;
import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.service.UsuarioService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = Uri.AUTENTICACION)
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "/register")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody Usuario user, HttpServletRequest request) throws AuthenticationException, ExceptionBBDD {
        return ResponseEntity.ok().body(usuarioService.saveUser(user, request));
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() throws ExceptionBBDD {
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @GetMapping(value = "/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Integer id) throws ExceptionBBDD {
        return ResponseEntity.ok().body(usuarioService.findById(id));
    }


    @PostMapping(value = "/roles/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RolResponseDTO> crearRol(@RequestBody Rol rol) throws ExceptionBBDD {
        return ResponseEntity.ok().body(usuarioService.saveRole(rol));
    }

    @GetMapping(value = "/token/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthenticationException {
        usuarioService.generateRefreshToken(request, response);
    }


}
