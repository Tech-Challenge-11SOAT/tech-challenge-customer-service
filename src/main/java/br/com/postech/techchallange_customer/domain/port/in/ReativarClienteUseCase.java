package br.com.postech.techchallange_customer.domain.port.in;

/**
 * Porta de entrada (Inbound Port) - Use Case
 * Reativar um cliente desativado
 */
public interface ReativarClienteUseCase {

	/**
	 * Reativa um cliente previamente desativado
	 * 
	 * @param clienteId UUID do cliente
	 * @throws br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException se
	 *                                                                                         cliente
	 *                                                                                         não
	 *                                                                                         existir
	 */
	void execute(String clienteId);
}
