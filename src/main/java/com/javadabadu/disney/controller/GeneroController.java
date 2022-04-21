package com.javadabadu.disney.controller;

import com.javadabadu.disney.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
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

    public ResponseEntity<?> save(){
        return ResponseEntity.ok("hola");
    }

}
