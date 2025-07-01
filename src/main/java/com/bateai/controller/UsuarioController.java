package com.bateai.controller;

import com.bateai.dto.CadastroCoordenadorDTO;
import com.bateai.dto.CadastroColaboradorDTO;
import com.bateai.dto.DashboardDTO;
import com.bateai.dto.UsuarioResponseDTO;
import com.bateai.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/aprovar-vinculo/{idColaborador}")
    public ResponseEntity<Void> aprovarVinculo(@PathVariable Long idColaborador) {
        usuarioService.aprovarVinculo(idColaborador);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPendentes(@RequestParam Long empresaId) {
        List<UsuarioResponseDTO> pendentes = usuarioService.listarColaboradoresPendentes(empresaId);
        return ResponseEntity.ok(pendentes);
    }

    @GetMapping("/aprovados")
    public ResponseEntity<List<UsuarioResponseDTO>> listarColaboradoresAprovados(@RequestParam Long empresaId) {
        return ResponseEntity.ok(usuarioService.listarColaboradoresAprovados(empresaId));
    }

    @GetMapping("/colaboradores")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodosColaboradores(@RequestParam Long empresaId) {
        return ResponseEntity.ok(usuarioService.listarTodosColaboradores(empresaId));
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Void> rejeitarVinculo(@PathVariable Long id) {
        usuarioService.rejeitarVinculo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> dashboard(@RequestParam Long empresaId) {
        return ResponseEntity.ok(usuarioService.gerarDashboard(empresaId));
    }
}