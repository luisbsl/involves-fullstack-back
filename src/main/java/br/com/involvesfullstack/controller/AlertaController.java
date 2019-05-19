package br.com.involvesfullstack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.involvesfullstack.model.Alerta;
import br.com.involvesfullstack.service.BuscaAlertasService;
import br.com.involvesfullstack.service.ProcessarAlertasService;

@RestController
@RequestMapping(AlertaController.ALERTA_BASE_URI)
@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 3600)
public class AlertaController {
	
	public static final String ALERTA_BASE_URI = "api/v1/alertas";

	@Autowired
	private BuscaAlertasService buscaAlertasService;
	
	@Autowired
	private ProcessarAlertasService processador;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Alerta> alertas() {
		return buscaAlertasService.buscarTodos();
    }
	
	@GetMapping("/processar")
    public void processar() {
		processador.processa();
    }
	
}
