package com.bateai.service;

import com.bateai.dto.RegistroPontoDTO;
import com.bateai.dto.RegistroPontoResponseDTO;
import com.bateai.dto.UsuarioResumoDTO;
import com.bateai.entity.RegistroPonto;
import com.bateai.entity.Usuario;
import com.bateai.entity.enums.StatusVinculo;
import com.bateai.entity.enums.TipoUsuario;
import com.bateai.repository.RegistroPontoRepository;
import com.bateai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroPontoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroPontoRepository registroPontoRepository;

    private RegistroPontoResponseDTO toResponseDTO(RegistroPonto ponto) {
        Usuario u = ponto.getColaborador();

        UsuarioResumoDTO colaboradorDTO = new UsuarioResumoDTO(
                u.getId(),
                u.getNome(),
                u.getEmail()
        );

        return new RegistroPontoResponseDTO(
                ponto.getId(),
                ponto.getDataHora(),
                ponto.getTipoRegistro().name(),
                ponto.getLocalizacao(),
                colaboradorDTO
        );
    }

    public RegistroPontoResponseDTO registrarPonto(RegistroPontoDTO dto) {
        Usuario colaborador = usuarioRepository.findById(dto.getColaboradorId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (colaborador.getTipoUsuario() != TipoUsuario.COLABORADOR) {
            throw new IllegalArgumentException("Somente funcionários podem bater ponto");
        }

        if (colaborador.getStatusVinculo() != StatusVinculo.APROVADO) {
            throw new IllegalStateException("Usuário não autorizado a bater ponto.");
        }

        RegistroPonto ponto = new RegistroPonto();
        ponto.setColaborador(colaborador);
        ponto.setTipoRegistro(dto.getTipoRegistro());
        ponto.setDataHora(LocalDateTime.now());
        ponto.setLocalizacao(dto.getLocalizacao());

        RegistroPonto salvo = registroPontoRepository.save(ponto);
        return toResponseDTO(salvo);
    }

    public List<RegistroPonto> listarPorColaborador(Long colaboradorId) {
        Usuario colaborador = usuarioRepository.findById(colaboradorId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        return registroPontoRepository.findByColaborador(colaborador);
    }

}