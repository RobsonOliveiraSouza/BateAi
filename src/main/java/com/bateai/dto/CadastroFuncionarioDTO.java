package com.bateai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroFuncionarioDTO {
    private String nome;
    private String email;
    private String senha;
    private Long coordenadorId;
}
