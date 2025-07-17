package com.bateai.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @NotBlank
    private String senhaResponsavel;
}