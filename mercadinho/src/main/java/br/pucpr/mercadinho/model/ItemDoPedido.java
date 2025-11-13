package br.pucpr.mercadinho.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TAB_ITEM_PEDIDO")
public class ItemDoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relação de Integração: A qual pedido este item pertence?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @JsonIgnore // Evita loop infinito no JSON
    private Pedido pedido;

    // Relação de Integração: Qual produto foi comprado?
    @ManyToOne(fetch = FetchType.EAGER) // Queremos saber qual é o produto
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario; // Preço no momento da compra
}