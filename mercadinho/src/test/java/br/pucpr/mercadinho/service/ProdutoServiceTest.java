package br.pucpr.mercadinho.service;

import br.pucpr.mercadinho.dto.ProdutoRequest;
import br.pucpr.mercadinho.model.Categoria;
import br.pucpr.mercadinho.model.Produto;
import br.pucpr.mercadinho.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    public void createDeveSalvarProdutoComCategoria() {
        // 1. Cenário (Arrange)
        ProdutoRequest request = new ProdutoRequest();
        request.setNome("Banana");
        request.setPreco(new BigDecimal("5.99"));
        request.setEstoque(100);
        request.setCategoriaId(1L);

        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        categoriaMock.setNome("Hortifruti");

        Produto produtoSalvoMock = new Produto();
        produtoSalvoMock.setId(1L);
        produtoSalvoMock.setNome("Banana");
        produtoSalvoMock.setCategoria(categoriaMock); // Importante

        // 2. Configuração dos Mocks (quando o service chamar...)
        // ...o categoriaService.findById(1L), retorne a categoriaMock
        when(categoriaService.findById(1L)).thenReturn(categoriaMock);

        // ...o produtoRepository.save(qualquerProduto), retorne o produtoSalvoMock
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvoMock);

        // 3. Execução (Act)
        Produto resultado = produtoService.create(request);

        // 4. Verificação (Assert)
        assertNotNull(resultado);
        assertEquals("Banana", resultado.getNome());
        assertEquals("Hortifruti", resultado.getCategoria().getNome());
    }
}