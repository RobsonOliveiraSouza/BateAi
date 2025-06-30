package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
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
    public Usuario cadastrarCoordenador(CadastroCoordenadorDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Empresa empresa = empresaRepository.findByCnpj(dto.getCnpjEmpresa())
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

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario cadastrarColaborador(CadastroColaboradorDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Empresa empresa = empresaRepository.findByCnpj(dto.getCnpjEmpresa())
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
        usuario.setVinculoAprovado(false); // precisa da aprovação

        return usuarioRepository.save(usuario);
    }

    @Override
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}