package com.javadabadu.disney.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Integer id;

    private String username;

    private String password;

    private RolResponseDTO rol;
}
