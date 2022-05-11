package com.javadabadu.disney.models.impl;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.models.entity.Personaje;
import com.javadabadu.disney.repository.GeneroRepository;
import com.javadabadu.disney.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private MessageSource message;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Genero findById(Integer id) throws ExceptionBBDD {
        return generoRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genero> findAll() throws ExceptionBBDD{
        try {
            return generoRepository.findAll();
        } catch (Exception e) {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }
    }

    @Override
    @Transactional
    public Genero save(Genero genero, Integer id) throws ExceptionBBDD {
        responseBBDD(generoRepository.create(id, genero.getNombre(), genero.getImagen()), id);
        return findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer lastValueId()throws ExceptionBBDD {
        if (generoRepository.lastValueId() >= 1) {
            return generoRepository.lastValueId();
        } else {
            throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
        }    }

    @Override
    @Transactional
    public Genero update(Genero genero) throws ExceptionBBDD {
        String response = generoRepository.update(genero.getId(), genero.getNombre(), genero.getImagen());
        responseBBDD(response, genero.getId());
        return genero;
    }

    @Override
    @Transactional
    public String softDelete(Integer id) {
        return generoRepository.softDelete(id) ? "Se elimino el genero seleccionado" : "Error en la transaccion contacte con su ADMI";
    }

    @Override
    public Boolean existsById(Integer id) {
        return generoRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public void responseBBDD(String response, Integer id) throws ExceptionBBDD {
        responseBBDDOk(response);

    }

    private void responseBBDDOk(String response) throws ExceptionBBDD {
        if (!response.contains("Se creo correctamente") && !response.contains("Se modifico correctamente")) {
            throw new ExceptionBBDD(response);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Genero getGenero(Genero genero, Integer id) throws ExceptionBBDD {
        Genero source = null;
        if (existsById(id)) {
            source = findById(id);
            genero.setId(id);
            source = genero;
            return source;
        } else {
            return genero  ;
        }
    }
}
