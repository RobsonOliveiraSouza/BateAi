package com.bateai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpresaResumoDTO {
    private Long id;
    private String nomeFantasia;
    private String cnpj;
}
