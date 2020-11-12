package br.com.everis.estacionamento.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everis.estacionamento.model.Setup;
@Repository
public interface SetupRepository  extends JpaRepository<Setup, Long> { 
	
    public Setup findById(String id);
		
	public List<Setup> findByDia(String dia);
	
	
}
