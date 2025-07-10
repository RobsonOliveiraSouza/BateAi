package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
import com.bateai.dto.DashboardDTO;
import com.bateai.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO cadastrarCoordenador(CadastroCoordenadorDTO dto);
    UsuarioResponseDTO cadastrarColaborador(CadastroColaboradorDTO dto);
    void aprovarVinculoColaborador(Long id, String email);
    void rejeitarVinculoColaborador(Long id, String email);
    void removerVinculoColaborador(Long id, String email);
    void aprovarVinculoCoordenador(Long id, String email);
    void rejeitarVinculoCoordenador(Long id, String email);
    void removerVinculoCoordenador(Long id, String email);
    void deletarUsuario(Long idColaborador, String coordenadorEmail);
    void redefinirSenha(Long id, String novaSenha);
    List<UsuarioResponseDTO> listarColaboradoresPendentes(Long empresaId);
    List<UsuarioResponseDTO> listarColaboradoresAprovados(Long empresaId);
    List<UsuarioResponseDTO> listarTodosColaboradores(Long empresaId);
    List<UsuarioResponseDTO> listarCoordenadoresPorEmpresa(Long empresaId);
    DashboardDTO gerarDashboard(Long empresaId);
}