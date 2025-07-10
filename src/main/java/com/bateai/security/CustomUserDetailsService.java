package com.bateai.security;

import com.bateai.entity.Empresa;
import com.bateai.entity.Usuario;
import com.bateai.repository.EmpresaRepository;
import com.bateai.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getSenha())
                    .roles(usuario.getTipoUsuario().name())
                    .build();
        }

        Optional<Empresa> empresaOpt = empresaRepository.findByEmailResponsavel(email);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            return User.builder()
                    .username(empresa.getEmailResponsavel())
                    .password(empresa.getSenhaResponsavel())
                    .roles("EMPRESA")
                    .build();
        }

        throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
    }
}
