package br.com.postech.techchallange_customer.application.service;

import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.port.in.DeletarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

public class DeletarClienteService implements DeletarClienteUseCase {

	private final ClienteRepositoryPort clienteRepository;

	public DeletarClienteService(ClienteRepositoryPort clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public void execute(String clienteId) {
		if (!clienteRepository.findByClienteId(clienteId).isPresent()) {
			throw new ClienteNotFoundException(clienteId);
		}

		clienteRepository.deleteByClienteId(clienteId);
	}
}
