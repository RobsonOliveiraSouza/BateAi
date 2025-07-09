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
        usuario.setStatusVinculo(StatusVinculo.APROVADO);

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
        usuario.setStatusVinculo(StatusVinculo.PENDENTE);

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
    public void aprovarVinculo(Long idColaborador, String coordenadorEmail) {
        Usuario coordenador = usuarioRepository.findByEmail(coordenadorEmail)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        if (coordenador.getTipoUsuario() != TipoUsuario.COORDENADOR) {
            throw new SecurityException("Apenas coordenadores podem aprovar colaboradores");
        }

        Usuario colaborador = usuarioRepository.findById(idColaborador)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado"));

        if (!colaborador.getEmpresa().getId().equals(coordenador.getEmpresa().getId())) {
            throw new SecurityException("Você só pode aprovar colaboradores da sua empresa");
        }

        colaborador.setStatusVinculo(StatusVinculo.APROVADO);
        usuarioRepository.save(colaborador);
    }


    @Override
    public void rejeitarVinculo(Long idColaborador, String coordenadorEmail) {
        Usuario coordenador = usuarioRepository.findByEmail(coordenadorEmail)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        if (coordenador.getTipoUsuario() != TipoUsuario.COORDENADOR) {
            throw new SecurityException("Apenas coordenadores podem rejeitar colaboradores.");
        }

        Usuario colaborador = usuarioRepository.findById(idColaborador)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado."));

        if (!colaborador.getEmpresa().getId().equals(coordenador.getEmpresa().getId())) {
            throw new SecurityException("Você só pode rejeitar colaboradores da sua empresa.");
        }

        colaborador.setStatusVinculo(StatusVinculo.REJEITADO);
        usuarioRepository.save(colaborador);
    }

    @Override
    public void removerVinculo(Long colaboradorId, String emailCoordenador) {
        Usuario coordenador = usuarioRepository.findByEmail(emailCoordenador)
                .orElseThrow(() -> new IllegalArgumentException("Coordenador não encontrado"));

        Usuario colaborador = usuarioRepository.findById(colaboradorId)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado"));

        if (!Objects.equals(coordenador.getEmpresa().getId(), colaborador.getEmpresa().getId())) {
            throw new SecurityException("Você não pode remover vínculo de colaborador de outra empresa.");
        }

        if (colaborador.getTipoUsuario() != TipoUsuario.COLABORADOR) {
            throw new IllegalArgumentException("Só é possível remover vínculo de COLABORADORES.");
        }

        colaborador.setStatusVinculo(StatusVinculo.DESLIGADO);
        usuarioRepository.save(colaborador);
    }


    @Override
    public List<UsuarioResponseDTO> listarColaboradoresPendentes(Long empresaId) {
        List<Usuario> pendentes = usuarioRepository.findByEmpresaIdAndTipoUsuarioAndStatusVinculo(
                empresaId,
                TipoUsuario.COLABORADOR,
                StatusVinculo.PENDENTE
        );

        return pendentes.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<UsuarioResponseDTO> listarColaboradoresAprovados(Long empresaId) {
        List<Usuario> usuarios = usuarioRepository.findByEmpresaIdAndTipoUsuarioAndStatusVinculo(
                empresaId, TipoUsuario.COLABORADOR, StatusVinculo.APROVADO
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
        long aprovados = usuarioRepository.countByEmpresaIdAndTipoUsuarioAndStatusVinculo(empresaId, TipoUsuario.COLABORADOR, StatusVinculo.APROVADO);
        long pendentes = usuarioRepository.countByEmpresaIdAndTipoUsuarioAndStatusVinculo(empresaId, TipoUsuario.COLABORADOR, StatusVinculo.PENDENTE);
        long rejeitados = usuarioRepository.countByEmpresaIdAndTipoUsuarioAndStatusVinculo(empresaId, TipoUsuario.COLABORADOR, StatusVinculo.REJEITADO);

        return new DashboardDTO(total, aprovados, pendentes, rejeitados);
    }
}