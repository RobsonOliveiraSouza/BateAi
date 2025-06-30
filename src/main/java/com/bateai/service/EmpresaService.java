package com.bateai.service;

import com.bateai.dto.EmpresaDTO;
import com.bateai.entity.Empresa;

public interface EmpresaService {
    Empresa cadastrarEmpresa(EmpresaDTO dto);
}
