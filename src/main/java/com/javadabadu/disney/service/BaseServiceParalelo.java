package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;

import java.util.List;

public interface BaseServiceParalelo<E, O, I> {
    List<O> findAll() throws ExceptionBBDD;

    O findById(I id) throws ExceptionBBDD;
}
