package com.bateai.service;

import com.bateai.dto.EmpresaResumoDTO;
import com.bateai.dto.RegistroPontoDTO;
import com.bateai.dto.RegistroPontoResponseDTO;
import com.bateai.dto.UsuarioResumoDTO;
import com.bateai.entity.Empresa;
import com.bateai.entity.RegistroPonto;
import com.bateai.entity.Usuario;
import com.bateai.entity.enums.StatusVinculo;
import com.bateai.entity.enums.TipoUsuario;
import com.bateai.repository.RegistroPontoRepository;
import com.bateai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroPontoServiceImp implements RegistroPontoService {

    private final RegistroPontoRepository registroPontoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RegistroPontoServiceImp(RegistroPontoRepository registroPontoRepository, UsuarioRepository usuarioRepository) {
        this.registroPontoRepository = registroPontoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private RegistroPontoResponseDTO toResponseDTO(RegistroPonto ponto) {
        Usuario u = ponto.getColaborador();
        Empresa e = ponto.getEmpresa();

        UsuarioResumoDTO colaboradorDTO = new UsuarioResumoDTO(
                u.getId(),
                u.getNome(),
                u.getEmail()
        );

        EmpresaResumoDTO empresaDTO = new EmpresaResumoDTO(
                e.getId(),
                e.getNomeFantasia(),
                e.getCnpj()
        );

        return new RegistroPontoResponseDTO(
                ponto.getId(),
                ponto.getDataHora(),
                ponto.getTipoRegistro().name(),
                ponto.getLocalizacao(),
                colaboradorDTO,
                empresaDTO
        );
    }

    public RegistroPontoResponseDTO registrarPonto(RegistroPontoDTO dto, String email) {
        Usuario colaborador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (colaborador.getTipoUsuario() != TipoUsuario.COLABORADOR &&
            colaborador.getTipoUsuario() != TipoUsuario.COORDENADOR) {
            throw new IllegalArgumentException("Somente funcionários podem bater ponto");
        }

        if (colaborador.getStatusVinculo() != StatusVinculo.APROVADO_COLABORADOR) {
            throw new IllegalStateException("Usuário não autorizado a bater ponto. Status atual: " + colaborador.getStatusVinculo());
        }

        RegistroPonto ultimo = registroPontoRepository.findTopByColaboradorOrderByDataHoraDesc(colaborador);
        if (ultimo != null && ultimo.getTipoRegistro() == dto.getTipoRegistro()) {
            Duration diff = Duration.between(ultimo.getDataHora(), LocalDateTime.now());
            if (diff.toMinutes() < 2) {
                throw new IllegalStateException("Já existe um registro recente desse tipo.");
            }
        }

        RegistroPonto ponto = new RegistroPonto();
        ponto.setColaborador(colaborador);
        ponto.setEmpresa(colaborador.getEmpresa());
        ponto.setTipoRegistro(dto.getTipoRegistro());
        ponto.setDataHora(LocalDateTime.now());
        ponto.setLocalizacao(dto.getLocalizacao());

        RegistroPonto salvo = registroPontoRepository.save(ponto);
        return toResponseDTO(salvo);
    }

    @Override
    public List<RegistroPontoResponseDTO> listarPontosPorColaborador(Long colaboradorId, String emailCoordenador) {
        Usuario colaborador = usuarioRepository.findById(colaboradorId)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado"));

        Usuario coordenador = usuarioRepository.findByEmail(emailCoordenador)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        if (!colaborador.getEmpresa().getId().equals(coordenador.getEmpresa().getId())) {
            throw new IllegalStateException("Colaborador não pertence à mesma empresa do coordenador.");
        }

        return registroPontoRepository.findByColaborador(colaborador)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<RegistroPontoResponseDTO> listarMeusPontos(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return registroPontoRepository.findByColaborador(usuario)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<RegistroPontoResponseDTO> listarPontosPorEmpresa(Long empresaId, String emailCoordenador) {
        Usuario coordenador = usuarioRepository.findByEmail(emailCoordenador)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        if (coordenador.getEmpresa() == null || !coordenador.getEmpresa().getId().equals(empresaId)) {
            throw new IllegalStateException("Acesso negado à empresa.");
        }

        List<RegistroPonto> pontos = registroPontoRepository.findAll().stream()
                .filter(p -> p.getEmpresa().getId().equals(empresaId))
                .toList();

        return pontos.stream().map(this::toResponseDTO).toList();
    }
}