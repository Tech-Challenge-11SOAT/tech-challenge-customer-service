package br.com.postech.techchallange_customer.domain.exception;

/**
 * Exceção quando um cliente não é encontrado
 */
public class ClienteNotFoundException extends DomainException {

	public ClienteNotFoundException(String clienteId) {
		super("Cliente não encontrado com ID: " + clienteId);
	}

	public ClienteNotFoundException(String campo, String valor) {
		super(String.format("Cliente não encontrado com %s: %s", campo, valor));
	}
}
