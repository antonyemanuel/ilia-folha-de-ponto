package br.com.ilia.digital.folhadeponto.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.ilia.digital.folhadeponto.model.Registro;

public class RegistroDto {

	private String dia;
	private List<String> horarios;
	
	public RegistroDto(Registro registro, List<String> horarios) {
		this.dia = registro.getDataHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.horarios = horarios;
	}
	
	public RegistroDto(String dia, List<String> horarios) {
		this.dia = dia;
		this.horarios = horarios;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public List<String> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<String> horarios) {
		this.horarios = horarios;
	}

}
