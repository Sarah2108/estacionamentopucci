package br.com.everis.estacionamento.dto;

public class EntradaEstacionamentoDTO {
	private String placa;
	private String modelo;
	private String cpf;
	private String horaEntrada;
	private String dataEntrada;
	private String diaSaida;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public String getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getDiaSaida() {
		return diaSaida;
	}

	public void setDiaSaida(String diaSaida) {
		this.diaSaida = diaSaida;
	}

}
