package br.pucpr.mercadinho.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class PedidoRequest {

    @NotEmpty(message = "O pedido não pode estar vazio")
    @Valid // <-- Importante: Isso força a validação dos itens *dentro* da lista
    private List<ItemPedidoRequest> itens;
}