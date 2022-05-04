package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.AudioVisualRepository;
import com.javadabadu.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Service
public class PeliculaServiceImpl implements PeliculaService {
    @Autowired
    AudioVisualRepository av;
    @Autowired
    private MessageSource message;
    @Autowired
    private ModelMapperDTOImp mm;

    @Override
    public ResponseEntity<?> findById(Integer id, HttpServletRequest request) {
        try {
            Pelicula p = (Pelicula) av.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
            var dto = mm.peliculaToResponseDTO(p);
            return ResponseEntity.ok().body(dto);
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }


    }

    @Override
    public ResponseEntity<?> save(Pelicula pelicula, HttpServletRequest request) {
        var peli = av.save(pelicula);
        return ResponseEntity.ok().body(peli);
    }
}
