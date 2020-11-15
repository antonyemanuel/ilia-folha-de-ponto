package br.com.ilia.digital.folhadeponto.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ilia.digital.folhadeponto.model.Registro;
import br.com.ilia.digital.folhadeponto.repository.RegistroRepository;

@Service
public class RegistroService {

	@Autowired
	private RegistroRepository repository;
	
	public void save(Registro registro) {
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

}
