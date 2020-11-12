package br.com.everis.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everis.estacionamento.model.EntradaEstacionamento;

@Repository
public interface EntradaEstacionamentoRepository extends JpaRepository<EntradaEstacionamento, Long> { 

    public EntradaEstacionamento findById(long id);
    
    public EntradaEstacionamento findByVeiculoIdAndHoraSaida(long veiculoId, String horaSaida); 
	
}
