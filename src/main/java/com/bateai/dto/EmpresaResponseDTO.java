package com.bateai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpresaResponseDTO {
    private Long id;
    private String cnpj;
    private String nomeFantasia;
    private String razaoSocial;
    private String endereco;
    private String emailResponsavel;
    private boolean emailConfirmado;
}
