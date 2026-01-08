package br.com.postech.techchallange_customer.infrastructure.config;

import br.com.postech.techchallange_customer.application.service.*;
import br.com.postech.techchallange_customer.domain.port.in.*;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de injeção de dependências para casos de uso
 * Conecta as camadas da arquitetura hexagonal
 */
@Configuration
public class UseCaseConfig {

	/**
	 * Bean para criar cliente
	 */
	@Bean
	public CriarClienteUseCase criarClienteUseCase(ClienteRepositoryPort clienteRepository) {
		return new CriarClienteService(clienteRepository);
	}

	/**
	 * Bean para buscar cliente
	 */
	@Bean
	public BuscarClienteUseCase buscarClienteUseCase(ClienteRepositoryPort clienteRepository) {
		return new BuscarClienteService(clienteRepository);
	}

	/**
	 * Bean para listar clientes
	 */
	@Bean
	public ListarClientesUseCase listarClientesUseCase(ClienteRepositoryPort clienteRepository) {
		return new ListarClientesService(clienteRepository);
	}

	/**
	 * Bean para atualizar cliente
	 */
	@Bean
	public AtualizarClienteUseCase atualizarClienteUseCase(ClienteRepositoryPort clienteRepository) {
		return new AtualizarClienteService(clienteRepository);
	}

	/**
	 * Bean para desativar cliente
	 */
	@Bean
	public DesativarClienteUseCase desativarClienteUseCase(ClienteRepositoryPort clienteRepository) {
		return new DesativarClienteService(clienteRepository);
	}

	/**
	 * Bean para reativar cliente
	 */
	@Bean
	public ReativarClienteUseCase reativarClienteUseCase(ClienteRepositoryPort clienteRepository) {
		return new ReativarClienteService(clienteRepository);
	}

	/**
	 * Bean para deletar cliente
	 */
	@Bean
	public DeletarClienteUseCase deletarClienteUseCase(ClienteRepositoryPort clienteRepository) {
		return new DeletarClienteService(clienteRepository);
	}
}
