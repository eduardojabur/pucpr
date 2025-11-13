package br.pucpr.mercadinho.service;

import br.pucpr.mercadinho.dto.RegisterRequest;
import br.pucpr.mercadinho.model.Cliente;
import br.pucpr.mercadinho.model.security.Role;
import br.pucpr.mercadinho.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder; // Injeta o encriptador

    // Injeção de dependência via construtor
    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Lógica de negócio para registrar um novo cliente.
     * Recebe o DTO, não a entidade.
     */
    public Cliente register(RegisterRequest request) {
        // Regra de Negócio: Verifica se o email já existe
        if (clienteRepository.findByEmail(request.getEmail()).isPresent()) {
            // Em um projeto real, usaríamos nossa exceção customizada
            throw new IllegalStateException("Email já cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());

        // Regra de Negócio: Encripta a senha antes de salvar
        cliente.setSenha(passwordEncoder.encode(request.getSenha()));

        // Regra de Negócio: Define o Role padrão
        cliente.setRole(Role.ROLE_CLIENTE);

        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Corrigido: O ID do Cliente é Long, não Integer
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
}