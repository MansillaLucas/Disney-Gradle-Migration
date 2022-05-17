package com.javadabadu.disney.service;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.PersonajeRequestDTO;

import java.util.Map;

public interface BaseServiceRequest <RQ, O, I>{

    O getPersistenceEntity(RQ entityRequest, Integer id) throws ExceptionBBDD;
    O updatePartial(I id, Map<String, Object> propiedades) throws ExceptionBBDD ;
}
