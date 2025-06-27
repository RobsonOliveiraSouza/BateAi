package com.bateai.repository;

import com.bateai.entity.RegistroPonto;
import com.bateai.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Long> {
    List<RegistroPonto> findByFuncionario(Usuario funcionario);

    List<RegistroPonto> findByFuncionarioAndDataHoraBetween(
            Usuario funcionario,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    RegistroPonto findTopByFuncionarioOrderByDataHoraDesc();
}