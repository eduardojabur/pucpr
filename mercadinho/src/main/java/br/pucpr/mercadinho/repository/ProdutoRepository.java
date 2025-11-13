package br.pucpr.mercadinho.repository;

import br.pucpr.mercadinho.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // O JpaRepository já fornece todos os métodos padrão
    // como findAll(), findById(), save(), deleteById(), etc.
}