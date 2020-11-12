package br.com.everis.estacionamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.everis.estacionamento.dto.ClienteDTO;
import br.com.everis.estacionamento.model.Cliente;
import br.com.everis.estacionamento.repository.ClienteRepository;
import br.com.everis.estacionamento.service.ClienteService;

@RestController
public class ClienteControle {
	@Autowired
	private ClienteService service;

	@GetMapping(value = "/buscaClientes")
	public List<Cliente> buscaClientes() {
		return service.findAll();
	}
	
	@GetMapping(value = "/buscaSeClienteExiste")
	public Cliente buscaCliente(@RequestParam (required = true) String nome, String cpf ) {
		
		Cliente resultado = new Cliente();
		
		resultado = service.findCliente(nome, cpf);
		
		return resultado;
		
	}
	
	@PostMapping(value = "/incluirCliente")
	public Cliente buscaCliente(@RequestBody ClienteDTO cliente ) {
		
		Cliente resultado = new Cliente();
		
		service.saveCliente(cliente.getNome(), cliente.getCpf(), cliente.getTelefone());
		
		resultado = service.findCliente(cliente.getNome(), cliente.getCpf());
		
		return resultado;
		
	}
}
