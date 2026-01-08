package br.com.postech.techchallange_customer.infrastructure.persistence.mapper;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.infrastructure.persistence.document.ClienteDocument;

/**
 * Mapper para conversão entre Cliente (Domain) e ClienteDocument (MongoDB)
 */
public class ClienteDocumentMapper {

	private ClienteDocumentMapper() {
		// Construtor privado para evitar instanciação
	}

	/**
	 * Converte de Domain Entity para Document
	 */
	public static ClienteDocument toDocument(Cliente cliente) {
		if (cliente == null) {
			return null;
		}

		ClienteDocument document = new ClienteDocument();
		document.setId(cliente.getId());
		document.setClienteId(cliente.getClienteId());
		document.setNomeCliente(cliente.getNomeCliente());
		document.setEmailCliente(cliente.getEmailCliente());
		document.setCpfCliente(cliente.getCpfCliente());
		document.setTelefone(cliente.getTelefone());
		document.setAtivo(cliente.getAtivo());
		document.setDataCadastro(cliente.getDataCadastro());
		document.setDataUltimaAtualizacao(cliente.getDataUltimaAtualizacao());
		document.setVersao(cliente.getVersao());

		// Mapear endereco
		if (cliente.getEndereco() != null) {
			document.setEndereco(toEnderecoDocument(cliente.getEndereco()));
		}

		// Mapear metadata
		if (cliente.getMetadata() != null) {
			document.setMetadata(toMetadataDocument(cliente.getMetadata()));
		}

		return document;
	}

	/**
	 * Converte de Document para Domain Entity
	 */
	public static Cliente toDomain(ClienteDocument document) {
		if (document == null) {
			return null;
		}

		Cliente cliente = new Cliente();
		cliente.setId(document.getId());
		cliente.setClienteId(document.getClienteId());
		cliente.setNomeCliente(document.getNomeCliente());
		cliente.setEmailCliente(document.getEmailCliente());
		cliente.setCpfCliente(document.getCpfCliente());
		cliente.setTelefone(document.getTelefone());
		cliente.setAtivo(document.getAtivo());
		cliente.setDataCadastro(document.getDataCadastro());
		cliente.setDataUltimaAtualizacao(document.getDataUltimaAtualizacao());
		cliente.setVersao(document.getVersao());

		// Mapear endereco
		if (document.getEndereco() != null) {
			cliente.setEndereco(toEnderecoDomain(document.getEndereco()));
		}

		// Mapear metadata
		if (document.getMetadata() != null) {
			cliente.setMetadata(toMetadataDomain(document.getMetadata()));
		}

		return cliente;
	}

	/**
	 * Converte Endereco Domain para Document
	 */
	private static ClienteDocument.EnderecoDocument toEnderecoDocument(Endereco endereco) {
		if (endereco == null) {
			return null;
		}

		ClienteDocument.EnderecoDocument document = new ClienteDocument.EnderecoDocument();
		document.setRua(endereco.getRua());
		document.setNumero(endereco.getNumero());
		document.setComplemento(endereco.getComplemento());
		document.setBairro(endereco.getBairro());
		document.setCidade(endereco.getCidade());
		document.setEstado(endereco.getEstado());
		document.setCep(endereco.getCep());

		return document;
	}

	/**
	 * Converte EnderecoDocument para Domain
	 */
	private static Endereco toEnderecoDomain(ClienteDocument.EnderecoDocument document) {
		if (document == null) {
			return null;
		}

		Endereco endereco = new Endereco();
		endereco.setRua(document.getRua());
		endereco.setNumero(document.getNumero());
		endereco.setComplemento(document.getComplemento());
		endereco.setBairro(document.getBairro());
		endereco.setCidade(document.getCidade());
		endereco.setEstado(document.getEstado());
		endereco.setCep(document.getCep());

		return endereco;
	}

	/**
	 * Converte Metadata Domain para Document
	 */
	private static ClienteDocument.MetadataDocument toMetadataDocument(Metadata metadata) {
		if (metadata == null) {
			return null;
		}

		ClienteDocument.MetadataDocument document = new ClienteDocument.MetadataDocument();
		document.setOrigem(metadata.getOrigem());
		document.setCanal(metadata.getCanal());
		document.setTags(metadata.getTags());
		document.setNotas(metadata.getNotas());
		document.setDataDesativacao(metadata.getDataDesativacao());

		return document;
	}

	/**
	 * Converte MetadataDocument para Domain
	 */
	private static Metadata toMetadataDomain(ClienteDocument.MetadataDocument document) {
		if (document == null) {
			return null;
		}

		Metadata metadata = new Metadata();
		metadata.setOrigem(document.getOrigem());
		metadata.setCanal(document.getCanal());
		metadata.setTags(document.getTags());
		metadata.setNotas(document.getNotas());
		metadata.setDataDesativacao(document.getDataDesativacao());

		return metadata;
	}
}
