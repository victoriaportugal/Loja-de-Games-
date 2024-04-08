package com.generation.loja_games.loja_games.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.loja_games.loja_games.model.Categoria;
import com.generation.loja_games.loja_games.model.Produtos;
import com.generation.loja_games.loja_games.repository.CategoriaRepository;
import com.generation.loja_games.loja_games.repository.ProdutosRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")

public class ProdutosController {
	@Autowired
	private ProdutosRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produtos>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produtos> getById(@PathVariable Long id) {
		return produtoRepository.findById(id).map(produto -> ResponseEntity.status(HttpStatus.OK).body(produto))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/preco/{preco}")
	public ResponseEntity<List<Produtos>> getByPreco(@PathVariable BigDecimal preco){
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAllByPreco(preco));
	
	}

	@PostMapping
	public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produtos) {
		if(categoriaRepository.existsById(produtos.getCategoria().getId())) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produtos));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping
	public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produtos) {
	    // Verificar se a categoria do produto existe
	    Optional<Categoria> categoriaOptional = categoriaRepository.findById(produtos.getCategoria().getId());
	    if (categoriaOptional.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }

	    Optional<Produtos> produtoBanco = produtoRepository.findById(produtos.getId());
	    if (produtoBanco.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }

	    Produtos produtoAtualizado = produtoRepository.save(produtos);
	    return ResponseEntity.status(HttpStatus.OK).body(produtoAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<Produtos> produtoBanco = produtoRepository.findById(id);
		if (produtoBanco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		produtoRepository.delete(produtoBanco.get());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
