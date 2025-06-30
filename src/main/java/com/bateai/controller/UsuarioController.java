package com.bateai.controller;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
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
    public Usuario cadastrarCoordenador(@RequestBody CadastroCoordenadorDTO dto) {
        return usuarioService.cadastrarCoordenador(dto);
    }

    @PostMapping("/cadastrar-colaborador")
    public Usuario cadastrarColaborador(@RequestBody CadastroColaboradorDTO dto) {
        return usuarioService.cadastrarColaborador(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário excluído com sucesso.");
    }
}
