package br.com.postech.techchallange_customer.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MetadataDTO {

	private String origem;
	private String canal;
	private List<String> tags;
	private String notas;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dataDesativacao;

	public MetadataDTO() {
	}

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
