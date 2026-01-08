package br.com.postech.techchallange_customer.application.service;

import java.time.LocalDateTime;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.exception.ClienteAlreadyExistsException;
import br.com.postech.techchallange_customer.domain.exception.InvalidClienteException;
import br.com.postech.techchallange_customer.domain.port.in.CriarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

public class CriarClienteService implements CriarClienteUseCase {

	private final ClienteRepositoryPort clienteRepository;

	public CriarClienteService(ClienteRepositoryPort clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public Cliente execute(Cliente cliente) {
		this.validarCliente(cliente);

		if (clienteRepository.existsByCpf(cliente.getCpfCliente())) {
			throw new ClienteAlreadyExistsException("CPF", cliente.getCpfCliente());
		}

		if (clienteRepository.existsByEmail(cliente.getEmailCliente())) {
			throw new ClienteAlreadyExistsException("e-mail", cliente.getEmailCliente());
		}

		cliente.generateClienteIdIfNeeded();
		cliente.setAtivo(true);
		cliente.setDataCadastro(LocalDateTime.now());
		cliente.setDataUltimaAtualizacao(LocalDateTime.now());
		return clienteRepository.save(cliente);
	}

	private void validarCliente(Cliente cliente) {
		if (cliente == null) {
			throw new InvalidClienteException("Cliente não pode ser nulo");
		}

		if (!cliente.isValido()) {
			throw new InvalidClienteException("Dados do cliente inválidos");
		}

		if (!cliente.isCpfValido()) {
			throw new InvalidClienteException("CPF", "formato inválido (deve conter 11 dígitos)");
		}

		if (!cliente.isEmailValido()) {
			throw new InvalidClienteException("E-mail", "formato inválido");
		}
	}
}
