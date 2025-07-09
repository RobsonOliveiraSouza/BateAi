package com.bateai.controller;

import com.bateai.dto.RegistroPontoDTO;
import com.bateai.dto.RegistroPontoResponseDTO;
import com.bateai.entity.RegistroPonto;
import com.bateai.service.RegistroPontoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ponto")
public class RegistroPontoController {

    @Autowired
    private RegistroPontoServiceImp registroPontoService;

    @PostMapping("/registrar")
    public ResponseEntity<RegistroPontoResponseDTO> registrarPonto(@RequestBody RegistroPontoDTO dto) {
        return ResponseEntity.ok(registroPontoService.registrarPonto(dto));
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/colaborador/{id}")
    public ResponseEntity<List<RegistroPontoResponseDTO>> listarPontosPorColaborador(
            @PathVariable Long id,
            Authentication authentication) {
        List<RegistroPontoResponseDTO> pontos = registroPontoService.listarPontosPorColaborador(id, authentication.getName());
        return ResponseEntity.ok(pontos);
    }

    @PreAuthorize("hasAnyRole('COLABORADOR', 'COORDENADOR')")
    @GetMapping("/historico")
    public ResponseEntity<List<RegistroPontoResponseDTO>> listarMeusPontos(Authentication authentication) {
        String email = authentication.getName();
        List<RegistroPontoResponseDTO> pontos = registroPontoService.listarMeusPontos(email);
        return ResponseEntity.ok(pontos);
    }

    @PreAuthorize("hasRole('COORDENADOR')")
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<RegistroPontoResponseDTO>> listarPontosPorEmpresa(
            @PathVariable Long empresaId,
            Authentication authentication) {
        List<RegistroPontoResponseDTO> pontos = registroPontoService.listarPontosPorEmpresa(empresaId, authentication.getName());
        return ResponseEntity.ok(pontos);
    }
}