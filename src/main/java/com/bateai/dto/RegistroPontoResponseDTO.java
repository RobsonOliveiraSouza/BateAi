package com.bateai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RegistroPontoResponseDTO {

    private Long id;
    private LocalDateTime dataHora;
    private String tipoRegistro;
    private String localizacao;
    private UsuarioResumoDTO colaborador;
    private EmpresaResumoDTO empresa;
}
