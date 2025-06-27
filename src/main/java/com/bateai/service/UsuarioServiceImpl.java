package com.bateai.service;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroFuncionarioDTO;
import com.bateai.entity.Usuario;
import com.bateai.enums.TipoUsuario;
import com.bateai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario cadastrarCoordenador(CadastroCoordenadorDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTipoUsuario(TipoUsuario.COORDENADOR);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario cadastrarFuncionario(CadastroFuncionarioDTO dto) {
        Optional<Usuario> coordenador = usuarioRepository.findById(dto.getCoordenadorId());

        if (coordenador.isEmpty() || coordenador.get().getTipoUsuario() != TipoUsuario.COORDENADOR) {
            throw new IllegalArgumentException("Coordenador inválido");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTipoUsuario(TipoUsuario.FUNCIONARIO);

        return usuarioRepository.save(usuario);
    }
}