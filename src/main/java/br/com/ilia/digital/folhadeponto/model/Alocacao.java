package br.com.ilia.digital.folhadeponto.model;

import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Alocacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dia;
	
	private String nomeProjeto;
	
	private Duration tempo;
	
	public Alocacao() {
	}
	
	public Alocacao(LocalDate dia, String nomeProjeto, Duration tempo) {
		this.dia = dia;
		this.nomeProjeto = nomeProjeto;
		this.tempo = tempo;
	}
		
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}
	public Duration getTempo() {
		return tempo;
	}
	public void setTempo(Duration tempo) {
		this.tempo = tempo;
	}
	
}
