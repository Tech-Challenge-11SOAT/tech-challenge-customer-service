package br.com.postech.techchallange_customer.application.mapper;

import br.com.postech.techchallange_customer.application.dto.ClienteDTO;
import br.com.postech.techchallange_customer.application.dto.EnderecoDTO;
import br.com.postech.techchallange_customer.application.dto.MetadataDTO;
import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;

public class ClienteMapper {

	/**
	 * Converte de Domain Entity para DTO
	 */
	public static ClienteDTO toDTO(Cliente cliente) {
		if (cliente == null) {
			return null;
		}

		ClienteDTO dto = new ClienteDTO();
		dto.setId(cliente.getId());
		dto.setClienteId(cliente.getClienteId());
		dto.setNomeCliente(cliente.getNomeCliente());
		dto.setEmailCliente(cliente.getEmailCliente());
		dto.setCpfCliente(cliente.getCpfCliente());
		dto.setTelefone(cliente.getTelefone());
		dto.setAtivo(cliente.getAtivo());
		dto.setDataCadastro(cliente.getDataCadastro());
		dto.setDataUltimaAtualizacao(cliente.getDataUltimaAtualizacao());
		dto.setVersao(cliente.getVersao());

		if (cliente.getEndereco() != null) {
			dto.setEndereco(toEnderecoDTO(cliente.getEndereco()));
		}

		if (cliente.getMetadata() != null) {
			dto.setMetadata(toMetadataDTO(cliente.getMetadata()));
		}

		return dto;
	}

	/**
	 * Converte de DTO para Domain Entity
	 */
	public static Cliente toDomain(ClienteDTO dto) {
		if (dto == null) {
			return null;
		}

		Cliente cliente = new Cliente();
		cliente.setId(dto.getId());
		cliente.setClienteId(dto.getClienteId());
		cliente.setNomeCliente(dto.getNomeCliente());
		cliente.setEmailCliente(dto.getEmailCliente());
		cliente.setCpfCliente(dto.getCpfCliente());
		cliente.setTelefone(dto.getTelefone());
		cliente.setAtivo(dto.getAtivo());
		cliente.setDataCadastro(dto.getDataCadastro());
		cliente.setDataUltimaAtualizacao(dto.getDataUltimaAtualizacao());
		cliente.setVersao(dto.getVersao());

		if (dto.getEndereco() != null) {
			cliente.setEndereco(toEnderecoDomain(dto.getEndereco()));
		}

		if (dto.getMetadata() != null) {
			cliente.setMetadata(toMetadataDomain(dto.getMetadata()));
		}

		return cliente;
	}

	/**
	 * Converte Endereco Domain para DTO
	 */
	public static EnderecoDTO toEnderecoDTO(Endereco endereco) {
		if (endereco == null) {
			return null;
		}

		EnderecoDTO dto = new EnderecoDTO();
		dto.setRua(endereco.getRua());
		dto.setNumero(endereco.getNumero());
		dto.setComplemento(endereco.getComplemento());
		dto.setBairro(endereco.getBairro());
		dto.setCidade(endereco.getCidade());
		dto.setEstado(endereco.getEstado());
		dto.setCep(endereco.getCep());

		return dto;
	}

	/**
	 * Converte EnderecoDTO para Domain
	 */
	public static Endereco toEnderecoDomain(EnderecoDTO dto) {
		if (dto == null) {
			return null;
		}

		Endereco endereco = new Endereco();
		endereco.setRua(dto.getRua());
		endereco.setNumero(dto.getNumero());
		endereco.setComplemento(dto.getComplemento());
		endereco.setBairro(dto.getBairro());
		endereco.setCidade(dto.getCidade());
		endereco.setEstado(dto.getEstado());
		endereco.setCep(dto.getCep());

		return endereco;
	}

	/**
	 * Converte Metadata Domain para DTO
	 */
	public static MetadataDTO toMetadataDTO(Metadata metadata) {
		if (metadata == null) {
			return null;
		}

		MetadataDTO dto = new MetadataDTO();
		dto.setOrigem(metadata.getOrigem());
		dto.setCanal(metadata.getCanal());
		dto.setTags(metadata.getTags());
		dto.setNotas(metadata.getNotas());
		dto.setDataDesativacao(metadata.getDataDesativacao());

		return dto;
	}

	/**
	 * Converte MetadataDTO para Domain
	 */
	public static Metadata toMetadataDomain(MetadataDTO dto) {
		if (dto == null) {
			return null;
		}

		Metadata metadata = new Metadata();
		metadata.setOrigem(dto.getOrigem());
		metadata.setCanal(dto.getCanal());
		metadata.setTags(dto.getTags());
		metadata.setNotas(dto.getNotas());
		metadata.setDataDesativacao(dto.getDataDesativacao());

		return metadata;
	}
}
