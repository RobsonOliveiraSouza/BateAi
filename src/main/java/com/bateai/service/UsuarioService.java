package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroFuncionarioDTO;
import com.bateai.entity.Usuario;

public interface UsuarioService {
    Usuario cadastrarCoordenador(CadastroCoordenadorDTO dto);
    Usuario cadastrarFuncionario(CadastroFuncionarioDTO dto);
}
