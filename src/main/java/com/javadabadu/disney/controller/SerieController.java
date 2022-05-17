package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.dto.SerieRequestDTO;
import com.javadabadu.disney.models.dto.SerieResponseDTO;
import com.javadabadu.disney.models.entity.Serie;
import com.javadabadu.disney.service.SerieService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = Uri.SERIES)
public class SerieController {

    @Autowired
    SerieService serieService;

    @GetMapping("/")
    public ResponseEntity<CollectionModel<EntityModel<SerieResponseDTO>>> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<SerieResponseDTO> serieResponseDTOList = serieService.findAll();
        List<EntityModel<SerieResponseDTO>> entityModelList = new ArrayList<>();
        for (SerieResponseDTO serieResponseDTO1 : serieResponseDTOList) {
            entityModelList.add(EntityModel.of(serieResponseDTO1, serieService.getSelfLink(serieResponseDTO1.getId(), request)));
        }
        return ResponseEntity.ok().body(CollectionModel.of(entityModelList, serieService.getCollectionLink(request)));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<SerieResponseDTO>> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        SerieResponseDTO serieDTO = serieService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(serieDTO, serieService.getSelfLink(id, request),
                serieService.getCollectionLink(request)));
    }

    @PostMapping("/")
    public ResponseEntity<String> lastId(HttpServletRequest request) throws ExceptionBBDD{

       return ResponseEntity.created(URI.create(request.getRequestURI()
                    + serieService.lastValueId())).body("Se creo un registro");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<SerieResponseDTO>> crear(@RequestBody SerieRequestDTO serieRequestDTO, @PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        SerieResponseDTO serieDTO = serieService.getPersistenceEntity(serieRequestDTO, id);
        return ResponseEntity.ok().body(EntityModel.of(serieDTO, serieService.getSelfLink(id, request), serieService.getCollectionLink(request)));
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<SerieResponseDTO>> update(@PathVariable Integer id,
                                    @RequestBody Map<String, Object> propiedades,
                                    HttpServletRequest request) throws ExceptionBBDD {
        SerieResponseDTO serieDTO = serieService.updatePartial(id, propiedades);
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(serieDTO, serieService.getSelfLink(id, request)));

    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ResponseInfoDTO>> delete(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        String body = serieService.softDelete(serieService.findById(id).getId());
        ResponseInfoDTO response = new ResponseInfoDTO(body, request.getRequestURI(), HttpStatus.OK.value());
        return ResponseEntity.ok().body(EntityModel.of(response, serieService.getCollectionLink(request)));
    }
}