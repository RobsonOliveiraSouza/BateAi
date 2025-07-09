package com.bateai.controller;

import com.bateai.dto.*;
import com.bateai.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar-coordenador")
    public ResponseEntity<UsuarioResponseDTO> cadastrarCoordenador(@RequestBody CadastroCoordenadorDTO dto) {
        UsuarioResponseDTO response = usuarioService.cadastrarCoordenador(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar-colaborador")
    public ResponseEntity<UsuarioResponseDTO> cadastrarColaborador(@RequestBody CadastroColaboradorDTO dto) {
        UsuarioResponseDTO response = usuarioService.cadastrarColaborador(dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id, Authentication authentication) {
        usuarioService.deletarUsuario(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/usuarios/redefinir-senha/{id}")
    public ResponseEntity<?> redefinirSenha(@PathVariable Long id, @RequestBody NovaSenhaDTO dto) {
        usuarioService.redefinirSenha(id, dto.getNovaSenha());
        return ResponseEntity.ok("Senha redefinida com sucesso");
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @PutMapping("/aprovar-vinculo/{idColaborador}")
    public ResponseEntity<Void> aprovarVinculo(@PathVariable Long idColaborador, Authentication authentication) {
        usuarioService.aprovarVinculo(idColaborador, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Void> rejeitarVinculo(@PathVariable Long id, Authentication authentication) {
        usuarioService.rejeitarVinculo(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @PutMapping("/{id}/remover-vinculo")
    public ResponseEntity<Void> removerVinculo(@PathVariable Long id, Authentication authentication) {
        usuarioService.removerVinculo(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/pendentes")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPendentes(@RequestParam Long empresaId) {
        List<UsuarioResponseDTO> pendentes = usuarioService.listarColaboradoresPendentes(empresaId);
        return ResponseEntity.ok(pendentes);
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/aprovados")
    public ResponseEntity<List<UsuarioResponseDTO>> listarColaboradoresAprovados(@RequestParam Long empresaId) {
        return ResponseEntity.ok(usuarioService.listarColaboradoresAprovados(empresaId));
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/colaboradores")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodosColaboradores(@RequestParam Long empresaId) {
        return ResponseEntity.ok(usuarioService.listarTodosColaboradores(empresaId));
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/coordenadores")
    public ResponseEntity<List<UsuarioResponseDTO>> listarCoordenadores(@RequestParam Long empresaId) {
        List<UsuarioResponseDTO> coordenadores = usuarioService.listarCoordenadoresPorEmpresa(empresaId);
        return ResponseEntity.ok(coordenadores);
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> dashboard(@RequestParam Long empresaId) {
        return ResponseEntity.ok(usuarioService.gerarDashboard(empresaId));
    }
}