package com.bateai.service;

import com.bateai.dto.RegistroPontoDTO;
import com.bateai.entity.RegistroPonto;
import com.bateai.entity.Usuario;
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

    public RegistroPonto registrarPonto(RegistroPontoDTO dto) {
        Usuario colaborador = usuarioRepository.findById(dto.getColaboradorId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (colaborador.getTipoUsuario() != TipoUsuario.COLABORADOR) {
            throw new IllegalArgumentException("Somente funcionários podem bater ponto");
        }

        RegistroPonto ponto = new RegistroPonto();
        ponto.setColaborador(colaborador);
        ponto.setTipoRegistro(dto.getTipoRegistro());
        ponto.setDataHora(LocalDateTime.now());
        ponto.setLocalizacao(dto.getLocalizacao());

        return registroPontoRepository.save(ponto);
    }

    public List<RegistroPonto> listarPorColaborador(Long colaboradorId) {
        Usuario colaborador = usuarioRepository.findById(colaboradorId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        return registroPontoRepository.findByColaborador(colaborador);
    }

}