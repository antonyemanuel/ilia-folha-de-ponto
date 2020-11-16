package br.com.ilia.digital.folhadeponto.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ilia.digital.folhadeponto.controller.form.Momento;
import br.com.ilia.digital.folhadeponto.model.Registro;
import br.com.ilia.digital.folhadeponto.repository.RegistroRepository;

@Service
public class RegistroService {

	@Autowired
	private RegistroRepository repository;
	
	private static final int QUANTIDADE_MAX_REGISTROS_DIA = 4;
	private static final int QUANTIDADE_REGISTROS_ANTES_ALMOCO = 2;
	
	public Registro criarRegistro(Momento momento) {
		Registro registro = new Registro(LocalDateTime.parse(momento.getDataHora()));
		save(registro);
		return registro;
	}
	
	public void save(Registro registro) {
		validarRegistros(registro);
		repository.save(registro);
	}
	
	public List<String> getHorariosDoDia(Registro registro){
		List<LocalDateTime> registrosDia = repository.registrosDia(registro.getDataHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return registrosDia.stream().map(l-> l.format(DateTimeFormatter.ofPattern("HH:mm:ss"))).collect(Collectors.toList());
	}
	
	public List<String> getHorariosDoMes(String mes){
		List<LocalDateTime> registrosMes = repository.registrosMes(mes);
		return registrosMes.stream().map(l-> l.format(DateTimeFormatter.ofPattern("HH:mm:ss"))).collect(Collectors.toList());
	}

	private void validarRegistros(Registro registro) {
		String dataFormatada = registro.getDataHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		validarQuantidadeDiariaRegistros(dataFormatada);
		validarDiaUtil(registro.getDataHora().getDayOfWeek());
		validarIntervaloAlmoco(dataFormatada, registro);
	}
	
	private void validarQuantidadeDiariaRegistros(String dataFormatada) {
		List<LocalDateTime> horarios = repository.registrosDia(dataFormatada);
		if(horarios.size() >= QUANTIDADE_MAX_REGISTROS_DIA) {
			throw new RuntimeException("O limite diário de batidas foi atingido: " + QUANTIDADE_MAX_REGISTROS_DIA);
		}
	}
	
	private void validarDiaUtil(DayOfWeek dayOfWeek) {
		boolean isDiaUtil = !dayOfWeek.equals(DayOfWeek.SATURDAY) && !dayOfWeek.equals(DayOfWeek.SUNDAY);
		if (!isDiaUtil) {
			throw new RuntimeException("Não é permitido registrar o ponto Sábados e Domingos");
		}
	}

	private void validarIntervaloAlmoco(String dataFormatada, Registro registro) {
		List<LocalDateTime> horarios = repository.registrosDia(dataFormatada);
		if(horarios.size() == QUANTIDADE_REGISTROS_ANTES_ALMOCO) {
			Duration intervalo = Duration.between(horarios.get(QUANTIDADE_REGISTROS_ANTES_ALMOCO-1),registro.getDataHora());
			Duration duracaoAlmoco = Duration.parse("PT1H0M");
			int diff = intervalo.compareTo(duracaoAlmoco);
			if(diff  < 0) {
				throw new RuntimeException("O intervalo de almoço deve ser no mínimo de 1h");
			}
		}
	}
	
}
