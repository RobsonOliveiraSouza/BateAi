package com.bateai.service;

import com.bateai.dto.EmpresaDTO;
import com.bateai.dto.EmpresaResponseDTO;
import com.bateai.entity.Empresa;
import com.bateai.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImp implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmpresaServiceImp(EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public EmpresaResponseDTO cadastrarEmpresa(EmpresaDTO dto) {
        if (empresaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
            throw new IllegalArgumentException("Empresa com CNPJ já cadastrado.");
        }

        Empresa empresa = new Empresa();
        empresa.setCnpj(dto.getCnpj());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setEndereco(dto.getEndereco());
        empresa.setEmailResponsavel(dto.getEmailResponsavel());
        empresa.setSenhaResponsavel(passwordEncoder.encode(dto.getSenhaResponsavel()));
        empresa.setEmailConfirmado(false);

        Empresa salva = empresaRepository.save(empresa);
        return toResponseDTO(salva);
    }

    @Override
    public void redefinirSenha(Long empresaId, String novaSenha) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        empresa.setSenhaResponsavel(passwordEncoder.encode(novaSenha));
        empresaRepository.save(empresa);
    }

    private EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        return new EmpresaResponseDTO(
                empresa.getId(),
                empresa.getCnpj(),
                empresa.getNomeFantasia(),
                empresa.getRazaoSocial(),
                empresa.getEndereco(),
                empresa.getEmailResponsavel(),
                empresa.isEmailConfirmado()
        );
    }

}