package br.com.involvesfullstack.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.involvesfullstack.model.Alerta;
import br.com.involvesfullstack.service.BuscaAlertasService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AlertaControllerTest {

	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private AlertaController alertaController;

	@MockBean
	private BuscaAlertasService buscaAlertasService;

	private List<Alerta> alertas;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.alertaController).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		Alerta alertaPrecoBaixo = new Alerta("Padaria do seu João", "Preço abaixo do estipulado!",
				"Ovo de Pascoa Kinder 48", null, 3, -5);
		Alerta alertaRuptura = new Alerta("Angel One Capoeiras", "Ruptura detectada!", "Ovo de Pascoa Laka 48", null, 1,
				null);
		
		alertas = Arrays.asList(alertaPrecoBaixo, alertaRuptura);
	}

	@Test
	public void testAlertas() throws Exception {
        when(buscaAlertasService.buscarTodos()).thenReturn(alertas);

        mockMvc.perform(get("/"+AlertaController.ALERTA_BASE_URI).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].descricao", equalTo("Preço abaixo do estipulado!")))
            .andExpect(jsonPath("$[1].descricao", equalTo("Ruptura detectada!")));
	}

}
