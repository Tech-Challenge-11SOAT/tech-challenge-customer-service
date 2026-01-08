package br.com.postech.techchallange_customer.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallange_customer.infrastructure.persistence.document.ClienteDocument;

/**
 * Repository MongoDB para Cliente
 */
@Repository
public interface ClienteMongoRepository extends MongoRepository<ClienteDocument, String> {

	/**
	 * Busca cliente por clienteId (UUID)
	 */
	Optional<ClienteDocument> findByClienteId(String clienteId);

	/**
	 * Busca cliente por CPF
	 */
	Optional<ClienteDocument> findByCpfCliente(String cpfCliente);

	/**
	 * Busca cliente por e-mail
	 */
	Optional<ClienteDocument> findByEmailCliente(String emailCliente);

	/**
	 * Lista clientes ativos
	 */
	List<ClienteDocument> findByAtivoTrue();

	/**
	 * Lista clientes inativos
	 */
	List<ClienteDocument> findByAtivoFalse();

	/**
	 * Lista clientes por cidade e estado
	 */
	List<ClienteDocument> findByEnderecoCidadeAndEnderecoEstado(String cidade, String estado);

	/**
	 * Busca clientes por tag
	 */
	List<ClienteDocument> findByMetadataTagsContaining(String tag);

	/**
	 * Busca clientes ativos por cidade
	 */
	@Query("{ 'ativo': true, 'endereco.cidade': ?0 }")
	List<ClienteDocument> findClientesAtivosPorCidade(String cidade);

	/**
	 * Busca clientes VIP ativos
	 */
	@Query("{ 'ativo': true, 'metadata.tags': 'vip' }")
	List<ClienteDocument> findClientesVipAtivos();

	/**
	 * Conta clientes ativos
	 */
	long countByAtivoTrue();

	/**
	 * Verifica se existe cliente com CPF
	 */
	boolean existsByCpfCliente(String cpfCliente);

	/**
	 * Verifica se existe cliente com e-mail
	 */
	boolean existsByEmailCliente(String emailCliente);

	/**
	 * Deleta por clienteId
	 */
	void deleteByClienteId(String clienteId);
}
