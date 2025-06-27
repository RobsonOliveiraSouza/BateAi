package com.bateai.service;

import com.bateai.dto.RegistroPontoDTO;
import com.bateai.entity.RegistroPonto;
import com.bateai.entity.Usuario;
import com.bateai.enums.TipoUsuario;
import com.bateai.repository.RegistroPontoRepository;
import com.bateai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistroPontoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroPontoRepository registroPontoRepository;

    /* public RegistroPonto registrarPonto(RegistroPontoDTO dto) {
        Usuario funcionario = usuarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (funcionario.getTipoUsuario() != TipoUsuario.FUNCIONARIO) {
            throw new IllegalArgumentException("Somente funcionários podem bater ponto");
        }

        RegistroPonto ponto = new RegistroPonto();
        ponto.setFuncionario(funcionario);
        ponto.setTipoRegistro(dto.getTipoRegistro());
        ponto.setDataHora(LocalDateTime.now());
        ponto.setLocalizacao(dto.getLocalizacao());

        return registroPontoRepository.save(ponto);
    }

     */
}


