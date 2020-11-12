package br.com.everis.estacionamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everis.estacionamento.model.Veiculos;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculos, Long> {

	public Veiculos findById(long id);

	public Veiculos findByPlaca(String placa);

	public List<Veiculos> findByModeloAndClienteId(String modelo, long clienteId);

	public List<Veiculos> findByTipo(String tipo);

	public List<Veiculos> findByClienteIdAndPlaca(long clienteid, String placa);

	public List<Veiculos> findByClienteId(long clienteId);

}
