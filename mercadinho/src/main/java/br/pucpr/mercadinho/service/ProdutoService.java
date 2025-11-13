package br.pucpr.mercadinho.service;

import br.pucpr.mercadinho.dto.ProdutoRequest;
import br.pucpr.mercadinho.model.Categoria;
import br.pucpr.mercadinho.model.Produto;
import br.pucpr.mercadinho.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService; // Integração

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    public Produto create(ProdutoRequest request) {
        // Regra de Negócio: Busca a categoria antes de salvar o produto
        Categoria categoria = categoriaService.findById(request.getCategoriaId());

        Produto produto = new Produto();
        produto.setNome(request.getNome());
        produto.setPreco(request.getPreco());
        produto.setEstoque(request.getEstoque());
        produto.setCategoria(categoria); // Seta a entidade Categoria

        return produtoRepository.save(produto);
    }

    public Produto update(Long id, ProdutoRequest request) {
        Produto produto = findById(id); // Reusa o método de busca
        Categoria categoria = categoriaService.findById(request.getCategoriaId());

        produto.setNome(request.getNome());
        produto.setPreco(request.getPreco());
        produto.setEstoque(request.getEstoque());
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void delete(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}