package br.com.involvesfullstack.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alerta {

	private String pontoDeVenda;
	private String descricao;
	private String produto;
	private String categoria;
	private Integer flTipo;
	private Integer margem;
	
}
