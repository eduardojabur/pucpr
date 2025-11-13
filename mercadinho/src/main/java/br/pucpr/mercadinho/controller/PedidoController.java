package br.pucpr.mercadinho.controller;

import br.pucpr.mercadinho.dto.PedidoRequest;
import br.pucpr.mercadinho.model.Pedido;
import br.pucpr.mercadinho.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "4. Pedidos", description = "Endpoints para clientes criarem e visualizarem seus pedidos")
@SecurityRequirement(name = "bearerAuth") // Todos os endpoints aqui exigem login
//@Secured("ROLE_CLIENTE") // Garante que só clientes acessam
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Criar um novo pedido", description = "Cria um pedido para o cliente autenticado")
    @PostMapping
    public ResponseEntity<Pedido> create(@Valid @RequestBody PedidoRequest request, Authentication authentication) {
        // O Spring Security injeta a 'Authentication' do usuário logado
        // O 'authentication.getName()' retorna o "username", que definimos como o email.
        String emailDoCliente = authentication.getName();

        Pedido pedido = pedidoService.create(request, emailDoCliente);
        return ResponseEntity.ok(pedido);
    }

    @Operation(summary = "Listar meus pedidos", description = "Lista todos os pedidos feitos pelo cliente autenticado")
    @GetMapping("/meus")
    public ResponseEntity<List<Pedido>> findMyOrders(Authentication authentication) {
        String emailDoCliente = authentication.getName();
        List<Pedido> pedidos = pedidoService.findMyOrders(emailDoCliente);
        return ResponseEntity.ok(pedidos);
    }
}