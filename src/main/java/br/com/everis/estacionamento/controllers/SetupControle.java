package br.com.everis.estacionamento.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.el.parser.ParseException;

import br.com.everis.estacionamento.dto.ClienteDTO;
import br.com.everis.estacionamento.dto.SetupDTO;
import br.com.everis.estacionamento.model.Cliente;
import br.com.everis.estacionamento.model.Setup;
import br.com.everis.estacionamento.service.ClienteService;
import br.com.everis.estacionamento.service.SetupService;

@RestController
public class SetupControle {

	@Autowired
	private SetupService service;

	@GetMapping(value = "/buscaSetup")
	public List<Setup> buscaSetup() {
		return service.findAll();
	}

	@GetMapping(value = "/buscaSeSetupExiste")
	public Setup buscaSetup(@RequestParam(required = true) String dia) {

		Setup resultado = new Setup();

		resultado = service.buscarSetup(dia);

		return resultado;

	}

	@PostMapping(value = "/incluirSetup")
	public Setup buscaSetup(@RequestBody SetupDTO setup) {

		Setup resultado = new Setup();

		service.saveSetup(setup.getDia(), setup.getVagas(), setup.getTaxa());

		resultado = service.buscarSetup(setup.getDia());

		return resultado;

	}
}
