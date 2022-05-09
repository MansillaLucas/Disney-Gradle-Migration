package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PersonajeRequestDTO;

public interface BaseServiceWrite<E, O, I> {
    O save(E entity);

    E getEntity(E entity, I id) throws ExceptionBBDD;

    String softDelete(I id) throws ExceptionBBDD;

    E getEntity(I id, PersonajeRequestDTO propiedades) throws ExceptionBBDD;
}
