package br.pucpr.mercadinho.controller;

import br.pucpr.mercadinho.dto.ProdutoRequest;
import br.pucpr.mercadinho.model.Produto;
import br.pucpr.mercadinho.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "3. Produtos", description = "Endpoints para gerenciar os produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ModelMapper modelMapper; // Usado para conversão

    public ProdutoController(ProdutoService produtoService, ModelMapper modelMapper) {
        this.produtoService = produtoService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Listar todos os produtos (Público)")
    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @Operation(summary = "Buscar produto por ID (Público)")
    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.findById(id));
    }

    @Operation(summary = "Criar novo produto (ADMIN)")
    @PostMapping
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearerAuth") // Informa ao Swagger que este é protegido
    public ResponseEntity<Produto> create(@Valid @RequestBody ProdutoRequest request) {
        // Usamos o service que já sabe como tratar o DTO
        Produto produto = produtoService.create(request);
        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Atualizar produto (ADMIN)")
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Produto> update(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        Produto produto = produtoService.update(id, request);
        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Excluir produto (ADMIN)")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}