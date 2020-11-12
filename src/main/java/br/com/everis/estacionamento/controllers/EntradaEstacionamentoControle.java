package br.com.everis.estacionamento.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.everis.estacionamento.dto.EntradaEstacionamentoDTO;
import br.com.everis.estacionamento.model.Cliente;
import br.com.everis.estacionamento.model.EntradaEstacionamento;
import br.com.everis.estacionamento.model.Setup;
import br.com.everis.estacionamento.model.Veiculos;
import br.com.everis.estacionamento.service.ClienteService;
import br.com.everis.estacionamento.service.EntradaEstacionamentoService;
import br.com.everis.estacionamento.service.SetupService;
import br.com.everis.estacionamento.service.VeiculosService;

@RestController
public class EntradaEstacionamentoControle {

	@Autowired
	private EntradaEstacionamentoService service;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private VeiculosService veiculoService;

	@Autowired
	private SetupService setupService;

	@GetMapping(value = "/buscaEntradaEstacionamento")
	public List<EntradaEstacionamento> buscaEntradaEstacionamento() {
		return service.findAll();
	}

	@PostMapping(value = "/incluirEntradaEstacionamento")
	public EntradaEstacionamento buscaEntradaEstacionamento(
			@RequestBody EntradaEstacionamentoDTO entradaEstacionamento) {

		EntradaEstacionamento resultado = new EntradaEstacionamento();

		Cliente clientePraEstacionar = clienteService.findCliente(null, entradaEstacionamento.getCpf());

		if (clientePraEstacionar != null) {

			Veiculos veiculoPraEstacionar = veiculoService.buscarVeiculos(entradaEstacionamento.getPlaca(),
					entradaEstacionamento.getModelo(), clientePraEstacionar.getId(), null);
			
			EntradaEstacionamento existeVeiculo = service.findEntradaEstacionamento(veiculoPraEstacionar.getId());

			if(existeVeiculo != null) {
				throw new ServiceException("Veiculo já esta estacionado");

			}
			
			service.saveEntradaEstacionamento(veiculoPraEstacionar.getId(), entradaEstacionamento.getHoraEntrada(),
					entradaEstacionamento.getDataEntrada());

			resultado = service.findEntradaEstacionamento(veiculoPraEstacionar.getId());

		}
		return resultado;

	}

	@PostMapping(value = "/retirarVeiculo")
	public void retirarVeiculo(@RequestBody EntradaEstacionamentoDTO entradaEstacionamento) {

		service.retirarVeiculo(entradaEstacionamento.getPlaca(), entradaEstacionamento.getModelo(), entradaEstacionamento.getCpf());
	}

	@PostMapping(value = "/calcularTaxa")
	public Double calcularTaxa(@RequestBody EntradaEstacionamentoDTO entradaEstacionamento) {

		Double taxa = service.calcularTaxa(entradaEstacionamento.getPlaca(), entradaEstacionamento.getModelo(), entradaEstacionamento.getCpf(), entradaEstacionamento.getDiaSaida());

		return taxa;

	}
	
	
	@GetMapping(value = "/quantidadeVagasLivres")
	public Integer calcularTaxa(@RequestParam(required = true) String dia) {

		int taxa = service.QuantidadeVagas(dia);

		return taxa;

	}
}
