package com.bateai.controller;

import com.bateai.dto.EmpresaDTO;
import com.bateai.entity.Empresa;
import com.bateai.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/cadastrar")
    public Empresa cadastrarEmpresa(@RequestBody EmpresaDTO dto) {
        return empresaService.cadastrarEmpresa(dto);
    }
}