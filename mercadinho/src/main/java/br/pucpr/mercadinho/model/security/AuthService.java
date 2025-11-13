package br.pucpr.mercadinho.model.security;

import br.pucpr.mercadinho.dto.AuthRequest;
import br.pucpr.mercadinho.dto.AuthResponse;
import br.pucpr.mercadinho.model.Cliente;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // O Spring injeta os beans
    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse authenticate(AuthRequest request) {
        // 1. O AuthenticationManager usa o UserDetailsService para
        // buscar o usuário e verificar a senha.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Se a autenticação foi bem-sucedida, pegamos o usuário real
        var cliente = (Cliente) authentication.getPrincipal();

        // 3. Geramos o token JWT para este usuário
        String jwtToken = jwtService.generateToken(cliente);

        // 4. Retornamos a resposta
        return new AuthResponse(
                jwtToken,
                cliente.getEmail(),
                cliente.getRole().name() // Retorna o Role (ex: "ROLE_ADMIN")
        );
    }
}