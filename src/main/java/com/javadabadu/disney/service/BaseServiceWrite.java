package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;

public interface BaseServiceWrite<E, O, I>{
    E getEntity(E entity, I id) throws ExceptionBBDD;
    O save(E entity);
    Link getSelfLink(I id, HttpServletRequest request) throws ExceptionBBDD;
    Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD;


}
