package com.bateai.dto;

import com.bateai.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaResumoDTO {
    private Long id;
    private String nomeFantasia;
    private String cnpj;

    public EmpresaResumoDTO(Empresa empresa) {
        this.id = empresa.getId();
        this.nomeFantasia = empresa.getNomeFantasia();
        this.cnpj = empresa.getCnpj();
    }
}
