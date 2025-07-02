package com.bateai.controller;

import com.bateai.dto.EmpresaDTO;
import com.bateai.dto.EmpresaResponseDTO;
import com.bateai.dto.NovaSenhaDTO;
import com.bateai.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EmpresaResponseDTO> cadastrarEmpresa(@RequestBody @Valid EmpresaDTO dto) {
        EmpresaResponseDTO empresa = empresaService.cadastrarEmpresa(dto);
        return ResponseEntity.ok(empresa);
    }

    @PutMapping("/redefinir-senha/{id}")
    public ResponseEntity<Void> redefinirSenha(@PathVariable Long id, @RequestBody @Valid NovaSenhaDTO dto) {
        empresaService.redefinirSenha(id, dto.getNovaSenha());
        return ResponseEntity.noContent().build();
    }
}