package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;

public interface BaseServiceWrite<E, O, I> {
    O save(E entity) throws ExceptionBBDD;

    Link getSelfLink(I id, HttpServletRequest request) throws ExceptionBBDD;

    Link getCollectionLink(HttpServletRequest request) throws ExceptionBBDD;

    String softDelete(I id) throws ExceptionBBDD;

}
