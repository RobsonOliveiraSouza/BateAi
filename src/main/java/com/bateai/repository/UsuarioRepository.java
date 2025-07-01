package com.bateai.repository;

import com.bateai.entity.Usuario;
import com.bateai.entity.enums.StatusVinculo;
import com.bateai.entity.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    List<Usuario> findByEmpresaIdAndTipoUsuarioAndStatusVinculo(Long empresaId, TipoUsuario tipoUsuario, StatusVinculo statusVinculo);
}