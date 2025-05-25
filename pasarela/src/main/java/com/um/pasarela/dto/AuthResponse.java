package com.um.pasarela.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private String username;
    private String nombre;
    private List<String> roles;

    public AuthResponse(String token, String username, String nombre, List<String> roles) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.roles = roles;
    }
}