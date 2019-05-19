package br.com.involvesfullstack.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pesquisa {
	private Integer id;
	private String rotulo;
	private String notificante;
	@JsonProperty("ponto_de_venda")
	private String pontoDeVenda;
	private String produto;
	@JsonProperty("preco_estipulado")
	private Integer precoEstipulado;
	private String categoria;
	@JsonProperty("participacao_estipulada")
	private Integer participacaoEstipulada;
	private List<Resposta> respostas;
}
