package br.com.postech.techchallange_customer.domain.entity;

public class Endereco {

	private String rua;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;

	public Endereco() {
	}

	public Endereco(String rua, String numero, String cidade, String estado, String cep) {
		this.rua = rua;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
	}

	/**
	 * Valida se o endereço está completo
	 */
	public boolean isCompleto() {
		return this.rua != null && !this.rua.isEmpty()
				&& this.numero != null && !this.numero.isEmpty()
				&& this.cidade != null && !this.cidade.isEmpty()
				&& this.estado != null && !this.estado.isEmpty()
				&& this.cep != null && !this.cep.isEmpty();
	}

	/**
	 * Valida se o CEP tem formato válido
	 */
	public boolean isCepValido() {
		return this.cep != null && this.cep.matches("\\d{8}");
	}

	/**
	 * Valida se o estado tem formato válido (2 caracteres)
	 */
	public boolean isEstadoValido() {
		return this.estado != null && this.estado.matches("[A-Z]{2}");
	}

	/**
	 * Formata o CEP no padrão XXXXX-XXX
	 */
	public String formatarCep() {
		if (isCepValido()) {
			return cep.substring(0, 5) + "-" + cep.substring(5);
		}
		return cep;
	}

	// Getters e Setters

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
