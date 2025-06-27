package com.bateai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CadastroFuncionarioDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private String email;
    private String senha;
    private Long coordenadorId;
}
