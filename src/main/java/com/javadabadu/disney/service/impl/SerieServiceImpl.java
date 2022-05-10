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
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
           try {
               serieRepository.findAll().stream()
                       .filter(audioVisual -> audioVisual instanceof Serie)
                       .forEach(audioVisual -> serieResponseDTO.add(mm.serieToResponseDTO((Serie) audioVisual)));
           } catch (Exception e) {
               throw new ExceptionBBDD("Error en la transaccion contacte con su ADM");
           }

        return serieResponseDTO;
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

    //TODO implementar metodo
    @Override
    public Integer lastValueId() throws ExceptionBBDD {
        return null;
    }


    //TODO restan metodos de guardar y actualizar (agregar tambien in interfaz correspondiente)


    @Override
    public Serie getEntitySave(Serie entity, Integer id) throws ExceptionBBDD {
        return null;
    }

    @Override
    public SerieResponseDTO save(Serie entity) {
        return null;
    }

    @Override
    public Link getSelfLink(Integer id, HttpServletRequest request)  {
        return null;
    }

    @Override
    public Link getCollectionLink(HttpServletRequest request)  {
        return null;
    }

    @Override
    public String softDelete(Integer id) throws ExceptionBBDD {
        return null;
    }

    @Override
    public Serie getEntity(Integer id, Map<String, Object> propiedades) throws ExceptionBBDD {
        return null;
    }
}
