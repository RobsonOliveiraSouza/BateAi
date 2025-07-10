package com.bateai.controller;

import com.bateai.dto.AuthenticationRequest;
import com.bateai.dto.AuthenticationResponse;
import com.bateai.dto.RefreshTokenRequest;
import com.bateai.entity.Empresa;
import com.bateai.repository.EmpresaRepository;
import com.bateai.repository.UsuarioRepository;
import com.bateai.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String accessToken = jwtUtil.generateToken(usuario);
        String refreshToken = jwtUtil.generateRefreshToken(usuario);

        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    @PostMapping("/login-empresa")
    public ResponseEntity<AuthenticationResponse> loginEmpresa(@RequestBody AuthenticationRequest request) {
        Empresa empresa = empresaRepository.findByEmailResponsavel(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        if (!passwordEncoder.matches(request.getSenha(), empresa.getSenhaResponsavel())) {
            throw new RuntimeException("Senha incorreta");
        }

        String accessToken = jwtUtil.generateTokenEmpresa(empresa);
        String refreshToken = jwtUtil.generateRefreshTokenEmpresa(empresa);

        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String email = jwtUtil.extractUsername(request.getRefreshToken());

        if (email == null || usuarioRepository.findByEmail(email).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var usuario = usuarioRepository.findByEmail(email).get();
        String newAccessToken = jwtUtil.generateToken(usuario);

        return ResponseEntity.ok(new AuthenticationResponse(newAccessToken, request.getRefreshToken()));
    }
}