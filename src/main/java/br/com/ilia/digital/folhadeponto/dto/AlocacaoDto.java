package br.com.ilia.digital.folhadeponto.dto;

import java.time.format.DateTimeFormatter;

import br.com.ilia.digital.folhadeponto.model.Alocacao;

public class AlocacaoDto {
	
	public String dia;
	public String nomeProjeto;
	public String tempo;
	
	public AlocacaoDto(Alocacao alocacao) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		this.dia = alocacao.getDia().format(formatter);
		this.nomeProjeto = alocacao.getNomeProjeto();
		this.tempo = alocacao.getTempo().toString();
	}
	
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
	
	
}
