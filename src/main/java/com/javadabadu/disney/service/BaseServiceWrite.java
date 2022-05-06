package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;

import java.util.Map;

public interface BaseServiceWrite<E, O, I> {
    O save(E entity);

    E getEntity(E entity, I id) throws ExceptionBBDD;

    String softDelete(I id) throws ExceptionBBDD;

    E getEntity(I id, Map<String, Object> propiedades) throws ExceptionBBDD;

}
