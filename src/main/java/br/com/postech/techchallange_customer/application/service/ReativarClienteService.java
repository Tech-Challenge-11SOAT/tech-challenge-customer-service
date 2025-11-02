package br.com.postech.techchallange_customer.application.service;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.port.in.ReativarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

public class ReativarClienteService implements ReativarClienteUseCase {

	private final ClienteRepositoryPort clienteRepository;

	public ReativarClienteService(ClienteRepositoryPort clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public void execute(String clienteId) {
		Cliente cliente = clienteRepository.findByClienteId(clienteId)
				.orElseThrow(() -> new ClienteNotFoundException(clienteId));

		cliente.reativar();

		clienteRepository.update(cliente);
	}
}
