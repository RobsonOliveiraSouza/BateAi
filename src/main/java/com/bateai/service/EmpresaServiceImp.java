package com.bateai.service;

import com.bateai.dto.EmpresaDTO;
import com.bateai.entity.Empresa;
import com.bateai.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImp implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaServiceImp(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public Empresa cadastrarEmpresa(EmpresaDTO dto) {
        if (empresaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
            throw new IllegalArgumentException("Empresa com CNPJ já cadastrado.");
        }

        Empresa empresa = new Empresa();
        empresa.setCnpj(dto.getCnpj());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setEndereco(dto.getEndereco());
        empresa.setEmailResponsavel(dto.getEmailResponsavel());
        empresa.setEmailConfirmado(false);

        return empresaRepository.save(empresa);
    }
}