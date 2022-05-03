package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Personaje;
import com.javadabadu.disney.repository.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    PersonajeRepository personajeRepository;

    @Override
    public Personaje save(Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    @Override
    public List<Personaje> findAll() throws ExceptionBBDD {
        try{
            return personajeRepository.findAll();
        }catch (Exception e){
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }
    }

    @Override
    public Personaje findById(Integer id) throws ExceptionBBDD {
        return personajeRepository.findById(id).orElseThrow(()->new ExceptionBBDD("Id no válido"));
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {

        if (personajeRepository.softDelete(id)) {
            return "Se elimino el genero seleccionado";
        } else {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }

    }

    @Override
    public Boolean existsById(Integer id) {
        return personajeRepository.existsById(id);
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        if (personajeRepository.lastValueId()>=1) {
            return personajeRepository.lastValueId() ;
        } else {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }

    }

    @Override
    public List<Personaje> filterCharacter(String name, Integer edad, Integer idMovie) {
/*        if(name!=null){
            return personajeRepository.findByNombre(name).orElseThrow();
        }
        if(edad!=null){
            return personajeRepository.findByEdad(edad).orElseThrow();
        }
        if(idMovie!=null){
            return
        }*/
        return Collections.emptyList();
    }
}
