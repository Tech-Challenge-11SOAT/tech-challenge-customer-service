package br.com.postech.techchallange_customer.application.service;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.port.in.DesativarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

public class DesativarClienteService implements DesativarClienteUseCase {

	private final ClienteRepositoryPort clienteRepository;

	public DesativarClienteService(ClienteRepositoryPort clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public void execute(String clienteId) {
		Cliente cliente = clienteRepository.findByClienteId(clienteId)
				.orElseThrow(() -> new ClienteNotFoundException(clienteId));

		cliente.desativar();

		clienteRepository.update(cliente);
	}
}
