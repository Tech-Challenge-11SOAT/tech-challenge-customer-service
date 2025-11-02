package br.com.postech.techchallange_customer.domain.exception;

/**
 * Exceção para validações de domínio
 */
public class InvalidClienteException extends DomainException {

	public InvalidClienteException(String message) {
		super(message);
	}

	public InvalidClienteException(String campo, String motivo) {
		super(String.format("Campo '%s' inválido: %s", campo, motivo));
	}
}
