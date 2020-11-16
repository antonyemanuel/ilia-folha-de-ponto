package br.com.ilia.digital.folhadeponto.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ilia.digital.folhadeponto.dto.AlocacaoReport;
import br.com.ilia.digital.folhadeponto.dto.RegistroDto;
import br.com.ilia.digital.folhadeponto.dto.RelatorioDto;

@Service
public class RelatorioService {

	@Autowired
	AlocacaoService alocacaoService;
	
	@Autowired 
	RegistroService registroService;
	
	public RelatorioDto gerarRelatorio(String mes) {
		
		List<AlocacaoReport> alocacoes = new ArrayList<AlocacaoReport>();
		Map<String, Duration> mapProjetoTempo = alocacaoService.getAlocacoesReportAgrupadasDto(mes);
		for (Entry<String, Duration> e : mapProjetoTempo.entrySet()) {
			String nomeProjeto = e.getKey();
			Duration tempo = e.getValue();
			alocacoes.add(new AlocacaoReport(nomeProjeto, tempo));
		}
		
		List<RegistroDto> registros = new ArrayList<RegistroDto>();
		Map<String, List<LocalDateTime>> mapDiaHorarios = registroService.getHorariosMesAgrupadosPorDia(mes);
		for (Entry<String, List<LocalDateTime>> e : mapDiaHorarios.entrySet()) {
			String dia = e.getKey();
			List<String> horarios = e.getValue().stream().map(l-> l.format(DateTimeFormatter.ofPattern("HH:mm:ss"))).collect(Collectors.toList());
			registros.add(new RegistroDto(dia, horarios));
		}
		
		RelatorioDto relatorio = new RelatorioDto(mes, alocacoes, registros);
		
		return relatorio;
	}
}
