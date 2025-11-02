package br.com.postech.techchallange_customer.domain.port.in;

/**
 * Porta de entrada (Inbound Port) - Use Case
 * Deletar permanentemente um cliente (hard delete)
 */
public interface DeletarClienteUseCase {

	/**
	 * Deleta permanentemente um cliente do sistema
	 * CUIDADO: Esta operação é irreversível!
	 * 
	 * @param clienteId UUID do cliente
	 * @throws br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException se
	 *                                                                                         cliente
	 *                                                                                         não
	 *                                                                                         existir
	 */
	void execute(String clienteId);
}
