package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.RolResponseDTO;
import com.javadabadu.disney.models.dto.UsuarioResponseDTO;
import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.service.UsuarioService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = Uri.AUTENTICACION)
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "/register")
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> crearUsuario(@RequestBody Usuario user, HttpServletRequest request) throws AuthenticationException, ExceptionBBDD {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.saveUser(user, request);

        return ResponseEntity.ok().body(EntityModel.of(usuarioResponseDTO, usuarioService.getSelfLink(usuarioResponseDTO.getId(), request), usuarioService.getCollectionLink(request)));
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<CollectionModel<EntityModel<UsuarioResponseDTO>>> findAll(HttpServletRequest request) throws ExceptionBBDD {

        List<UsuarioResponseDTO> usuarioResponseDTOList = usuarioService.findAll();
        List<EntityModel<UsuarioResponseDTO>> usuarios = new ArrayList<>();

        for (UsuarioResponseDTO usuario : usuarioResponseDTOList) {
            usuarios.add(EntityModel.of(usuario, usuarioService.getSelfLink(usuario.getId(), request)));
        }

        return ResponseEntity.ok().body(CollectionModel.of(usuarios, usuarioService.getCollectionLink(request)));
    }

    @GetMapping(value = "/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {

        UsuarioResponseDTO usuarioResponseDTO = usuarioService.findById(id);

        return ResponseEntity.ok().body(EntityModel.of(usuarioResponseDTO, usuarioService.getSelfLink(id, request), usuarioService.getCollectionLink(request)));
    }

    @PostMapping(value = "/roles/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EntityModel<RolResponseDTO>> crearRol(@RequestBody Rol rol, HttpServletRequest request) throws ExceptionBBDD {

        RolResponseDTO rolResponseDTO = usuarioService.saveRole(rol);

        return ResponseEntity.ok().body(EntityModel.of(rolResponseDTO, usuarioService.getSelfLink(rolResponseDTO.getId(), request), usuarioService.getCollectionLink(request)));

    }

    @GetMapping(value = "/token/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthenticationException {
        usuarioService.generateRefreshToken(request, response);
    }

}
