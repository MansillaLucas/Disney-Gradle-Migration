package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;

public interface BaseServiceParalelo<R,E,I> {
    E getSaveEntity(R requestDto, I id) throws ExceptionBBDD;
}
