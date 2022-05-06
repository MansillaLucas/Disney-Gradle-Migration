package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.repository.SerieRepository;
import com.javadabadu.disney.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieServiceImpl implements SerieService {
    @Autowired
    SerieRepository serieRepository;
    @Autowired
    private MessageSource message;

    //TODO implementar metodo
    @Override
    public List<SerieResponseDTO> findAll() throws ExceptionBBDD {
        return null;
    }

    //TODO implementar metodo
    @Override
    public SerieResponseDTO findById(Integer id) throws ExceptionBBDD {
        return null;
    }

    //TODO implementar metodo
    @Override
    public Boolean existsById(Integer id) {
        return null;
    }

    //TODO implementar metodo
    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        return null;
    }

    //TODO restan metodos de guardar y actualizar (agregar tambien in interfaz correspondiente)
}
