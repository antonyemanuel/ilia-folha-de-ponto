package br.com.ilia.digital.folhadeponto.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ilia.digital.folhadeponto.model.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Long>{

	@Query("SELECT dataHora FROM Registro WHERE FORMATDATETIME(dataHora,'yyyy-MM-dd') = ?1 ORDER BY dataHora")
	List<LocalDateTime> registrosDia(String dia);
	
	@Query("SELECT dataHora FROM Registro WHERE FORMATDATETIME(dataHora,'yyyy-MM') = ?1 ORDER BY dataHora")
	List<LocalDateTime> registrosMes(String mes);
}
