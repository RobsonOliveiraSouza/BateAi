package com.bateai.controller;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroFuncionarioDTO;
import com.bateai.entity.Usuario;
import com.bateai.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/cadastrar-funcionario")
    public Usuario cadastrarFuncionario(@RequestBody CadastroFuncionarioDTO dto) {
        return usuarioService.cadastrarFuncionario(dto);
    }
}
