package br.com.ilia.digital.folhadeponto.dto;

import java.time.Duration;

public class AlocacaoReport {

	public String nomeProjeto;
	public Duration tempoTotalAlocado;
	
	public AlocacaoReport(String nomeProjeto, Duration tempoTotalAlocado) {
		this.nomeProjeto = nomeProjeto;
		this.tempoTotalAlocado = tempoTotalAlocado;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public Duration getTempoTotalAlocado() {
		return tempoTotalAlocado;
	}

	public void setTempoTotalAlocado(Duration tempoTotalAlocado) {
		this.tempoTotalAlocado = tempoTotalAlocado;
	}
	
}
