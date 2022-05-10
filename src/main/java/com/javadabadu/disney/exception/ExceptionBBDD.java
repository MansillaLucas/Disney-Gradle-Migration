package com.javadabadu.disney.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionBBDD extends Exception {

    private String message;
    private HttpStatus statusCode;

    public ExceptionBBDD() {
    }

    public ExceptionBBDD(String message) {
        super(message);
    }

    public ExceptionBBDD(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}
