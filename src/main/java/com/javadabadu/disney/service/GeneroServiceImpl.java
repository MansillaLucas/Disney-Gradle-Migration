package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private MessageSource message;

    @Override
    @Transactional(readOnly = true)
    public Genero findById(Integer id) throws ExceptionBBDD {
        return generoRepository.findById(id).orElseThrow(()-> new ExceptionBBDD(message.getMessage("id.not.found", null, Locale.US)));
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
        return generoRepository.lastValueId();
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
    public String softDelete(Integer id) {
        if (generoRepository.softDelete(id)) {
            return "Se elimino el genero seleccionado";
        }
        return "Error en la transaccion contacte con su ADMI";

    }

}
