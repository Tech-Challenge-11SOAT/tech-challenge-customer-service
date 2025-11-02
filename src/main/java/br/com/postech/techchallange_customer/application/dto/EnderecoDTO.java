package br.com.postech.techchallange_customer.application.dto;

import jakarta.validation.constraints.Pattern;

public class EnderecoDTO {

	private String rua;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;

	@Pattern(regexp = "[A-Z]{2}", message = "Estado deve ter 2 caracteres")
	private String estado;

	@Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
	private String cep;

	public EnderecoDTO() {
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
}
