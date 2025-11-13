package br.pucpr.mercadinho.service;

import br.pucpr.mercadinho.dto.ItemPedidoRequest;
import br.pucpr.mercadinho.dto.PedidoRequest;
import br.pucpr.mercadinho.model.*;
import br.pucpr.mercadinho.repository.ClienteRepository;
import br.pucpr.mercadinho.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoService produtoService; // Usamos o ProdutoService para pegar os produtos

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoService = produtoService;
    }

    @Transactional // Garante que ou tudo (pedido, itens, estoque) funciona, ou nada é salvo
    public Pedido create(PedidoRequest request, String emailCliente) {

        // 1. Encontra o Cliente que está fazendo o pedido
        Cliente cliente = clienteRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PROCESSANDO);

        BigDecimal valorTotal = BigDecimal.ZERO;
        List<ItemDoPedido> itensDoPedido = new ArrayList<>();

        // 2. Processa cada item do DTO
        for (ItemPedidoRequest itemRequest : request.getItens()) {
            Produto produto = produtoService.findById(itemRequest.getProdutoId());

            // 3. Regra de Negócio: Verifica o estoque
            if (produto.getEstoque() < itemRequest.getQuantidade()) {
                throw new IllegalStateException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            // 4. Cria o ItemDoPedido
            ItemDoPedido item = new ItemDoPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemRequest.getQuantidade());
            item.setPrecoUnitario(produto.getPreco()); // Guarda o preço daquele momento
            itensDoPedido.add(item);

            // 5. Calcula o valor total
            valorTotal = valorTotal.add(
                    produto.getPreco().multiply(BigDecimal.valueOf(itemRequest.getQuantidade()))
            );

            // 6. Regra de Negócio: Abate do estoque (Opcional, mas recomendado)
            // produto.setEstoque(produto.getEstoque() - itemRequest.getQuantidade());
            // produtoService.save(produto); // Salvaria o estoque atualizado
        }

        pedido.setItens(itensDoPedido);
        pedido.setValorTotal(valorTotal);

        // 7. Salva o Pedido (e os ItensDoPedido, graças ao CascadeType.ALL)
        return pedidoRepository.save(pedido);
    }

    /**
     * Busca todos os pedidos feitos por um cliente específico.
     */
    public List<Pedido> findMyOrders(String emailCliente) {
        Cliente cliente = clienteRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return pedidoRepository.findByClienteId(cliente.getId());
    }
}