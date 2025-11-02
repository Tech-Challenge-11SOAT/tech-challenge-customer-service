package br.com.postech.techchallange_customer.domain.port.in;

import java.util.Optional;

import br.com.postech.techchallange_customer.domain.entity.Cliente;

/**
 * Porta de entrada (Inbound Port) - Use Case
 * Buscar cliente por diferentes critérios
 */
public interface BuscarClienteUseCase {

	/**
	 * Busca cliente por clienteId (UUID)
	 * 
	 * @param clienteId UUID do cliente
	 * @return Optional com cliente encontrado
	 */
	Optional<Cliente> porClienteId(String clienteId);

	/**
	 * Busca cliente por CPF
	 * 
	 * @param cpf CPF do cliente (11 dígitos)
	 * @return Optional com cliente encontrado
	 */
	Optional<Cliente> porCpf(String cpf);

	/**
	 * Busca cliente por e-mail
	 * 
	 * @param email e-mail do cliente
	 * @return Optional com cliente encontrado
	 */
	Optional<Cliente> porEmail(String email);
}
