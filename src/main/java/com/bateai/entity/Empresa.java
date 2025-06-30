package com.bateai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String cnpj;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Email
    private String emailResponsavel;

    @Column
    private boolean emailConfirmado = false;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nomeFantasia;

    @Column
    private String razaoSocial;

    @Column
    private String endereco;

    @OneToMany(mappedBy = "empresa")
    private List<Usuario> usuarios;
}