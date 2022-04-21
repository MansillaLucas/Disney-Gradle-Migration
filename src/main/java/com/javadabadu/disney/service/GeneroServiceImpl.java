package com.javadabadu.disney.service;

import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImpl implements GeneroService{

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Genero> findById(Integer id) {
        return generoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    @Override
    @Transactional
    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer lastValueId() {
        return lastValueId();
    }

    @Override
    @Transactional
    public Genero update(Genero genero, Integer id) {
        Genero generoEncontrado = generoRepository.findById(id).orElseThrow();
        generoEncontrado.setImagen(genero.getImagen());
        generoEncontrado.setNombre(genero.getNombre());

        return save(generoEncontrado);
    }

    @Override
    @Transactional
    public Boolean softDelete(Integer id) {
        return generoRepository.changeStatus(id);
    }
}
