package br.com.postech.techchallange_customer.domain.port.in;

import br.com.postech.techchallange_customer.domain.entity.Cliente;

/**
 * Porta de entrada (Inbound Port) - Use Case
 * Atualizar dados de um cliente existente
 */
public interface AtualizarClienteUseCase {

	/**
	 * Atualiza os dados de um cliente
	 * 
	 * @param clienteId         UUID do cliente a ser atualizado
	 * @param clienteAtualizado dados atualizados do cliente
	 * @return cliente atualizado
	 * @throws br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException se
	 *                                                                                         cliente
	 *                                                                                         não
	 *                                                                                         existir
	 * @throws br.com.postech.techchallange_customer.domain.exception.InvalidClienteException  se
	 *                                                                                         dados
	 *                                                                                         forem
	 *                                                                                         inválidos
	 */
	Cliente execute(String clienteId, Cliente clienteAtualizado);
}
