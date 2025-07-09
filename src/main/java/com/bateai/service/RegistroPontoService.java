package com.bateai.service;

import com.bateai.dto.RegistroPontoDTO;
import com.bateai.dto.RegistroPontoResponseDTO;
import com.bateai.entity.RegistroPonto;

import java.util.List;

public interface RegistroPontoService {
    RegistroPontoResponseDTO registrarPonto(RegistroPontoDTO dto);
    List<RegistroPontoResponseDTO> listarPontosPorColaborador(Long colaboradorId, String emailCoordenador);
    List<RegistroPontoResponseDTO> listarMeusPontos(String email);
    List<RegistroPontoResponseDTO> listarPontosPorEmpresa(Long empresaId, String emailCoordenador);
}
