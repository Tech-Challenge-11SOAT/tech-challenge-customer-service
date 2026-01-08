package br.com.postech.techchallange_customer.domain.port.in;

import br.com.postech.techchallange_customer.domain.entity.Cliente;

/**
 * Porta de entrada (Inbound Port) - Use Case
 * Criar um novo cliente
 */
public interface CriarClienteUseCase {

	/**
	 * Cria um novo cliente no sistema
	 * 
	 * @param cliente dados do cliente a ser criado
	 * @return cliente criado com ID gerado
	 * @throws br.com.postech.techchallange_customer.domain.exception.ClienteAlreadyExistsException se
	 *                                                                                              CPF
	 *                                                                                              ou
	 *                                                                                              email
	 *                                                                                              já
	 *                                                                                              existir
	 * @throws br.com.postech.techchallange_customer.domain.exception.InvalidClienteException       se
	 *                                                                                              dados
	 *                                                                                              forem
	 *                                                                                              inválidos
	 */
	Cliente execute(Cliente cliente);
}
