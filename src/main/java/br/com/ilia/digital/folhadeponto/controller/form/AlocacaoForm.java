package br.com.ilia.digital.folhadeponto.controller.form;

import java.time.Duration;
import java.time.LocalDate;

import br.com.ilia.digital.folhadeponto.model.Alocacao;

public class AlocacaoForm {
	
	private String dia;
	private String nomeProjeto;
	private String tempo;
	
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	
	public String getNomeProjeto() {
		return nomeProjeto;
	}
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}
	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	public Alocacao converter() {
		return new Alocacao(LocalDate.parse(dia), nomeProjeto, Duration.parse(tempo));
	}
	
}
