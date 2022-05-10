package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.entity.Personaje;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BaseService <E, I>{

    E save(E entity) throws ExceptionBBDD;
    List<E> findAll() throws ExceptionBBDD;
    E findById(I id) throws ExceptionBBDD;
    String softDelete(I id) throws ExceptionBBDD;
    Boolean existsById(I id) throws ExceptionBBDD;
    Integer lastValueId() throws ExceptionBBDD;
    E getEntity(E entity, I id) throws ExceptionBBDD;
    E getEntity(I id, Map<String, Object> propiedades) throws ExceptionBBDD;
    Link getSelfLink(I id, HttpServletRequest request) throws ExceptionBBDD;
    Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD;

}
