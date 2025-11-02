package br.com.postech.techchallange_customer.domain.exception;

/**
 * Exceção quando há conflito de dados únicos (CPF, Email já cadastrados)
 */
public class ClienteAlreadyExistsException extends DomainException {

	public ClienteAlreadyExistsException(String campo, String valor) {
		super(String.format("Já existe um cliente cadastrado com %s: %s", campo, valor));
	}
}
