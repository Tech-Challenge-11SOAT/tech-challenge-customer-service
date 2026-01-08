package br.com.postech.techchallange_customer.infrastructure.persistence.adapter;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;
import br.com.postech.techchallange_customer.infrastructure.persistence.document.ClienteDocument;
import br.com.postech.techchallange_customer.infrastructure.persistence.mapper.ClienteDocumentMapper;
import br.com.postech.techchallange_customer.infrastructure.persistence.repository.ClienteMongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistência MongoDB
 * Implementa a porta de saída ClienteRepositoryPort
 */
@Component
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

	private final ClienteMongoRepository mongoRepository;

	public ClienteRepositoryAdapter(ClienteMongoRepository mongoRepository) {
		this.mongoRepository = mongoRepository;
	}

	@Override
	public Cliente save(Cliente cliente) {
		ClienteDocument document = ClienteDocumentMapper.toDocument(cliente);
		ClienteDocument saved = mongoRepository.save(document);
		return ClienteDocumentMapper.toDomain(saved);
	}

	@Override
	public Cliente update(Cliente cliente) {
		ClienteDocument document = ClienteDocumentMapper.toDocument(cliente);
		ClienteDocument updated = mongoRepository.save(document);
		return ClienteDocumentMapper.toDomain(updated);
	}

	@Override
	public Optional<Cliente> findByClienteId(String clienteId) {
		return mongoRepository.findByClienteId(clienteId)
				.map(ClienteDocumentMapper::toDomain);
	}

	@Override
	public Optional<Cliente> findByCpf(String cpf) {
		return mongoRepository.findByCpfCliente(cpf)
				.map(ClienteDocumentMapper::toDomain);
	}

	@Override
	public Optional<Cliente> findByEmail(String email) {
		return mongoRepository.findByEmailCliente(email)
				.map(ClienteDocumentMapper::toDomain);
	}

	@Override
	public Optional<Cliente> findById(String id) {
		return mongoRepository.findById(id)
				.map(ClienteDocumentMapper::toDomain);
	}

	@Override
	public List<Cliente> findAll() {
		return mongoRepository.findAll().stream()
				.map(ClienteDocumentMapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Cliente> findAllAtivos() {
		return mongoRepository.findByAtivoTrue().stream()
				.map(ClienteDocumentMapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Cliente> findAllInativos() {
		return mongoRepository.findByAtivoFalse().stream()
				.map(ClienteDocumentMapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Cliente> findByCidadeAndEstado(String cidade, String estado) {
		return mongoRepository.findByEnderecoCidadeAndEnderecoEstado(cidade, estado).stream()
				.map(ClienteDocumentMapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Cliente> findByTag(String tag) {
		return mongoRepository.findByMetadataTagsContaining(tag).stream()
				.map(ClienteDocumentMapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Cliente> findClientesAtivosPorCidade(String cidade) {
		return mongoRepository.findClientesAtivosPorCidade(cidade).stream()
				.map(ClienteDocumentMapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Cliente> findClientesVipAtivos() {
		return mongoRepository.findClientesVipAtivos().stream()
				.map(ClienteDocumentMapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public long countAtivos() {
		return mongoRepository.countByAtivoTrue();
	}

	@Override
	public boolean existsByCpf(String cpf) {
		return mongoRepository.existsByCpfCliente(cpf);
	}

	@Override
	public boolean existsByEmail(String email) {
		return mongoRepository.existsByEmailCliente(email);
	}

	@Override
	public void delete(Cliente cliente) {
		ClienteDocument document = ClienteDocumentMapper.toDocument(cliente);
		mongoRepository.delete(document);
	}

	@Override
	public void deleteByClienteId(String clienteId) {
		mongoRepository.deleteByClienteId(clienteId);
	}
}
