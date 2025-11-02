package br.com.postech.techchallange_customer.application.service;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.exception.InvalidClienteException;
import br.com.postech.techchallange_customer.domain.port.in.AtualizarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

public class AtualizarClienteService implements AtualizarClienteUseCase {

	private final ClienteRepositoryPort clienteRepository;

	public AtualizarClienteService(ClienteRepositoryPort clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public Cliente execute(String clienteId, Cliente clienteAtualizado) {
		Cliente clienteExistente = clienteRepository.findByClienteId(clienteId)
				.orElseThrow(() -> new ClienteNotFoundException(clienteId));

		this.validarAtualizacao(clienteAtualizado);

		clienteExistente.setNomeCliente(clienteAtualizado.getNomeCliente());
		clienteExistente.setEmailCliente(clienteAtualizado.getEmailCliente());
		clienteExistente.setTelefone(clienteAtualizado.getTelefone());
		clienteExistente.setEndereco(clienteAtualizado.getEndereco());
		clienteExistente.setMetadata(clienteAtualizado.getMetadata());

		clienteExistente.updateTimestamp();

		return clienteRepository.update(clienteExistente);
	}

	private void validarAtualizacao(Cliente cliente) {
		if (cliente == null) {
			throw new InvalidClienteException("Cliente não pode ser nulo");
		}

		if (cliente.getNomeCliente() == null || cliente.getNomeCliente().isEmpty()) {
			throw new InvalidClienteException("nome", "não pode ser vazio");
		}

		if (cliente.getEmailCliente() == null || cliente.getEmailCliente().isEmpty()) {
			throw new InvalidClienteException("e-mail", "não pode ser vazio");
		}

		if (!cliente.isEmailValido()) {
			throw new InvalidClienteException("e-mail", "formato inválido");
		}
	}
}
