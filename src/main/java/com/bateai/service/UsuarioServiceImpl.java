package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
import com.bateai.dto.UsuarioResponseDTO;
import com.bateai.dto.EmpresaResumoDTO;
import com.bateai.entity.Empresa;
import com.bateai.entity.Usuario;
import com.bateai.entity.enums.TipoUsuario;
import com.bateai.repository.EmpresaRepository;
import com.bateai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        usuario.setVinculoAprovado(true);

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
        usuario.setTipoUsuario(TipoUsuario.COLABORADOR);
        usuario.setEmpresa(empresa);
        usuario.setVinculoAprovado(false);

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
                empresaDTO
        );
    }

    @Override
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}