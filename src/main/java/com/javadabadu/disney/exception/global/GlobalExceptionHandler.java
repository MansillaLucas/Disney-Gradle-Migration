package com.javadabadu.disney.exception.global;

import com.javadabadu.disney.models.dto.ResponseInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> NumberFormatException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO("URI inválida, los parametros no son del tipo esperado", request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> MediaTypeNotSupportedException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO("MediaType inválido", request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
    }

}