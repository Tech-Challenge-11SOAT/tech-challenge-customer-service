package br.com.postech.techchallange_customer.application.service;

import java.util.Optional;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.port.in.BuscarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

public class BuscarClienteService implements BuscarClienteUseCase {

	private final ClienteRepositoryPort clienteRepository;

	public BuscarClienteService(ClienteRepositoryPort clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public Optional<Cliente> porClienteId(String clienteId) {
		return clienteRepository.findByClienteId(clienteId);
	}

	@Override
	public Optional<Cliente> porCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

	@Override
	public Optional<Cliente> porEmail(String email) {
		return clienteRepository.findByEmail(email);
	}
}
