package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.service.SerieService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = Uri.SERIES)
public class SerieController {

    @Autowired
    SerieService serieService;

    @GetMapping("/")
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<SerieResponseDTO> serieResponseDTOList = serieService.findAll();
        List<EntityModel<SerieResponseDTO>> entityModelList = new ArrayList<>();
        for (SerieResponseDTO serieResponseDTO1 : serieResponseDTOList) {
            entityModelList.add(EntityModel.of(serieResponseDTO1, serieService.getSelfLink(serieResponseDTO1.getId(), request)));
        }
        return ResponseEntity.ok().body(CollectionModel.of(entityModelList, serieService.getCollectionLink(request)));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        SerieResponseDTO serieDTO = serieService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(serieDTO, serieService.getSelfLink(id, request),
                serieService.getCollectionLink(request)));
    }
}


