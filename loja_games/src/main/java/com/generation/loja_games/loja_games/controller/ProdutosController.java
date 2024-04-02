package com.generation.loja_games.loja_games.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.loja_games.loja_games.model.Produtos;
import com.generation.loja_games.loja_games.repository.CategoriaRepository;
import com.generation.loja_games.loja_games.repository.ProdutosRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")

public class ProdutosController {
	@Autowired
	ProdutosRepository produtosRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List <Produtos>> get(){
		return ResponseEntity.ok(produtosRepository.findAll());
	}

	@PostMapping
	public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produtos){
		if(categoriaRepository.existsById(produtos.getCategoria().getId()))
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtosRepository.save(produtos));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Produto não existe!", null);
	}
	@PutMapping 
	public ResponseEntity <Produtos> put(@Valid @RequestBody Produtos produtos){
		if(produtosRepository.existsById(produtos.getId())) {
			//Put: Para fazer uma Requisição de Alteração de recursos
			
			if(categoriaRepository.existsById(produtos.getCategoria().getId()))
		return ResponseEntity.status(HttpStatus.OK)
						.body(produtosRepository.save(produtos));
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não exite!", null);
	}
	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
}

}
