package com.bateai.entity;

import com.bateai.entity.enums.TipoRegistro;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registros_ponto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O colaborador precisa existir!")
    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Usuario colaborador;

    @NotNull(message = "A empresa precisa existir!")
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @NotNull(message = "A data e hora são obrigatórias")
    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de registro é obrigatório")
    @Column(name = "tipo_registro", nullable = false)
    private TipoRegistro tipoRegistro;

    @NotBlank(message = "A localização é obrigatória")
    @Column(nullable = false)
    private String localizacao;
}