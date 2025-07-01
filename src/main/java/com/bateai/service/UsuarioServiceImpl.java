package com.bateai.service;

import com.bateai.dto.*;
import com.bateai.entity.Empresa;
import com.bateai.entity.Usuario;
import com.bateai.entity.enums.StatusVinculo;
import com.bateai.entity.enums.TipoUsuario;
import com.bateai.repository.EmpresaRepository;
import com.bateai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
    }

    @Override
    public UsuarioResponseDTO cadastrarCoordenador(CadastroCoordenadorDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
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
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
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
    public void aprovarVinculo(Long idColaborador) {
        Usuario colaborador = usuarioRepository.findById(idColaborador)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado"));

        if (colaborador.getTipoUsuario() != TipoUsuario.COLABORADOR) {
            throw new IllegalArgumentException("Somente colaboradores podem ter o vínculo aprovado");
        }

        colaborador.setStatusVinculo(StatusVinculo.APROVADO);
        usuarioRepository.save(colaborador);
    }

    public void rejeitarVinculo(Long idColaborador) {
        Usuario colaborador = usuarioRepository.findById(idColaborador)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado"));

        if (colaborador.getTipoUsuario() != TipoUsuario.COLABORADOR) {
            throw new IllegalArgumentException("Somente colaboradores podem ter o vínculo rejeitado");
        }

        colaborador.setStatusVinculo(StatusVinculo.REJEITADO);
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
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
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