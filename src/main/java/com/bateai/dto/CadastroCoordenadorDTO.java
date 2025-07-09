package com.bateai.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CadastroCoordenadorDTO {
    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;

    @NotBlank
    private String cpf;

    private String telefone;

    @NotBlank
    @NotNull
    private Long empresaId;
}