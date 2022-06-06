package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;

import java.util.Map;

public interface BaseServicePatch<T, O, I> {
    O getPersistenceEntity(T entityRequestDTO, Integer id) throws ExceptionBBDD;

    O updatePartial(I id, Map<String, Object> propiedades) throws ExceptionBBDD;
}
