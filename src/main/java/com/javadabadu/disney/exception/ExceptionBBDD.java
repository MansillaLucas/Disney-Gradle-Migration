package com.javadabadu.disney.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionBBDD extends Exception {

    private final HttpStatus statusCode;


    public ExceptionBBDD(String message) {
        super(message);
        statusCode = null;
    }

    public ExceptionBBDD(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
