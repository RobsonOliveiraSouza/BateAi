package com.bateai.dto;

import com.bateai.entity.enums.StatusVinculo;
import com.bateai.entity.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String setor;
    private TipoUsuario tipoUsuario;
    private StatusVinculo statusVinculo;
    private EmpresaResumoDTO empresa;
}

