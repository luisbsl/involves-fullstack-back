package br.com.involvesfullstack.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.involvesfullstack.model.Pesquisa;

@Service
public abstract class ImportadorPesquisasService {
	
	private static final String PESQUISAS_BASE_URL = "https://selecao-involves.agilepromoter.com/pesquisas";
	
	public static List<Pesquisa> importarPesquisas() {
		RestTemplate restTemplate = new RestTemplate();

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
		restTemplate.getMessageConverters().add(0, converter);

		Pesquisa[] pesquisas = restTemplate.getForObject(PESQUISAS_BASE_URL, Pesquisa[].class);
		
		return Arrays.asList(pesquisas);
	}
}
