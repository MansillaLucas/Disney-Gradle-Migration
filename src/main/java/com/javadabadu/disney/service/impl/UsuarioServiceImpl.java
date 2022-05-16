package com.javadabadu.disney.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.repository.RolRepository;
import com.javadabadu.disney.repository.UsuarioRepository;
import com.javadabadu.disney.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario searchedUsuario = usuarioRepository.findByUsername(username);

        if (searchedUsuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado en la base de datos");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(searchedUsuario.getRol().getDescripcion()));

        return new org.springframework.security.core.userdetails.User(searchedUsuario.getUsername(),
                searchedUsuario.getPassword(),
                authorities);
    }

    public Usuario saveUser(Usuario user, HttpServletRequest request) throws AuthenticationException {

        if (request.getHeader(AUTHORIZATION) == null) {
            if (user.getRol().getId() == 1) {
                throw new AuthenticationException("No tienes permisos para crear un ADMIN");
            }
        } else {
            String rol = cleanRolString(getUserRol(request));
            if (rol.equals("ROLE_USER")) {
                throw new AuthenticationException("No tienes permisos para crear usuarios");
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usuarioRepository.save(user);
    }


    private String getUserRol(HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256("${SECRET_WORD}".getBytes());

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer ".length());

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getClaim("role").toString();
    }


    @Override
    public Rol saveRole(Rol role) {
        return rolRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String description) {
        Usuario searchedUsuario = getUser(username);
        Rol searchedRole = rolRepository.findByDescripcion(description);

        searchedUsuario.setRol(searchedRole);

    }

    public void generateRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthenticationException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("${SECRET_WORD}".getBytes());

                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String username = decodedJWT.getSubject();
                Usuario usuario = getUser(username);

                String accessToken = JWT.create()
                        .withSubject(usuario.getUsername()) //se debe poner algo unico de la entidad (falta hacer unico el username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) //Asi dura 10 min el token
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", usuario.getRol().getDescripcion())
                        .sign(algorithm);

                response.setHeader("acces_token", accessToken);
                response.setHeader("refresh_token", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            } catch (Exception e) {
                response.sendError(HttpStatus.FORBIDDEN.value());
                response.setHeader("error", e.getMessage());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new AuthenticationException("No se encuenta el Refresh Token");
        }
    }

    private String cleanRolString(String rol) {
        rol = rol.replaceAll("\"", "");
        rol = rol.replace("]", "");
        rol = rol.replace("[", "");

        return rol;
    }


    @Override
    public Usuario getUser(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }


}
