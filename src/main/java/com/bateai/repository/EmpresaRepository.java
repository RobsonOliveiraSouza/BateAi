package com.bateai.repository;

import com.bateai.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByCnpj(String cnpj);
    Optional<Empresa> findByCnpj(String cnpj);
    Optional<Empresa> findByEmailResponsavel(String email);
    List<Empresa> findByNomeFantasiaContainingIgnoreCaseOrCnpjContaining(String nome, String cnpj);

}
