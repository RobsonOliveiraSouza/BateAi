package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
import com.bateai.dto.DashboardDTO;
import com.bateai.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO cadastrarCoordenador(CadastroCoordenadorDTO dto);
    UsuarioResponseDTO cadastrarColaborador(CadastroColaboradorDTO dto);
    void aprovarVinculo(Long idColaborador, String coordenadorEmail);
    void rejeitarVinculo(Long idColaborador, String coordenadorEmail);
    void removerVinculo(Long idColaborador, String coordenadorEmail);
    void deletarUsuario(Long idColaborador, String coordenadorEmail);
    void redefinirSenha(Long id, String novaSenha);
    List<UsuarioResponseDTO> listarColaboradoresPendentes(Long empresaId);
    List<UsuarioResponseDTO> listarColaboradoresAprovados(Long empresaId);
    List<UsuarioResponseDTO> listarTodosColaboradores(Long empresaId);
    List<UsuarioResponseDTO> listarCoordenadoresPorEmpresa(Long empresaId);
    DashboardDTO gerarDashboard(Long empresaId);
}