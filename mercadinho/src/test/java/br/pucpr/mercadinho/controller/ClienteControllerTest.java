package br.pucpr.mercadinho.controller;

import br.pucpr.mercadinho.model.Cliente;
import br.pucpr.mercadinho.model.security.JwtService;
import br.pucpr.mercadinho.model.security.UserDetailsServiceImpl;
import br.pucpr.mercadinho.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Foca apenas no ClienteController
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula as requisições HTTP

    // Mocks de todas as dependências do controller e da segurança
    @MockBean
    private ClienteService clienteService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService; // Necessário para a segurança

    @Test
    @WithMockUser(roles = "ADMIN") // Simula um usuário logado com ROLE_ADMIN
    public void testFindAllEndpoint() throws Exception {
        // 1. Cenário
        Cliente cliente = new Cliente();
        cliente.setEmail("email@email.com");
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);

        // 2. Mock
        when(clienteService.findAll()).thenReturn(clientes);

        // 3. Execução e Verificação
        // Testa o endpoint correto: /api/clientes
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].email").value("email@email.com"));
    }
}