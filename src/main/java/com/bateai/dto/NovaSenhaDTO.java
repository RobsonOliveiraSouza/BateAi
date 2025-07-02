package com.bateai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NovaSenhaDTO {
    @NotBlank
    private String novaSenha;
}
