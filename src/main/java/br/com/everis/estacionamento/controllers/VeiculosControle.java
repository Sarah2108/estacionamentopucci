package br.com.everis.estacionamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.everis.estacionamento.dto.VeiculosDTO;
import br.com.everis.estacionamento.model.Cliente;
import br.com.everis.estacionamento.model.Veiculos;
import br.com.everis.estacionamento.service.ClienteService;
import br.com.everis.estacionamento.service.VeiculosService;

@RestController
public class VeiculosControle {

	public static final Logger LOGGER = LoggerFactory.getLogger(VeiculosControle.class);

	@Autowired
	private VeiculosService service;

	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/buscaVeiculos")
	public List<Veiculos> buscaVeiculos() {
		return service.findAll();

	}

	@GetMapping(value = "/buscaSeVeiculoExiste")
	public Veiculos buscaVeiculos(String placa, String cpf, String modelo, String tipo) {

		Veiculos resultado = new Veiculos();

		Cliente clienteVeiculo = new Cliente();

		clienteVeiculo = clienteService.findCliente(null, cpf);

		if (clienteVeiculo != null) {

			LOGGER.info("clienteId: " + clienteVeiculo.getId());

			resultado = service.buscarVeiculos(placa, modelo, clienteVeiculo.getId(), tipo);

		}else {
			throw new ServiceException("Cliente não Encontrado");
		}
		return resultado;

	}

	@PostMapping(value = "/incluirVeiculos")
	public Veiculos buscaVeiculos(@RequestBody VeiculosDTO veiculos) {

		Veiculos resultado = new Veiculos();

		Cliente clienteVeiculo = new Cliente();

		clienteVeiculo = clienteService.findCliente(null, veiculos.getCpf());

		if (clienteVeiculo != null) {

			LOGGER.info("clienteId: " + clienteVeiculo.getId());

			service.saveVeiculos(veiculos.getPlaca(), veiculos.getModelo(), clienteVeiculo.getId(), veiculos.getTipo());

			LOGGER.info("placa: " + veiculos.getPlaca());
			LOGGER.info("modelo: " + veiculos.getModelo());
			LOGGER.info("tipo: " + veiculos.getTipo());

			resultado = service.buscarVeiculos(veiculos.getPlaca(), veiculos.getModelo(), clienteVeiculo.getId(),
					veiculos.getTipo());
		}else {
			throw new ServiceException("Cliente não Encontrado");
		}
		
		return resultado;

	}
}
