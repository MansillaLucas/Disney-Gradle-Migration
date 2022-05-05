package com.javadabadu.disney.service.impl;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.entity.AudioVisual;
import com.javadabadu.disney.models.entity.Pelicula;
import com.javadabadu.disney.models.mapped.ModelMapperDTOImp;
import com.javadabadu.disney.repository.PeliculaRepository;
import com.javadabadu.disney.service.PeliculaService;
import javassist.expr.Instanceof;
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
    PeliculaRepository peliculaRepository;
    @Autowired
    private MessageSource message;
    @Autowired
    private ModelMapperDTOImp mm;

    @Override
    public ResponseEntity<?> findById(Integer id, HttpServletRequest request) {
        try {
            AudioVisual av = peliculaRepository.findById(id).orElseThrow(() -> new ExceptionBBDD(message.getMessage("id.not.found", new String[]{Integer.toString(id)}, Locale.US)));
            if(av instanceof Pelicula) {
                Pelicula p = (Pelicula) av;
                var dto = mm.peliculaToResponseDTO(p);
                return ResponseEntity.ok().body(dto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO("El ID no corresponde a una pelicula.", request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
        catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @Override
    public ResponseEntity<?> save(Pelicula pelicula, HttpServletRequest request) {
        var peli = peliculaRepository.save(pelicula);
        return ResponseEntity.ok().body(peli);
    }

    @Override
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Pelicula pelicula, Integer id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> softDelete(Integer id, HttpServletRequest request) {
        return null;
    }
}
