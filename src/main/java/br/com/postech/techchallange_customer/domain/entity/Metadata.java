package br.com.postech.techchallange_customer.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Metadata {

	private String origem;
	private String canal;
	private List<String> tags;
	private String notas;
	private LocalDateTime dataDesativacao;

	public Metadata() {
		this.tags = new ArrayList<>();
	}

	public Metadata(String origem, String canal) {
		this();
		this.origem = origem;
		this.canal = canal;
	}

	// Regras de negócio

	/**
	 * Adiciona uma tag se não existir
	 */
	public void adicionarTag(String tag) {
		if (this.tags == null) {
			this.tags = new ArrayList<>();
		}
		if (!this.tags.contains(tag)) {
			this.tags.add(tag);
		}
	}

	/**
	 * Remove uma tag
	 */
	public void removerTag(String tag) {
		if (this.tags != null) {
			this.tags.remove(tag);
		}
	}

	/**
	 * Verifica se possui uma tag específica
	 */
	public boolean hasTag(String tag) {
		return this.tags != null && this.tags.contains(tag);
	}

	/**
	 * Verifica se o cliente foi desativado
	 */
	public boolean isDesativado() {
		return this.dataDesativacao != null;
	}

	/**
	 * Limpa a data de desativação
	 */
	public void limparDataDesativacao() {
		this.dataDesativacao = null;
	}

	// Getters e Setters

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public LocalDateTime getDataDesativacao() {
		return dataDesativacao;
	}

	public void setDataDesativacao(LocalDateTime dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}
}
