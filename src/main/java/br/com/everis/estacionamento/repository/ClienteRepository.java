package br.com.everis.estacionamento.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everis.estacionamento.model.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> { 
	
	public Cliente findById(long id);
	
	public List<Cliente> findByNome(String nome);
	
	public Cliente findByCpf(String cpf);
	
	public List<Cliente> findByStatus(Boolean status);
	

}
