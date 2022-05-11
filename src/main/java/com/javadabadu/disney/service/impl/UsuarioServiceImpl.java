package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByUsername(username);

        UserDetails userDet = new User(user.getUsername(), user.getPassword(), getAuthorities(user));
        return userDet;
    }

    public List<GrantedAuthority> getAuthorities(Usuario user) {

        List<GrantedAuthority> roles = new ArrayList<>();
        GrantedAuthority gA = (GrantedAuthority) user.getRol();
        roles.add(gA);
        return roles;

    }

}
