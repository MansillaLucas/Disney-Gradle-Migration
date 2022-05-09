package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.Serie;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.SerieRepository;
import com.javadabadu.disney.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SerieServiceImpl implements SerieService {
    @Autowired
   SerieRepository serieRepository;
    @Autowired
    private MessageSource message;
    @Autowired
    private ModelMapperDTOImp mm;

       @Override
    public List<SerieResponseDTO> findAll() throws ExceptionBBDD {
        List<SerieResponseDTO> serieResponseDTO = new ArrayList<>();
        serieRepository.findAll().stream()
                .filter(audioVisual -> audioVisual instanceof Serie)
                .forEach(audioVisual -> serieResponseDTO.add(mm.serieToResponseDTO((Serie) audioVisual)));
        return serieResponseDTO;
    }

    @Override
    public SerieResponseDTO findById(Integer id) throws ExceptionBBDD {
        return null;
    }

    @Override
    public Boolean existsById(Integer id) {
        return null;
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        return null;
    }


}
