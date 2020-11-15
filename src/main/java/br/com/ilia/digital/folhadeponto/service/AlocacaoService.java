package br.com.ilia.digital.folhadeponto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ilia.digital.folhadeponto.dto.AlocacaoDto;
import br.com.ilia.digital.folhadeponto.model.Alocacao;
import br.com.ilia.digital.folhadeponto.repository.AlocacaoRepository;

@Service
public class AlocacaoService {

	@Autowired
	private AlocacaoRepository repository;
	
	public void save(Alocacao alocacao) {
		repository.save(alocacao);
	}
	
	public List<AlocacaoDto> getAlocacoesDto(String mes){
		List<Alocacao> alocacoesMes = repository.findByMes(mes);
		List<AlocacaoDto> alocacoesDto = alocacoesMes.stream().map(a-> new AlocacaoDto(a)).collect(Collectors.toList());
		return alocacoesDto;
	}
}
