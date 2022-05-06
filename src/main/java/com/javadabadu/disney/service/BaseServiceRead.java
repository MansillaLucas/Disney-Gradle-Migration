package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import org.springframework.hateoas.Link;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BaseServiceRead<O, I> {
    List<O> findAll() throws ExceptionBBDD;

    O findById(I id) throws ExceptionBBDD;

    Boolean existsById(I id);

    Integer lastValueId() throws ExceptionBBDD;

    Link getSelfLink(I id, HttpServletRequest request);

    Link getCollectionLink(HttpServletRequest request);
}
