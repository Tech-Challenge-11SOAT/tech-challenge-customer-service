package br.com.postech.techchallange_customer.domain.port.in;

/**
 * Porta de entrada (Inbound Port) - Use Case
 * Desativar um cliente (soft delete)
 */
public interface DesativarClienteUseCase {

	/**
	 * Desativa um cliente (soft delete)
	 * 
	 * @param clienteId UUID do cliente
	 * @throws br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException se
	 *                                                                                         cliente
	 *                                                                                         não
	 *                                                                                         existir
	 */
	void execute(String clienteId);
}
