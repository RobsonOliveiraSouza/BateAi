package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
import com.bateai.dto.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO cadastrarCoordenador(CadastroCoordenadorDTO dto);
    UsuarioResponseDTO cadastrarColaborador(CadastroColaboradorDTO dto);
    void deletarUsuario(Long id);
}