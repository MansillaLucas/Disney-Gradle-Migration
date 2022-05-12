package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.repository.RolRepository;
import com.javadabadu.disney.repository.UsuarioRepository;
import com.javadabadu.disney.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

        if (searchedUsuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado en la base de datos");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(searchedUsuario.getRol().getDescripcion()));

        return new org.springframework.security.core.userdetails.User(searchedUsuario.getUsername(),
                searchedUsuario.getPassword(),
                authorities);
    }

    @Override
    public Usuario saveUser(Usuario user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usuarioRepository.save(user);
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

    @Override
    public Usuario getUser(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }


}
