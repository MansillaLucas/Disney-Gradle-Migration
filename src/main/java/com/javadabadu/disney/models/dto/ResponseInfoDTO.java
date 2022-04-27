package com.javadabadu.disney.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseInfoDTO {
    private String timestap = LocalDate.now().toString();
    private String message;
    private String path;
    private Integer codeStatus;

    public ResponseInfoDTO(String message, String path, Integer codeStatus) {
        this.message = message;
        this.path = path;
        this.codeStatus = codeStatus;
    }
}
