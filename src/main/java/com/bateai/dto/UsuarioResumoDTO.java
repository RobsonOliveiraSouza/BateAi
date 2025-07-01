package com.bateai.dto;

import lombok.Data;

@Data
public class UsuarioResumoDTO {
    private Long id;
    private String nome;
    private String email;

    public UsuarioResumoDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }
}
