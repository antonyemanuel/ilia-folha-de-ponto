package br.com.ilia.digital.folhadeponto.dto;

import java.util.List;

public class RelatorioDto {
	
	public String mes;
	public List<AlocacaoReport> alocacoes;
	public List<RegistroDto> registros;
	
	public RelatorioDto(String mes, List<AlocacaoReport> alocacoes, List<RegistroDto> registros) {
		this.mes = mes;
		this.alocacoes = alocacoes;
		this.registros = registros;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<AlocacaoReport> getAlocacoes() {
		return alocacoes;
	}

	public void setAlocacoes(List<AlocacaoReport> alocacoes) {
		this.alocacoes = alocacoes;
	}

	public List<RegistroDto> getRegistros() {
		return registros;
	}

	public void setRegistros(List<RegistroDto> registros) {
		this.registros = registros;
	}

}
