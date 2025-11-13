package br.pucpr.mercadinho.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemPedidoRequest {

    @NotNull
    @Min(value = 1, message = "O ID do produto é obrigatório")
    private Long produtoId;

    @NotNull
    @Min(value = 1, message = "A quantidade deve ser de no mínimo 1")
    private Integer quantidade;
}