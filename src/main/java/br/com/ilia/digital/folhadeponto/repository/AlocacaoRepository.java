package br.com.ilia.digital.folhadeponto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ilia.digital.folhadeponto.model.Alocacao;

public interface AlocacaoRepository extends JpaRepository<Alocacao, Long>{

	@Query("SELECT a FROM Alocacao a WHERE FORMATDATETIME(dia,'yyyy-MM') = ?1")
	List<Alocacao> findByMes(String mes);
	
	@Query("SELECT a FROM Alocacao a WHERE FORMATDATETIME(dia,'yyyy-MM-dd') = ?1")
	List<Alocacao> findByDia(String dia);
}
