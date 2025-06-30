package com.bateai.dto;

import com.bateai.entity.enums.TipoRegistro;
import lombok.Data;

@Data
public class RegistroPontoDTO {
    private Long colaboradorId;
    private TipoRegistro tipoRegistro;
    private String localizacao;
}