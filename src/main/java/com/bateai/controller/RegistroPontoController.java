package com.bateai.controller;

import com.bateai.dto.RegistroPontoDTO;
import com.bateai.dto.RegistroPontoResponseDTO;
import com.bateai.entity.RegistroPonto;
import com.bateai.service.RegistroPontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ponto")
public class RegistroPontoController {

    @Autowired
    private RegistroPontoService registroPontoService;

    @PostMapping("/registrar")
    public ResponseEntity<RegistroPontoResponseDTO> registrarPonto(@RequestBody RegistroPontoDTO dto) {
        return ResponseEntity.ok(registroPontoService.registrarPonto(dto));
    }

    @GetMapping("/colaborador/{id}")
    public List<RegistroPonto> listarPorColaborador(@PathVariable Long id) {
        return registroPontoService.listarPorColaborador(id);
    }
}