package br.com.postech.techchallange_customer.domain.port.out;

import java.util.List;
import java.util.Optional;

import br.com.postech.techchallange_customer.domain.entity.Cliente;

/**
 * Porta de saída (Outbound Port) para persistência de Cliente
 * Interface que define o contrato para o repositório
 * Implementada pela camada de infraestrutura
 */
public interface ClienteRepositoryPort {

	/**
	 * Salva um novo cliente
	 */
	Cliente save(Cliente cliente);

	/**
	 * Atualiza um cliente existente
	 */
	Cliente update(Cliente cliente);

	/**
	 * Busca cliente por clienteId (UUID)
	 */
	Optional<Cliente> findByClienteId(String clienteId);

	/**
	 * Busca cliente por CPF
	 */
	Optional<Cliente> findByCpf(String cpf);

	/**
	 * Busca cliente por e-mail
	 */
	Optional<Cliente> findByEmail(String email);

	/**
	 * Busca cliente por ID do MongoDB
	 */
	Optional<Cliente> findById(String id);

	/**
	 * Lista todos os clientes
	 */
	List<Cliente> findAll();

	/**
	 * Lista apenas clientes ativos
	 */
	List<Cliente> findAllAtivos();

	/**
	 * Lista apenas clientes inativos
	 */
	List<Cliente> findAllInativos();

	/**
	 * Busca clientes por cidade e estado
	 */
	List<Cliente> findByCidadeAndEstado(String cidade, String estado);

	/**
	 * Busca clientes por tag
	 */
	List<Cliente> findByTag(String tag);

	/**
	 * Busca clientes ativos por cidade
	 */
	List<Cliente> findClientesAtivosPorCidade(String cidade);

	/**
	 * Busca clientes VIP ativos
	 */
	List<Cliente> findClientesVipAtivos();

	/**
	 * Conta clientes ativos
	 */
	long countAtivos();

	/**
	 * Verifica se existe cliente com CPF
	 */
	boolean existsByCpf(String cpf);

	/**
	 * Verifica se existe cliente com e-mail
	 */
	boolean existsByEmail(String email);

	/**
	 * Deleta cliente permanentemente
	 */
	void delete(Cliente cliente);

	/**
	 * Deleta cliente por clienteId
	 */
	void deleteByClienteId(String clienteId);
}
