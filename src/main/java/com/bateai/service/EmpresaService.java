package com.bateai.service;

import com.bateai.dto.EmpresaDTO;
import com.bateai.dto.EmpresaResponseDTO;
import com.bateai.entity.Empresa;

import java.util.List;

public interface EmpresaService {
    EmpresaResponseDTO cadastrarEmpresa(EmpresaDTO dto);
    void redefinirSenha(Long empresaId, String novaSenha);
    List<Empresa> buscarPorNomeOuCnpj(String termo);
}