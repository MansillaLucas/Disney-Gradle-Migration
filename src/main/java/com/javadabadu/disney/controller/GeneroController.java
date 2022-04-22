package com.javadabadu.disney.controller;

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
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(generoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
//        if(generoService.findById(id).isPresent()){
//            return ResponseEntity.ok().body(generoService.findById(id).get());
//        }
//        return ResponseEntity.ok().body("No se encontro");
        try{
            return ResponseEntity.ok().body(generoService.findById(id).orElseThrow());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> lastId(){
        return ResponseEntity.created(URI.create("localhost:8080/api/v1/generos/"+generoService.lastValueId())).body("se creo un registro");
    }

    public ResponseEntity<?> save(){
        return ResponseEntity.ok("hola");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> crear(@RequestBody Genero genero, @PathVariable Integer id){
        return ResponseEntity.ok().body(generoService.findById(id)
                .map(generoUpdate -> {
                    generoUpdate.setNombre(genero.getNombre());
                    generoUpdate.setImagen(genero.getImagen());
                    return generoService.save(generoUpdate);
                })
                .orElseGet(() -> {
                    return generoService.save(genero);
                })
        );
    }

}
