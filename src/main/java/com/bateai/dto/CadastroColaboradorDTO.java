package com.bateai.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CadastroColaboradorDTO {
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
    private String setor;

    @NotBlank
    private Long empresaId;

    private Long coordenadorId;
}