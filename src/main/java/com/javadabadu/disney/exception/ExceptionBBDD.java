package com.javadabadu.disney.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ExceptionBBDD extends Exception  {

    public ExceptionBBDD() {
    }

    public ExceptionBBDD(String message) {
        super(message);
    }
}
