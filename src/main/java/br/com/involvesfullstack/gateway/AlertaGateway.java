package br.com.involvesfullstack.gateway;

import java.util.List;

import br.com.involvesfullstack.model.Alerta;

public interface AlertaGateway {
	
	void salvar(Alerta alerta);

	List<Alerta> buscarTodos();
}
