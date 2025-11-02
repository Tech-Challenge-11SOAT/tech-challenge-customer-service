package br.com.postech.techchallange_customer.infrastructure.persistence.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Documento MongoDB para Cliente
 * Collection: clientes
 */
@Document(collection = "clientes")
public class ClienteDocument {

	@Id
	private String id;

	@Indexed(unique = true)
	private String clienteId;

	@TextIndexed
	private String nomeCliente;

	@Indexed(unique = true)
	private String emailCliente;

	@Indexed(unique = true)
	private String cpfCliente;

	private String telefone;

	private EnderecoDocument endereco;

	private Boolean ativo;

	private LocalDateTime dataCadastro;

	private LocalDateTime dataUltimaAtualizacao;

	@Version
	private Integer versao;

	private MetadataDocument metadata;

	// Construtores

	public ClienteDocument() {
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

	public EnderecoDocument getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDocument endereco) {
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

	public MetadataDocument getMetadata() {
		return metadata;
	}

	public void setMetadata(MetadataDocument metadata) {
		this.metadata = metadata;
	}

	/**
	 * Classe interna para Endereço
	 */
	public static class EnderecoDocument {
		private String rua;
		private String numero;
		private String complemento;
		private String bairro;
		private String cidade;
		private String estado;
		private String cep;

		public EnderecoDocument() {
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

	/**
	 * Classe interna para Metadata
	 */
	public static class MetadataDocument {
		private String origem;
		private String canal;
		private List<String> tags;
		private String notas;
		private LocalDateTime dataDesativacao;

		public MetadataDocument() {
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
}
