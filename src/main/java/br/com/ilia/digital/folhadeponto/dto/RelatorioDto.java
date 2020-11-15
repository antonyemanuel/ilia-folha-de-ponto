package br.com.ilia.digital.folhadeponto.dto;

import java.util.List;

public class RelatorioDto {
	
	public String mes;
	public List<AlocacaoDto> alocacoes;
	public List<String> horarios;
	
	public RelatorioDto(String mes, List<AlocacaoDto> alocacoes, List<String> horarios) {
		this.mes = mes;
		this.alocacoes = alocacoes;
		this.horarios = horarios;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<AlocacaoDto> getAlocacoes() {
		return alocacoes;
	}

	public void setAlocacoes(List<AlocacaoDto> alocacoes) {
		this.alocacoes = alocacoes;
	}

	public List<String> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<String> horarios) {
		this.horarios = horarios;
	}
	
}
