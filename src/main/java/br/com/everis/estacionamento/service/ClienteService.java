package br.com.everis.estacionamento.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.everis.estacionamento.controllers.VeiculosControle;
import br.com.everis.estacionamento.model.Cliente;
import br.com.everis.estacionamento.repository.ClienteRepository;

@Service
public class ClienteService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);


	@Autowired
	private ClienteRepository repository;

	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Cliente findCliente(String nome, String cpf) {

		Cliente clienteResultado = new Cliente();
		
		LOGGER.info("nome: " + nome);
		LOGGER.info("cpf: " + cpf);
		
		if (cpf != null && !cpf.equalsIgnoreCase("")) {
			clienteResultado = repository.findByCpf(cpf);
			if (clienteResultado != null) {
				return clienteResultado;
			}
		}
		if (nome != null && !nome.equalsIgnoreCase("")) {
			List<Cliente> clientes = repository.findByNome(nome);

			if (!clientes.isEmpty()) {
				clienteResultado = clientes.get(0);
				return clienteResultado;
			}
		}

		return clienteResultado;

	}
	
	public void saveCliente(String nome, String cpf, String telefone) {
		Cliente clienteNovo =  new Cliente();
		
		clienteNovo.setCpf(cpf);
		clienteNovo.setNome(nome);
		clienteNovo.setTelefone(telefone);
		clienteNovo.setStatus(true);
	
		repository.saveAndFlush(clienteNovo);
		
	}
	
	public void atualizaCliente(Cliente cliente) {
		Optional<Cliente> clienteBusca =  repository.findById(cliente.getId());
		
		Cliente atualizarCliente = clienteBusca.get();
		
		atualizarCliente.setCpf(cliente.getCpf());
		atualizarCliente.setNome(cliente.getNome());
		atualizarCliente.setTelefone(cliente.getTelefone());
		atualizarCliente.setStatus(cliente.getStatus());
	
		repository.saveAndFlush(atualizarCliente);
		
	}
	
	public Integer quantidadeVagasOcupadas() {
		Integer quantidadeAtivos = 0;
		
		List<Cliente> clientesAtivos = repository.findByStatus(true);
		
		if(!clientesAtivos.isEmpty()) {
			quantidadeAtivos = clientesAtivos.size();
		}
		
		return quantidadeAtivos;
		
	}
	
	
	
}
