package com.bateai.service;

import com.bateai.dto.*;
import com.bateai.entity.Empresa;
import com.bateai.entity.Usuario;
import com.bateai.entity.enums.StatusVinculo;
import com.bateai.entity.enums.TipoUsuario;
import com.bateai.repository.EmpresaRepository;
import com.bateai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
    }

    private void validarMesmoContextoEmpresa(Usuario solicitante, Usuario alvo) {
        if (!solicitante.getEmpresa().getId().equals(alvo.getEmpresa().getId())) {
            throw new IllegalArgumentException("Usuários de empresas diferentes");
        }
    }

    @Override
    public UsuarioResponseDTO cadastrarCoordenador(CadastroCoordenadorDTO dto) {
        validarEmailUnico(dto.getEmail());

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setCpf(dto.getCpf());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEmpresa(empresa);
        usuario.setTipoUsuario(TipoUsuario.COORDENADOR);
        usuario.setStatusVinculo(StatusVinculo.PENDENTE_COORDENADOR);

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponseDTO(salvo);
    }

    @Override
    public UsuarioResponseDTO cadastrarColaborador(CadastroColaboradorDTO dto) {
        validarEmailUnico(dto.getEmail());

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));
        usuario.setCpf(dto.getCpf());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSetor(dto.getSetor());
        usuario.setEmpresa(empresa);
        usuario.setTipoUsuario(TipoUsuario.COLABORADOR);
        usuario.setStatusVinculo(StatusVinculo.PENDENTE_COLABORADOR);

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponseDTO(salvo);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        Empresa empresa = usuario.getEmpresa();
        EmpresaResumoDTO empresaDTO = new EmpresaResumoDTO(
                empresa.getId(),
                empresa.getNomeFantasia(),
                empresa.getCnpj()
        );

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getSetor(),
                usuario.getTipoUsuario(),
                usuario.getStatusVinculo(),
                empresaDTO
        );
    }

    @Override
    public void aprovarVinculoColaborador(Long id, String email) {
        Usuario coordenador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Usuario colaborador = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado"));

        validarMesmoContextoEmpresa(coordenador, colaborador);
        colaborador.setStatusVinculo(StatusVinculo.APROVADO_COLABORADOR);
        usuarioRepository.save(colaborador);
    }

    @Override
    public void rejeitarVinculoColaborador(Long id, String email) {
        Usuario coordenador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Usuario colaborador = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado"));

        validarMesmoContextoEmpresa(coordenador, colaborador);
        colaborador.setStatusVinculo(StatusVinculo.REJEITADO_COLABORADOR);
        usuarioRepository.save(colaborador);
    }

    @Override
    public void removerVinculoColaborador(Long id, String email) {
        Usuario coordenador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Usuario colaborador = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado"));

        validarMesmoContextoEmpresa(coordenador, colaborador);
        colaborador.setStatusVinculo(StatusVinculo.DESLIGADO_COLABORADOR);
        usuarioRepository.save(colaborador);
    }

    @Override
    public void aprovarVinculoCoordenador(Long id, String emailAutenticado) {
        Usuario aprovador = usuarioRepository.findByEmail(emailAutenticado).orElse(null);
        Usuario coordenador = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        if (aprovador != null) {
            validarMesmoContextoEmpresa(aprovador, coordenador);

            if (!aprovador.getStatusVinculo().equals(StatusVinculo.APROVADO_COORDENADOR)) {
                throw new IllegalArgumentException("Coordenador ainda não aprovado não pode aprovar outro.");
            }
            coordenador.setStatusVinculo(StatusVinculo.APROVADO_COORDENADOR);
            usuarioRepository.save(coordenador);
            return;
        }

        Empresa empresa = empresaRepository.findByEmailResponsavel(emailAutenticado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!empresa.getId().equals(coordenador.getEmpresa().getId())) {
            throw new IllegalArgumentException("Empresa não pode aprovar coordenador de outra empresa");
        }
        coordenador.setStatusVinculo(StatusVinculo.APROVADO_COORDENADOR);
        usuarioRepository.save(coordenador);
    }

    @Override
    public void rejeitarVinculoCoordenador(Long id, String email) {
        Usuario aprovador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Usuario coordenador = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        validarMesmoContextoEmpresa(aprovador, coordenador);
        coordenador.setStatusVinculo(StatusVinculo.REJEITADO_COORDENADOR);
        usuarioRepository.save(coordenador);
    }

    @Override
    public void removerVinculoCoordenador(Long id, String email) {
        Usuario aprovador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Usuario coordenador = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        validarMesmoContextoEmpresa(aprovador, coordenador);
        coordenador.setStatusVinculo(StatusVinculo.DESLIGADO_COORDENADOR);
        usuarioRepository.save(coordenador);
    }

    @Override
    public List<UsuarioResponseDTO> listarColaboradoresPendentes(Long empresaId) {
        List<Usuario> pendentes = usuarioRepository.findByEmpresaIdAndTipoUsuarioAndStatusVinculo(
                empresaId,
                TipoUsuario.COLABORADOR,
                StatusVinculo.PENDENTE_COLABORADOR
        );

        return pendentes.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<UsuarioResponseDTO> listarColaboradoresAprovados(Long empresaId) {
        List<Usuario> usuarios = usuarioRepository.findByEmpresaIdAndTipoUsuarioAndStatusVinculo(
                empresaId, TipoUsuario.COLABORADOR, StatusVinculo.APROVADO_COLABORADOR
        );
        return usuarios.stream().map(this::toResponseDTO).toList();
    }

    public List<UsuarioResponseDTO> listarTodosColaboradores(Long empresaId) {
        return usuarioRepository.findByEmpresaIdAndTipoUsuario(empresaId, TipoUsuario.COLABORADOR)
                .stream().map(this::toResponseDTO).toList();
    }

    @Override
    public List<UsuarioResponseDTO> listarCoordenadoresPorEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        List<Usuario> coordenadores = usuarioRepository.findByEmpresaAndTipoUsuario(empresa, TipoUsuario.COORDENADOR);

        return coordenadores.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public void deletarUsuario(Long idUsuario, String emailCoordenador) {
        Usuario coordenador = usuarioRepository.findByEmail(emailCoordenador)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        Usuario usuarioParaDeletar = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!Objects.equals(coordenador.getEmpresa().getId(), usuarioParaDeletar.getEmpresa().getId())) {
            throw new SecurityException("Você não tem permissão para deletar este usuário.");
        }

        usuarioRepository.deleteById(idUsuario);
    }

    public void redefinirSenha(Long id, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    @Override
    public DashboardDTO gerarDashboard(Long empresaId) {
        long total = usuarioRepository.countByEmpresaIdAndTipoUsuario(empresaId, TipoUsuario.COLABORADOR);
        long aprovados = usuarioRepository.countByEmpresaIdAndTipoUsuarioAndStatusVinculo(empresaId, TipoUsuario.COLABORADOR, StatusVinculo.APROVADO_COLABORADOR);
        long pendentes = usuarioRepository.countByEmpresaIdAndTipoUsuarioAndStatusVinculo(empresaId, TipoUsuario.COLABORADOR, StatusVinculo.PENDENTE_COLABORADOR);
        long rejeitados = usuarioRepository.countByEmpresaIdAndTipoUsuarioAndStatusVinculo(empresaId, TipoUsuario.COLABORADOR, StatusVinculo.REJEITADO_COLABORADOR);

        return new DashboardDTO(total, aprovados, pendentes, rejeitados);
    }
}