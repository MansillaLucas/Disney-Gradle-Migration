package com.javadabadu.disney.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonPatch;
import com.flipkart.zjsonpatch.JsonPatchApplicationException;
import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.service.GeneroService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping(value = Uri.GENEROS)
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id, HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(generoService.findById(id));
        } catch (ExceptionBBDD e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(e.getMessage(),request.getRequestURI(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(generoService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<?> lastId(HttpServletRequest request) {
        return ResponseEntity.created(URI.create(request.getRequestURI() + generoService.lastValueId())).body("se creo un registro");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Genero genero, @PathVariable Integer id) {
        Genero source = null;
        try {
            source = generoService.findById(id);
            genero.setImagen(source.getImagen());
            genero.setPeliculas(source.getPeliculas());
        } catch (ExceptionBBDD e) {
            genero.setId(id);
            genero.setAlta(true);
        }
        return ResponseEntity.ok().body(generoService.save(genero));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody JsonNode patch) {
        try {
            Genero searchedGenero = generoService.findById(id);
            searchedGenero = patchGenero(searchedGenero, patch);
            generoService.save(searchedGenero);
            return ResponseEntity.ok(searchedGenero);

        } catch (JsonPatchApplicationException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping(path = "/v2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer2(@PathVariable Integer id, @RequestBody Map<String, Object> propiedades) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Genero searchedGenero = generoService.findById(id);
            Map<String, Object> searchedGeneroMap = mapper.convertValue(searchedGenero, Map.class);
            propiedades.forEach((k, v) -> {
                if (searchedGeneroMap.containsKey(k)) {
                    searchedGeneroMap.replace(k, searchedGeneroMap.get(k), v);
                }
            });
            searchedGenero = mapper.convertValue(searchedGeneroMap, Genero.class);
            return ResponseEntity.status(HttpStatus.OK).body(generoService.save(searchedGenero));
        } catch (ExceptionBBDD ebd) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseInfoDTO(ebd.getMessage(), "acá va el path", HttpStatus.NOT_FOUND.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete( @PathVariable Integer id) throws Exception {
        return ResponseEntity.ok().body(generoService.softDelete(generoService.findById(id).getId()));

    }

    private Genero patchGenero(Genero generoToPatch, JsonNode patch) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode searchedGeneroNode = objectMapper.convertValue(generoToPatch, JsonNode.class);

        JsonNode patchedGeneroNode = JsonPatch.apply(patch, searchedGeneroNode); // [Parcheo]
        generoToPatch = objectMapper.treeToValue(patchedGeneroNode, Genero.class);

        return generoToPatch;
    }


}
