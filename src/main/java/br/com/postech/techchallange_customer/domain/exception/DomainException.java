package br.com.postech.techchallange_customer.domain.exception;

/**
 * Exceção de domínio para erros de negócio
 */
public class DomainException extends RuntimeException {

	public DomainException(String message) {
		super(message);
	}

	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}
}
