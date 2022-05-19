package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.PersonajeResponseDTO;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface BaseServiceWrite<E, O, I> {
    O save(E entity) throws ExceptionBBDD;

    Link getSelfLink(I id, HttpServletRequest request) throws ExceptionBBDD;

    Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD;

    String softDelete(I id) throws ExceptionBBDD;

}
