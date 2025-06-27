package com.bateai.controller;

import com.bateai.dto.RegistroPontoDTO;
import com.bateai.entity.RegistroPonto;
import com.bateai.service.RegistroPontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ponto")
public class RegistroPontoController {

    @Autowired
    private RegistroPontoService registroPontoService;

    @PostMapping("/registrar")
    public ResponseEntity<RegistroPonto> registrarPonto(@RequestBody RegistroPontoDTO dto) {
        RegistroPonto ponto = registroPontoService.registrarPonto(dto);
        return ResponseEntity.ok(ponto);
    }
}