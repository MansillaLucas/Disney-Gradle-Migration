package com.javadabadu.disney.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.service.UsuarioService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = Uri.AUTENTICACION)
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping(value = "/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @PostMapping(value = "/user/save")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario user) {
        return ResponseEntity.ok().body(usuarioService.saveUser(user));
    }

    @PostMapping(value = "/role/save")
    public ResponseEntity<?> crearRol(@RequestBody Rol rol) {
        return ResponseEntity.ok().body(usuarioService.saveRole(rol));
    }

    @PostMapping(value = "/role/add_to_user")
    public ResponseEntity<?> addRoleToUser(@RequestBody String username, String roleDescription) {
        usuarioService.addRoleToUser(username, roleDescription);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("${SECRET_WORD}".getBytes());

                JWTVerifier verifier = JWT.require(algorithm).build();

                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String username = decodedJWT.getSubject();
                Usuario usuario = usuarioService.getUser(username);
                String rol = decodedJWT.getClaim("role").toString();


                String accessToken = JWT.create()
                        .withSubject(usuario.getUsername()) //se debe poner algo unico de la entidad (falta hacer unico el username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) //Asi dura 10 min el token
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", (Map<String, ?>) usuario.getRol())
                        .sign(algorithm);


                Map<String, String> tokens = new HashMap<>();

                tokens.put("acces_token", accessToken);
                tokens.put("refresh_token", refreshToken);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.sendError(HttpStatus.FORBIDDEN.value());
                response.setHeader("error", e.getMessage());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("No se encuenta el Refresh Token");
        }
    }
}
