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

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private MessageSource message;

    @Override
    @Transactional(readOnly = true)
    public Genero findById(Integer id) throws ExceptionBBDD {
        return generoRepository.findById(id).orElseThrow(()-> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    @Override
    @Transactional
    public Genero save(Genero genero , Integer id) throws ExceptionBBDD {
        return responseBBDD(generoRepository.create(id, genero.getNombre(),genero.getImagen()),id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer lastValueId() {
        return generoRepository.lastValueId();
    }

    @Override
    @Transactional
    public Genero update(Genero genero) throws ExceptionBBDD {
        String response =generoRepository.update(genero.getId(), genero.getNombre(),genero.getImagen());
        return responseBBDD(response, genero.getId());

    }

    @Override
    @Transactional
    public String softDelete(Integer id) {
        if (generoRepository.softDelete(id)) {
            return "Se elimino el genero seleccionado";
        }
        return "Error en la transaccion contacte con su ADMI";

    }

    @Override
    public Boolean existsById(Integer id) {
        return generoRepository.existsById(id);
    }

    @Override
    public Genero responseBBDD(String response, Integer id) throws ExceptionBBDD{
        responseBBDDOk(response);
        System.out.println(findById(id));
        System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<REPOSITORI!!!!!!!!!!!! ");
        System.out.println(generoRepository.findById(id).get());
        return generoRepository.findById(id).get();
    }
    private void responseBBDDOk(String response) throws ExceptionBBDD {
        if (!response.contains("Se creo correctamente") && !response.contains("Se modifico correctamente")) {
            throw new ExceptionBBDD(response);
        }
    }
}
