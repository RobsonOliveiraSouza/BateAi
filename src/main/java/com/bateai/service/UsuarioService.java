package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
import com.bateai.dto.DashboardDTO;
import com.bateai.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO cadastrarCoordenador(CadastroCoordenadorDTO dto);
    UsuarioResponseDTO cadastrarColaborador(CadastroColaboradorDTO dto);
    void aprovarVinculo(Long idColaborador);
    void rejeitarVinculo(Long idColaborador);
    void deletarUsuario(Long id);
    List<UsuarioResponseDTO> listarColaboradoresPendentes(Long empresaId);
    List<UsuarioResponseDTO> listarColaboradoresAprovados(Long empresaId);
    List<UsuarioResponseDTO> listarTodosColaboradores(Long empresaId);
    DashboardDTO gerarDashboard(Long empresaId);
}