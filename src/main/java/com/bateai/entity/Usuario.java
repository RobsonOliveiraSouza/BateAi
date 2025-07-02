package com.bateai.entity;

import com.bateai.entity.enums.StatusVinculo;
import com.bateai.entity.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "O email é obrigatório")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @JsonIgnore
    @Column(nullable = false)
    private String senha;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String cpf;

    @Column
    private String telefone;

    @Column
    private String setor;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de usuário é obrigatório")
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario; // COORDENADOR ou COLABORADOR

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVinculo statusVinculo;
}