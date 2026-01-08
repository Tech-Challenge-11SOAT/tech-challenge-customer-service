package br.com.postech.techchallange_customer.application.service;

import java.util.List;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.port.in.ListarClientesUseCase;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

public class ListarClientesService implements ListarClientesUseCase {

	private final ClienteRepositoryPort clienteRepository;

	public ListarClientesService(ClienteRepositoryPort clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public List<Cliente> todos() {
		return clienteRepository.findAll();
	}

	@Override
	public List<Cliente> ativos() {
		return clienteRepository.findAllAtivos();
	}

	@Override
	public List<Cliente> inativos() {
		return clienteRepository.findAllInativos();
	}

	@Override
	public List<Cliente> porCidade(String cidade) {
		return clienteRepository.findClientesAtivosPorCidade(cidade);
	}

	@Override
	public List<Cliente> vipAtivos() {
		return clienteRepository.findClientesVipAtivos();
	}

	@Override
	public List<Cliente> porCidadeEEstado(String cidade, String estado) {
		return clienteRepository.findByCidadeAndEstado(cidade, estado);
	}

	@Override
	public List<Cliente> porTag(String tag) {
		return clienteRepository.findByTag(tag);
	}

	@Override
	public long contarAtivos() {
		return clienteRepository.countAtivos();
	}
}
