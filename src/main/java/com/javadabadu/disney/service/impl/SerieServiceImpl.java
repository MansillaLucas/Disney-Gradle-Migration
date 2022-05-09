package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Serie;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.SerieRepository;
import com.javadabadu.disney.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public SerieResponseDTO save(Serie entity) {
        return mm.serieToResponseDTO(serieRepository.save(entity));
    }

    @Override
    public SerieResponseDTO findById(Integer id) throws ExceptionBBDD {
        AudioVisual av = serieRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
        if(av instanceof Serie){
            Serie s = (Serie) av;
            SerieResponseDTO serieResponseDTO = mm.serieToResponseDTO(s);
            return serieResponseDTO;
        }
        throw new ExceptionBBDD(message.getMessage("id.not.serie", new String[]{Integer.toString(id)}, Locale.US));
    }

    @Override
    public Boolean existsById(Integer id) {
        return null;
    }

    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        return null;
    }

    @Override
    public SerieResponseDTO getEntity(Serie entity, Integer id) throws ExceptionBBDD {
        return null;
    }
}
