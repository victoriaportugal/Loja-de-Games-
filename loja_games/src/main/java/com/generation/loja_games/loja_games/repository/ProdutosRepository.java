package com.generation.loja_games.loja_games.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.loja_games.loja_games.model.Produtos;

public interface ProdutosRepository extends JpaRepository<Produtos, Long>{
	public List<Produtos> findAllByPreco(BigDecimal preco);
}
