package com.bateai.service;

import com.bateai.dto.EmpresaDTO;
import com.bateai.dto.EmpresaResponseDTO;

public interface EmpresaService {
    EmpresaResponseDTO cadastrarEmpresa(EmpresaDTO dto);
    void redefinirSenha(Long empresaId, String novaSenha);
}