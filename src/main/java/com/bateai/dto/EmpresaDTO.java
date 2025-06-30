package com.bateai.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpresaDTO {
    @NotBlank
    private String cnpj;

    @NotBlank
    private String nomeFantasia;

    private String razaoSocial;
    private String endereco;

    @NotBlank
    @Email
    private String emailResponsavel;
}