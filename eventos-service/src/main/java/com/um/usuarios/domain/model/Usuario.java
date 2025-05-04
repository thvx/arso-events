package com.um.usuarios.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class Usuario {
    private String id;
    private String nombre;
    private String userName;
    private String password;
    private String email;
    private Roles roles;
}