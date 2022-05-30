package com.javadabadu.disney.exception.global;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.response.ResponseInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ResponseInfoDTO> numberFormatException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseInfoDTO("URI inválida, los parametros no son del tipo esperado", request.getRequestURI(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ResponseInfoDTO> mediaTypeNotSupportedException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseInfoDTO("MediaType inválido", request.getRequestURI(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})

    public ResponseEntity<ResponseInfoDTO> methodNotSupportedException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ResponseInfoDTO("Método no soportado para el path correspondiente", request.getRequestURI(), HttpStatus.METHOD_NOT_ALLOWED.value()));
    }


    @ExceptionHandler({ExceptionBBDD.class})
    public ResponseEntity<ResponseInfoDTO> bbddException(HttpServletRequest request, ExceptionBBDD e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseInfoDTO(e.getMessage(), request.getRequestURI(), e.getStatusCode().value()));
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ResponseInfoDTO> AuthException(HttpServletRequest request, AuthenticationException authException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseInfoDTO(authException.getMessage(), request.getRequestURI(), HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ResponseInfoDTO> AccessDeniedException(HttpServletRequest request, AccessDeniedException accesExc) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseInfoDTO(accesExc.getMessage(), request.getRequestURI(), HttpStatus.FORBIDDEN.value()));
    }

}