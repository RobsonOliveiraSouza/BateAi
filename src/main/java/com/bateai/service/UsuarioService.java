package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
import com.bateai.entity.Usuario;

public interface UsuarioService {
    Usuario cadastrarCoordenador(CadastroCoordenadorDTO dto);
    Usuario cadastrarColaborador(CadastroColaboradorDTO dto);
    void deletarUsuario(Long id);
}