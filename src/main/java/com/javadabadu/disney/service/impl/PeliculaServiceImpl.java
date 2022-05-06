package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PeliculaResponseDTO;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.PeliculaRepository;
import com.javadabadu.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PeliculaServiceImpl implements PeliculaService {
    @Autowired
    PeliculaRepository peliculaRepository;
    @Autowired
    private MessageSource message;
    @Autowired
    private ModelMapperDTOImp mm;

    @Override
    public List<PeliculaResponseDTO> findAll() throws ExceptionBBDD {
        List<PeliculaResponseDTO> peliculaResponseDTO = new ArrayList<>();
        peliculaRepository.findAll().stream()
                .filter(audioVisual -> audioVisual instanceof Pelicula)
                .forEach(audioVisual -> peliculaResponseDTO.add(mm.peliculaToResponseDTO((Pelicula) audioVisual)));
        return peliculaResponseDTO;
    }

    @Override
    public PeliculaResponseDTO findById(Integer id) throws ExceptionBBDD {
        AudioVisual av = peliculaRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
        if (av instanceof Pelicula) {
            Pelicula p = (Pelicula) av;
            PeliculaResponseDTO peliculaResponseDTO = mm.peliculaToResponseDTO(p);
            return peliculaResponseDTO;
        }
        throw new ExceptionBBDD("el id no coincide con el de una pelicula");
    }

    @Override
    public Boolean existsById(Integer id) {
        return peliculaRepository.existsById(id);
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        return 99;
    }
}
