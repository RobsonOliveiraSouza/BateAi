package com.bateai.controller;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
import com.bateai.dto.UsuarioResponseDTO;
import com.bateai.entity.Usuario;
import com.bateai.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar-coordenador")
    public ResponseEntity<UsuarioResponseDTO> cadastrarCoordenador(@RequestBody CadastroCoordenadorDTO dto) {
        UsuarioResponseDTO response = usuarioService.cadastrarCoordenador(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar-colaborador")
    public ResponseEntity<UsuarioResponseDTO> cadastrarColaborador(@RequestBody CadastroColaboradorDTO dto) {
        UsuarioResponseDTO response = usuarioService.cadastrarColaborador(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}