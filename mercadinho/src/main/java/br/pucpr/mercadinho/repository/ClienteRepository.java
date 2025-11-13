package br.pucpr.mercadinho.repository;

import br.pucpr.mercadinho.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Metodo obrigatório para o Spring Security (UserDetailsServiceImpl)
    // Ele permite buscar o cliente pelo email na hora do login.
    Optional<Cliente> findByEmail(String email);

}