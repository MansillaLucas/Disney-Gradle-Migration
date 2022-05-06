package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;

public interface BaseServiceWrite<E, O, I> {
    O save(E entity);

    O getEntity(E entity, I id) throws ExceptionBBDD;

}
