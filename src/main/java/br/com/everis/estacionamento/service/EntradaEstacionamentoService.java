package br.com.everis.estacionamento.service;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.everis.estacionamento.model.Cliente;
import br.com.everis.estacionamento.model.EntradaEstacionamento;
import br.com.everis.estacionamento.model.Setup;
import br.com.everis.estacionamento.model.Veiculos;
import br.com.everis.estacionamento.repository.EntradaEstacionamentoRepository;

@Service
public class EntradaEstacionamentoService {

	@Autowired
	private EntradaEstacionamentoRepository repository;

	@Autowired
	private SetupService setupService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private VeiculosService veiculoService;

	public List<EntradaEstacionamento> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public EntradaEstacionamento findEntradaEstacionamento(Long veiculoId) {

		EntradaEstacionamento entradaEstacionamentoResultado = new EntradaEstacionamento();

		entradaEstacionamentoResultado = repository.findByVeiculoIdAndHoraSaida(veiculoId, null);

		return entradaEstacionamentoResultado;
	}

	public Double calcularTaxa(String placa, String modelo, String cpf, String diaSaida) {

		Cliente clienteAtual = new Cliente();

		clienteAtual = clienteService.findCliente(null, cpf);

		Veiculos veiculoAtual = new Veiculos();

		veiculoAtual = veiculoService.buscarVeiculos(placa, modelo, clienteAtual.getId(), "");

		Setup setupAtual = new Setup();

		setupAtual = setupService.buscarSetup(diaSaida);

		EntradaEstacionamento entrada = findEntradaEstacionamento(veiculoAtual.getId());

		Calendar data = Calendar.getInstance();
		int horaSaida = data.get(Calendar.HOUR_OF_DAY);
		int minSaida = data.get(Calendar.MINUTE);

		String horaSaidaString = horaSaida + ":" + minSaida + ":00";

		Double horasDentroDoEstacionamento = calcularDiferencaHoras(entrada.getHoraEntrada() + ":00", horaSaidaString);

		Double resultado = horasDentroDoEstacionamento * setupAtual.getTaxa();

		return resultado;

	}

	public void retirarVeiculo(String placa, String modelo, String cpf) {
		Cliente clienteAtual = new Cliente();

		clienteAtual = clienteService.findCliente(null , cpf);

		Veiculos veiculoAtual = new Veiculos();

		veiculoAtual = veiculoService.buscarVeiculos(placa, modelo, clienteAtual.getId(), "");

		EntradaEstacionamento entrada = findEntradaEstacionamento(veiculoAtual.getId());

		Calendar data = Calendar.getInstance();
		int horaAtual = data.get(Calendar.HOUR_OF_DAY);
		int minAtual = data.get(Calendar.MINUTE);

		entrada.setHoraSaida(horaAtual + ":" + minAtual + ":00");

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = dateFormat.format(date);

		entrada.setDataSaida(strDate);

		clienteAtual.setStatus(false);

		clienteService.atualizaCliente(clienteAtual);
	}

	public void saveEntradaEstacionamento(long veiculoId, String horaEntrada, String dataEntrada) {
		EntradaEstacionamento entradaNova = new EntradaEstacionamento();

		entradaNova.setVeiculoId(veiculoId);
		entradaNova.setHoraEntrada(horaEntrada);
		entradaNova.setDataEntrada(dataEntrada);

		repository.saveAndFlush(entradaNova);

	}

	private Double calcularDiferencaHoras(String horaInicial, String horaFinal) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		double diferencaHoras = 0.0;
		try {
			Date horaIni = sdf.parse(horaInicial);
			Date horaFim = sdf.parse(horaFinal);

			double horaI = horaIni.getTime();
			double horaF = horaFim.getTime();

			diferencaHoras = horaF - horaI;

			diferencaHoras = diferencaHoras / 1000 / 60 / 60;

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return diferencaHoras;

	}
	
	public int QuantidadeVagas(String dia) {
		
		int vagas = 0;
		
		int clientesAtivos = clienteService.quantidadeVagasOcupadas();
		
		Setup setupDia = setupService.buscarSetup(dia);
		
		vagas = setupDia.getVagas() - clientesAtivos;
		
		return vagas;
	}
}