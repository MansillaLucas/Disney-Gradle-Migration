package com.javadabadu.disney.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadabadu.disney.controller.UsuarioController;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.RolResponseDTO;
import com.javadabadu.disney.models.dto.UsuarioResponseDTO;
import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.RolRepository;
import com.javadabadu.disney.repository.UsuarioRepository;
import com.javadabadu.disney.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapperDTOImp mapperDTO;

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

    public UsuarioResponseDTO save(Usuario user) throws AuthenticationException, ExceptionBBDD {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if (request.getHeader(AUTHORIZATION) == null) {
            if (user.getRol().getId() == 1) {
                throw new AuthenticationException("No tienes permisos para crear un ADMIN");
            }
        } else {
            String rol = cleanRolString(getUserRol(request));
            if (!rol.equals("ROLE_ADMIN")) {
                throw new AuthenticationException("No tienes permisos para crear usuarios");
            }
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return mapperDTO.usuarioToUsuarioDTO(usuarioRepository.save(user));
        } catch (Exception ebd) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }

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
    public Usuario getUser(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<UsuarioResponseDTO> findAll() throws ExceptionBBDD {

        try {
            return mapperDTO.listUsuarioToUsuarioDTO(usuarioRepository.findAll());
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transacción, contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UsuarioResponseDTO findById(Integer id) throws ExceptionBBDD {
        Usuario searchedUsuario = usuarioRepository.findById(id).orElseThrow(() -> new ExceptionBBDD("Id no válido", HttpStatus.NOT_FOUND));

        return mapperDTO.usuarioToUsuarioDTO(searchedUsuario);
    }

    @Override
    public Boolean existsById(Integer id) throws ExceptionBBDD {
        return null;
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        return null;
    }

    @Override
    public UsuarioResponseDTO updatePartial(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {

        ObjectMapper mapper = new ObjectMapper();

        try {
            UsuarioResponseDTO usuarioResponseDTO = findById(id);

            Map<String, Object> searchedUsuarioMap = mapper.convertValue(usuarioResponseDTO, Map.class);

            propiedades.forEach((k, v) -> {
                if (searchedUsuarioMap.containsKey(k) && !k.equals("username")) {
                    String newPassword = passwordEncoder.encode((String) v);
                    searchedUsuarioMap.replace(k, searchedUsuarioMap.get(k), newPassword);
                }
            });

            Usuario searchedUsuario2 = mapper.convertValue(searchedUsuarioMap, Usuario.class);

            return mapperDTO.usuarioToUsuarioDTO(usuarioRepository.save(searchedUsuario2));
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    public Integer getUserIdByUsername(String username) {
        Usuario user = usuarioRepository.findByUsername(username);
        return user.getId();
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {
        try {
            if (usuarioRepository.softDelete(id)) {
                return "Se elimino el usuario seleccionado";
            } else {
                throw new ExceptionBBDD("Id no encontrado", HttpStatus.NOT_FOUND);
            }

        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public RolResponseDTO save(Rol role) throws ExceptionBBDD {
        try {
            return mapperDTO.rolToRolDTO(rolRepository.save(role));
        } catch (Exception ebd) {
            throw new ExceptionBBDD("Error en la transacción contacte con su ADM", HttpStatus.BAD_REQUEST);
        }

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

    public Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(UsuarioController.class).findAll(request)).withRel("Usuarios:");
        } catch (ExceptionBBDD ebd2) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }

    }

    public Link getSelfLink(Integer id, HttpServletRequest request) throws ExceptionBBDD {
        try {
            return linkTo(methodOn(UsuarioController.class).findById(id, request)).withSelfRel();
        } catch (ExceptionBBDD ebd) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM", HttpStatus.BAD_REQUEST);
        }
    }

    private String cleanRolString(String rol) {
        rol = rol.replaceAll("\"", "");
        rol = rol.replace("]", "");
        rol = rol.replace("[", "");

        return rol;
    }

}
