package br.com.everis.estacionamento.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.everis.estacionamento.model.Setup;
import br.com.everis.estacionamento.repository.SetupRepository;

@Service
public class SetupService {

	@Autowired
	private SetupRepository repository;

	public List<Setup> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Setup buscarSetup(String dia) {

		List<Setup> setupResultado = new ArrayList<Setup>();
		
		setupResultado = repository.findByDia(dia);
		
		if(setupResultado.isEmpty()) {
			throw new ServiceException("nenhum setup Encontrado");

		}
		return setupResultado.get(0);
	}

	public void saveSetup(String dia, int vagas, double taxa) {
		Setup dataNovo = new Setup();

		dataNovo.setDia(dia);
		dataNovo.setTaxa(taxa);
		dataNovo.setVagas(vagas);
		
		repository.saveAndFlush(dataNovo);
	}
	
}
