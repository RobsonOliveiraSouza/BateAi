package com.bateai.controller;

import com.bateai.dto.EmpresaDTO;
import com.bateai.entity.Empresa;
import com.bateai.service.EmpresaService;
import com.bateai.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;

    @Autowired
    public EmpresaController(EmpresaService empresaService, UsuarioService usuarioService) {
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public Empresa cadastrarEmpresa(@RequestBody EmpresaDTO dto) {
        return empresaService.cadastrarEmpresa(dto);
    }
}