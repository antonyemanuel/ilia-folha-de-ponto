package br.com.ilia.digital.folhadeponto.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ilia.digital.folhadeponto.controller.form.AlocacaoForm;
import br.com.ilia.digital.folhadeponto.dto.AlocacaoDto;
import br.com.ilia.digital.folhadeponto.model.Alocacao;
import br.com.ilia.digital.folhadeponto.repository.AlocacaoRepository;
import br.com.ilia.digital.folhadeponto.repository.RegistroRepository;

@Service
public class AlocacaoService {

	@Autowired
	private AlocacaoRepository repository;
	
	@Autowired
	private RegistroRepository registroRepository;
	
	public Alocacao criarAlocacao(AlocacaoForm alocacaoForm) {
		Alocacao alocacao = alocacaoForm.converter();
		
		save(alocacao);
		return alocacao;
	}	
	
	public void save(Alocacao alocacao) {
		validarAlocacao(alocacao);
		
		repository.save(alocacao);
	}
	
	public List<AlocacaoDto> getAlocacoesDto(String mes){
		List<Alocacao> alocacoesMes = repository.findByMes(mes);
		List<AlocacaoDto> alocacoesDto = alocacoesMes.stream().map(a-> new AlocacaoDto(a)).collect(Collectors.toList());
		return alocacoesDto;
	}
	
	public Map<String, Duration> getAlocacoesReportAgrupadasDto(String mes){
		List<Alocacao> alocacoesMes = repository.findByMes(mes);
		return alocacoesMes.stream().collect(Collectors.toMap(Alocacao::getNomeProjeto, Alocacao::getTempo, Duration::plus));
	}	
	
	private void validarAlocacao(Alocacao alocacao) {
		List<LocalDateTime> horarios = registroRepository.registrosDia(alocacao.getDia().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		validarHorasAlocacao(alocacao, horarios);
		validarTempoTotalDeAlocacoes(alocacao, horarios);
	}
	
	private void validarHorasAlocacao(Alocacao alocacao, List<LocalDateTime> horarios) {
		Duration horasInformadas = alocacao.getTempo();
		
		Duration horasDoDia = horasDoDia(horarios);		
		
		long diff = horasInformadas.compareTo(horasDoDia);
		if(diff > 0) {
			throw new RuntimeException("A Alocação não pode ser realizada porque ultrapassou o tempo trabalhado do dia");
		}
	}

	private Duration horasDoDia(List<LocalDateTime> horarios) {
		LocalDateTime inicio = null;
		LocalDateTime termino = horarios.get(horarios.size()-1);
		Duration horasDoDia = Duration.ZERO;
		
		for (int i = 0; i < horarios.size(); i++) {
			termino = horarios.get(i);
			if (inicio != null && i % 2 != 0) {
				horasDoDia = horasDoDia.plus(Duration.between(inicio, termino));
			}
			
			inicio = horarios.get(i);
		}
		return horasDoDia;
	}	
	
	private void validarTempoTotalDeAlocacoes(Alocacao alocacao, List<LocalDateTime> horarios) {
		List<Alocacao> alocacoes = repository.findByDia(alocacao.getDia().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		Duration durationParcial = alocacoes.stream().map(Alocacao::getTempo).reduce(Duration.ZERO, Duration::plus);
		Duration durationTotal = durationParcial.plus(alocacao.getTempo());
		
		Duration horasDoDia = horasDoDia(horarios);		
		long diff = durationTotal.compareTo(horasDoDia);
		if(diff > 0) {
			throw new RuntimeException("A Alocação não pode ser realizada porque as horas informadas superam a soma das horas do dia.");
		}
		
	}
	
}
