package br.com.postech.techchallange_customer.domain.port.in;

import java.util.List;

import br.com.postech.techchallange_customer.domain.entity.Cliente;

/**
 * Porta de entrada (Inbound Port) - Use Case
 * Listar clientes com diferentes filtros
 */
public interface ListarClientesUseCase {

	/**
	 * Lista todos os clientes
	 */
	List<Cliente> todos();

	/**
	 * Lista apenas clientes ativos
	 */
	List<Cliente> ativos();

	/**
	 * Lista apenas clientes inativos
	 */
	List<Cliente> inativos();

	/**
	 * Lista clientes por cidade
	 */
	List<Cliente> porCidade(String cidade);

	/**
	 * Lista clientes VIP ativos
	 */
	List<Cliente> vipAtivos();

	/**
	 * Lista clientes por cidade e estado
	 */
	List<Cliente> porCidadeEEstado(String cidade, String estado);

	/**
	 * Lista clientes por tag
	 */
	List<Cliente> porTag(String tag);

	/**
	 * Conta clientes ativos
	 */
	long contarAtivos();
}
