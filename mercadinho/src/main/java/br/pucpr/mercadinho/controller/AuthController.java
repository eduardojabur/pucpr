package br.pucpr.mercadinho.controller;

import br.pucpr.mercadinho.dto.AuthRequest;
import br.pucpr.mercadinho.dto.AuthResponse;
import br.pucpr.mercadinho.dto.RegisterRequest;
import br.pucpr.mercadinho.model.Cliente;
import br.pucpr.mercadinho.model.security.AuthService;
import br.pucpr.mercadinho.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "1. Autenticação", description = "Endpoints para login e registro de clientes")
public class AuthController {

    private final AuthService authService;
    private final ClienteService clienteService;

    public AuthController(AuthService authService, ClienteService clienteService) {
        this.authService = authService;
        this.clienteService = clienteService;
    }

    @Operation(summary = "Autenticar cliente", description = "Realiza o login e retorna um token JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registrar novo cliente", description = "Cria um novo cliente com o role ROLE_CLIENTE")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
        clienteService.register(request);
        // Retorna 201 Created sem corpo
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}