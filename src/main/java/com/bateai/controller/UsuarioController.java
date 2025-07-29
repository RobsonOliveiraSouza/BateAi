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

    @PreAuthorize("hasRole('COORDENADOR')" )
    @PutMapping("/{id}/aprovar-colaborador")
    public ResponseEntity<String> aprovarVinculoColaborador(@PathVariable Long id, Authentication auth) {
        usuarioService.aprovarVinculoColaborador(id, auth.getName());
        return ResponseEntity.ok("Colaborador aprovado com sucesso");
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @PutMapping("/{id}/rejeitar-colaborador")
    public ResponseEntity<String> rejeitarVinculoColaborador(@PathVariable Long id, Authentication auth) {
        usuarioService.rejeitarVinculoColaborador(id, auth.getName());
        return ResponseEntity.ok("Colaborador rejeitado com sucesso");
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @PutMapping("/{id}/remover-colaborador")
    public ResponseEntity<String> removerVinculoColaborador(@PathVariable Long id, Authentication auth) {
        usuarioService.removerVinculoColaborador(id, auth.getName());
        return ResponseEntity.ok("Vínculo do colaborador removido com sucesso");
    }

    @PreAuthorize("hasRole('COORDENADOR') or hasRole('EMPRESA')")
    @PutMapping("/{id}/aprovar-coordenador")
    public ResponseEntity<String> aprovarVinculoCoordenador(@PathVariable Long id, Authentication auth) {
        usuarioService.aprovarVinculoCoordenador(id, auth.getName());
        return ResponseEntity.ok("Coordenador aprovado com sucesso");
    }

    @PreAuthorize("hasRole('COORDENADOR') or hasRole('EMPRESA')")
    @PutMapping("/{id}/rejeitar-coordenador")
    public ResponseEntity<String> rejeitarVinculoCoordenador(@PathVariable Long id, Authentication auth) {
        usuarioService.rejeitarVinculoCoordenador(id, auth.getName());
        return ResponseEntity.ok("Coordenador rejeitado com sucesso");
    }

    @PreAuthorize("hasRole('COORDENADOR') or hasRole('EMPRESA')")
    @PutMapping("/{id}/remover-coordenador")
    public ResponseEntity<String> removerVinculoCoordenador(@PathVariable Long id, Authentication auth) {
        usuarioService.removerVinculoCoordenador(id, auth.getName());
        return ResponseEntity.ok("Vínculo do coordenador removido com sucesso");
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

    @PreAuthorize("hasRole('COORDENADOR') or hasRole('EMPRESA')")
    @GetMapping("/colaboradores")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodosColaboradores(@RequestParam Long empresaId) {
        return ResponseEntity.ok(usuarioService.listarTodosColaboradores(empresaId));
    }

    @PreAuthorize("hasRole('COORDENADOR') or hasRole('EMPRESA')")
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