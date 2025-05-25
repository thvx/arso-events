package com.um.pasarela.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Usuario {
    private String username;
    private String password;
    private String githubId;
    private String nombre;
    private List<String> roles;

    public Usuario(String username, String password, String githubId, String nombre, List<String> roles) {
        this.username = username;
        this.password = password;
        this.githubId = githubId;
        this.nombre = nombre;
        this.roles = roles;
    }
}