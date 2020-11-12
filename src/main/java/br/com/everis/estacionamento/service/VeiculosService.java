package br.com.everis.estacionamento.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.everis.estacionamento.model.Cliente;
import br.com.everis.estacionamento.model.Veiculos;
import br.com.everis.estacionamento.repository.VeiculosRepository;

@Service
public class VeiculosService {

	@Autowired
	private VeiculosRepository repository;

	public List<Veiculos> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Veiculos buscarVeiculos(String placa, String modelo, Long clienteId, String tipo) {

		List<Veiculos> veiculosResultado = new ArrayList<Veiculos>();
		Veiculos veiculoResultado = new Veiculos();

		if (modelo != null && !modelo.equalsIgnoreCase("") && clienteId != null) {
			veiculosResultado = repository.findByModeloAndClienteId(modelo, clienteId);
			if (!veiculosResultado.isEmpty()) {
				veiculoResultado = veiculosResultado.get(0);
			}
		} else {
			if (placa != null && !placa.equalsIgnoreCase("")) {
				veiculoResultado = repository.findByPlaca(placa);
			} else {
				throw new ServiceException("Placa ou Modelo e CPF precisam ser preenchidos");
			}
		}

		return veiculoResultado;

	}

	public void saveVeiculos(String placa, String modelo, long clienteid, String tipo) {
		Veiculos veiculoNovo = new Veiculos();

		veiculoNovo.setPlaca(placa);
		veiculoNovo.setModelo(modelo);
		veiculoNovo.setTipo(tipo);
		veiculoNovo.setClienteId(clienteid);

		repository.saveAndFlush(veiculoNovo);

	}

}
