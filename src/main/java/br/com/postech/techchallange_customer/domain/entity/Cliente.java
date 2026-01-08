package br.com.postech.techchallange_customer.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Cliente {

	private String id;
	private String clienteId;
	private String nomeCliente;
	private String emailCliente;
	private String cpfCliente;
	private String telefone;
	private Endereco endereco;
	private Boolean ativo;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataUltimaAtualizacao;
	private Integer versao;
	private Metadata metadata;

	public Cliente() {
		this.ativo = true;
		this.dataCadastro = LocalDateTime.now();
		this.dataUltimaAtualizacao = LocalDateTime.now();
	}

	public Cliente(String nomeCliente, String emailCliente, String cpfCliente) {
		this();
		this.nomeCliente = nomeCliente;
		this.emailCliente = emailCliente;
		this.cpfCliente = cpfCliente;
		this.generateClienteIdIfNeeded();
	}

	/**
	 * Gera um novo UUID para clienteId se não existir
	 */
	public void generateClienteIdIfNeeded() {
		if (this.clienteId == null || this.clienteId.isEmpty()) {
			this.clienteId = UUID.randomUUID().toString();
		}
	}

	/**
	 * Atualiza a data de última atualização
	 */
	public void updateTimestamp() {
		this.dataUltimaAtualizacao = LocalDateTime.now();
	}

	/**
	 * Desativa o cliente (soft delete)
	 */
	public void desativar() {
		this.ativo = false;
		if (this.metadata == null) {
			this.metadata = new Metadata();
		}
		this.metadata.setDataDesativacao(LocalDateTime.now());
		this.updateTimestamp();
	}

	/**
	 * Reativa o cliente
	 */
	public void reativar() {
		this.ativo = true;
		if (this.metadata != null) {
			this.metadata.setDataDesativacao(null);
		}
		this.updateTimestamp();
	}

	/**
	 * Valida se o cliente está ativo
	 */
	public boolean isAtivo() {
		return this.ativo != null && this.ativo;
	}

	/**
	 * Valida se o CPF tem formato válido
	 */
	public boolean isCpfValido() {
		return this.cpfCliente != null && this.cpfCliente.matches("\\d{11}");
	}

	/**
	 * Valida se o email tem formato válido
	 */
	public boolean isEmailValido() {
		return this.emailCliente != null && this.emailCliente.matches("^[A-Za-z0-9+_.-]+@(.+)$");
	}

	/**
	 * Valida se os dados obrigatórios estão preenchidos
	 */
	public boolean isValido() {
		return this.nomeCliente != null && !this.nomeCliente.isEmpty()
				&& this.emailCliente != null && !this.emailCliente.isEmpty()
				&& this.cpfCliente != null && !this.cpfCliente.isEmpty()
				&& isCpfValido()
				&& isEmailValido();
	}

	/**
	 * Adiciona uma tag ao metadata
	 */
	public void adicionarTag(String tag) {
		if (this.metadata == null) {
			this.metadata = new Metadata();
		}
		this.metadata.adicionarTag(tag);
	}

	/**
	 * Remove uma tag do metadata
	 */
	public void removerTag(String tag) {
		if (this.metadata != null) {
			this.metadata.removerTag(tag);
		}
	}

	/**
	 * Verifica se o cliente tem uma tag específica
	 */
	public boolean hasTag(String tag) {
		return this.metadata != null && this.metadata.hasTag(tag);
	}

	// Getters e Setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDateTime getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
}
