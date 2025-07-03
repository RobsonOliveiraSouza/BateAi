package com.bateai.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String senha;
}
