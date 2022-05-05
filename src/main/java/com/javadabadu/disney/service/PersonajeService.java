package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import com.javadabadu.disney.models.entity.Personaje;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PersonajeService extends BaseService<Personaje, Integer> {

    List<Personaje> filterCharacter(String name, Integer edad, Integer idMovie) throws ExceptionBBDD;

}
