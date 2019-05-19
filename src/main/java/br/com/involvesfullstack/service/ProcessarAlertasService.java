package br.com.involvesfullstack.service;

import static br.com.involvesfullstack.service.ImportadorPesquisasService.importarPesquisas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.involvesfullstack.gateway.AlertaGateway;
import br.com.involvesfullstack.model.Alerta;
import br.com.involvesfullstack.model.Pesquisa;
import br.com.involvesfullstack.model.Resposta;

@Service
public class ProcessarAlertasService {
	@Autowired
	private AlertaGateway gateway;

	private static final String QUAL_A_SITUACAO_DO_PRODUTO = "Qual a situação do produto?";
	private static final String QUAL_O_PRECO_DO_PRODUTO = "Qual o preço do produto?";
	private static final String QUAL_A_PARTICIPACAO = "%Share";
	private static final String PRODUTO_AUSENTE_NA_GONDOLA = "Produto ausente na gondola";

	public void processa() {
		List<Pesquisa> pesquisas = importarPesquisas();
		pesquisas.forEach(pesquisa ->
			verificaRespostasECriaAlertas(pesquisa, pesquisa.getRespostas())
		);
	}

	private void verificaRespostasECriaAlertas(final Pesquisa pesquisa, final List<Resposta> respostas) {
		pesquisa.getRespostas().forEach(resposta -> {
			Boolean naoOcorreuAlertaDeRuptura = !verificaECriaAlertaDeRuptura(pesquisa, resposta);
			if (naoOcorreuAlertaDeRuptura) {
				Boolean naoOcorreuAlertaDePreco = !verificaECriaAlertaDePreco(pesquisa, resposta);
				if (naoOcorreuAlertaDePreco) {
					verificaECriaAlertaDeParticipacao(pesquisa, resposta);
				}
			}
		});
	}

	private Boolean verificaECriaAlertaDeRuptura(final Pesquisa pesquisa, final Resposta resposta) {
		if (resposta.getPergunta().equals(QUAL_A_SITUACAO_DO_PRODUTO)
				&& resposta.getResposta().equals(PRODUTO_AUSENTE_NA_GONDOLA)) {
			criaAlertaESalvaNoBanco(pesquisa.getPontoDeVenda(), pesquisa.getProduto(), TipoAlerta.RUPTURA);
			return true;
		}
		return false;
	}

	private Boolean verificaECriaAlertaDePreco(final Pesquisa pesquisa, final Resposta resposta) {
		if (resposta.getPergunta().equals(QUAL_O_PRECO_DO_PRODUTO)) {
			int precoColetado = Integer.parseInt(resposta.getResposta());
			int precoEstipulado = pesquisa.getPrecoEstipulado();
			int margem = precoColetado - precoEstipulado;
			if (precoColetado > precoEstipulado) {
				criaAlertaESalvaNoBanco(pesquisa.getPontoDeVenda(), pesquisa.getProduto(), null, margem,
						TipoAlerta.PRECO_ACIMA_ESTIPULADO);
				return true;
			} else if (precoColetado < precoEstipulado) {
				criaAlertaESalvaNoBanco(pesquisa.getPontoDeVenda(), pesquisa.getProduto(), null, margem,
						TipoAlerta.PRECO_ABAIXO_ESTIPULADO);
				return true;
			}
		}
		return false;
	}

	private Boolean verificaECriaAlertaDeParticipacao(final Pesquisa pesquisa, final Resposta resposta) {
		if (resposta.getPergunta().equals(QUAL_A_PARTICIPACAO)) {
			int participacaoColetado = Integer.parseInt(resposta.getResposta());
			int participacaoEstipulado = pesquisa.getParticipacaoEstipulada();
			int margem = participacaoColetado - participacaoEstipulado;
			if (participacaoColetado > participacaoEstipulado) {
				criaAlertaESalvaNoBanco(pesquisa.getPontoDeVenda(), null, pesquisa.getCategoria(), margem,
						TipoAlerta.PARTICIPACAO_ACIMA_ESTIPULADA);
				return true;
			} else if (participacaoColetado < participacaoEstipulado) {
				criaAlertaESalvaNoBanco(pesquisa.getPontoDeVenda(), null, pesquisa.getCategoria(), margem,
						TipoAlerta.PARTICIPACAO_ABAIXO_ESTIPULADA);
				return true;
			}
		}
		return false;
	}

	private void criaAlertaESalvaNoBanco(final String pontoVenda, final String produto, final String categoria, final Integer margem,
			final TipoAlerta tipoAlerta) {
		Alerta alerta = new Alerta();
		alerta.setPontoDeVenda(pontoVenda);
		alerta.setDescricao(tipoAlerta.value);
		alerta.setProduto(produto);
		alerta.setCategoria(categoria);
		alerta.setFlTipo(tipoAlerta.key);
		alerta.setMargem(margem);
		gateway.salvar(alerta);
	}

	private void criaAlertaESalvaNoBanco(final String pontoVenda, final String produto, final TipoAlerta tipoAlerta) {
		criaAlertaESalvaNoBanco(pontoVenda, produto, null, null, tipoAlerta);
	}

	enum TipoAlerta {
		RUPTURA(1, "Ruptura detectada!"), PRECO_ACIMA_ESTIPULADO(2, "Preço acima do estipulado!"),
		PRECO_ABAIXO_ESTIPULADO(3, "Preço abaixo do estipulado!"),
		PARTICIPACAO_ACIMA_ESTIPULADA(4, "Participação acima da estipulada"),
		PARTICIPACAO_ABAIXO_ESTIPULADA(5, "Participação abaixo da estipulada");

		private final Integer key;
		private final String value;

		TipoAlerta(Integer key, String value) {
			this.key = key;
			this.value = value;
		}

		public Integer getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}
}