package br.com.involvesfullstack.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.involvesfullstack.model.Alerta;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuscaAlertasServiceTest {
	
	@Autowired
	private BuscaAlertasService buscaAlertasService;
	
	@Test
	public void testBuscarTodos() {
		List<Alerta> alertas = buscaAlertasService.buscarTodos();
		assertThat(alertas).isNotNull().isNotEmpty();
	}

}
