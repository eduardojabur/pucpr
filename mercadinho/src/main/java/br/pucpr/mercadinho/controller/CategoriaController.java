package br.pucpr.mercadinho.controller;

import br.pucpr.mercadinho.model.Categoria;
import br.pucpr.mercadinho.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "2. Categorias", description = "Endpoints para gerenciar as categorias de produtos")
@SecurityRequirement(name = "bearerAuth") // Exige token em todos os endpoints
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Listar todas as categorias")
    @GetMapping
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @Operation(summary = "Buscar categoria por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @Operation(summary = "Criar nova categoria (ADMIN)")
    @PostMapping
    @Secured("ROLE_ADMIN") // Trava o endpoint
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.save(categoria));
    }

    @Operation(summary = "Atualizar categoria (ADMIN)")
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoria.setId(id); // Garante que está atualizando o ID correto
        return ResponseEntity.ok(categoriaService.save(categoria));
    }

    @Operation(summary = "Excluir categoria (ADMIN)")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}