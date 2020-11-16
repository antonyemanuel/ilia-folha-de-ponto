package br.com.ilia.digital.folhadeponto.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ilia.digital.folhadeponto.controller.form.AlocacaoForm;
import br.com.ilia.digital.folhadeponto.controller.form.Momento;
import br.com.ilia.digital.folhadeponto.dto.AlocacaoDto;
import br.com.ilia.digital.folhadeponto.dto.RegistroDto;
import br.com.ilia.digital.folhadeponto.dto.RelatorioDto;
import br.com.ilia.digital.folhadeponto.model.Alocacao;
import br.com.ilia.digital.folhadeponto.model.Registro;
import br.com.ilia.digital.folhadeponto.service.AlocacaoService;
import br.com.ilia.digital.folhadeponto.service.RegistroService;
import br.com.ilia.digital.folhadeponto.service.RelatorioService;

@RestController
public class FolhaDePontoController {
	
	@Autowired
	private AlocacaoService alocacaoService;
	
	@Autowired
	private RegistroService registroService;
	
	@Autowired
	private RelatorioService relatorioService;
	
	@PostMapping("/alocacoes")
	public ResponseEntity<AlocacaoDto> alocar(@RequestBody AlocacaoForm alocacaoForm, UriComponentsBuilder uriBuilder) {
		Alocacao alocacao = alocacaoService.criarAlocacao(alocacaoForm);
		
		URI uri = uriBuilder.path("/alocacoes/{id}").buildAndExpand(alocacao.getId()).toUri();
		return ResponseEntity.created(uri).body(new AlocacaoDto(alocacao));		
	}

	@PostMapping("/batidas")
	public ResponseEntity<RegistroDto> baterPonto(@RequestBody Momento momento, UriComponentsBuilder uriBuilder) {
		Registro registro = registroService.criarRegistro(momento);
		
		List<String> horarios = registroService.getHorariosDoDia(registro);
		
		URI uri = uriBuilder.path("/batidas/{id}").buildAndExpand(registro.getId()).toUri();
		return ResponseEntity.created(uri).body(new RegistroDto(registro, horarios));		
	}

	@GetMapping("/folhas-de-ponto/{mes}")
	public ResponseEntity<RelatorioDto> relatorioMes(@PathVariable String mes, UriComponentsBuilder uriBuilder) {
		RelatorioDto relatorio = relatorioService.gerarRelatorio(mes);
		
		return ResponseEntity.ok(relatorio);
	}	
}
