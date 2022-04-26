package com.javadabadu.disney.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonPatch;
import com.flipkart.zjsonpatch.JsonPatchApplicationException;
import com.javadabadu.disney.models.entity.Genero;
import com.javadabadu.disney.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(generoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
//        if(generoService.findById(id).isPresent()){
//            return ResponseEntity.ok().body(generoService.findById(id).get());
//        }
//        return ResponseEntity.ok().body("No se encontro");
        try {
            return ResponseEntity.ok().body(generoService.findById(id).orElseThrow());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> lastId() {
        return ResponseEntity.created(URI.create("localhost:8080/api/v1/generos/" + generoService.lastValueId())).body("se creo un registro");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> crear(@RequestBody Genero genero, @PathVariable Integer id) {
        return ResponseEntity.ok().body(generoService.findById(id)
                .map(generoUpdate -> {

                    generoUpdate.setNombre(genero.getNombre());
                    generoUpdate.setImagen(genero.getImagen());
                    generoUpdate.setAlta(true);
                    return generoService.save(generoUpdate);
                })
                .orElseGet(() -> generoService.save(genero))
        );
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody JsonNode patch) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Genero searchedGenero = generoService.findById(id).orElseThrow(() -> new Exception());
            JsonNode searchedGeneroNode = objectMapper.convertValue(searchedGenero, JsonNode.class);

            JsonNode patchedGeneroNode = JsonPatch.apply(patch, searchedGeneroNode); // [Parcheo]
            searchedGenero = objectMapper.treeToValue(patchedGeneroNode, Genero.class);

            generoService.save(searchedGenero);
            return ResponseEntity.ok(searchedGenero);

        } catch (JsonPatchApplicationException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<?> delete( @PathVariable Integer id) throws Exception {
        return ResponseEntity.ok().body(generoService.softDelete(generoService.findById(id).orElseThrow(Exception::new).getId()));

    }
}
