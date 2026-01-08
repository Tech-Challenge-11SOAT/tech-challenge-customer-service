package br.com.postech.techchallange_customer.application.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListarClientesService Tests")
class ListarClientesServiceTest {

	@Mock
	private ClienteRepositoryPort clienteRepository;

	private ListarClientesService service;

	@BeforeEach
	void setUp() {
		service = new ListarClientesService(clienteRepository);
	}

	// Testes para método todos()

	@Test
	@DisplayName("Deve listar todos os clientes")
	void deveListarTodosOsClientes() {
		Cliente cliente1 = criarCliente("cliente-1", "João Silva", true);
		Cliente cliente2 = criarCliente("cliente-2", "Maria Santos", true);
		Cliente cliente3 = criarCliente("cliente-3", "Pedro Costa", false);

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2, cliente3);
		when(clienteRepository.findAll()).thenReturn(clientes);

		List<Cliente> resultado = service.todos();

		assertNotNull(resultado);
		assertEquals(3, resultado.size());
		assertTrue(resultado.contains(cliente1));
		assertTrue(resultado.contains(cliente2));
		assertTrue(resultado.contains(cliente3));
		verify(clienteRepository).findAll();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes")
	void deveRetornarListaVaziaQuandoNaoHaClientes() {
		when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.todos();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(clienteRepository).findAll();
	}

	@Test
	@DisplayName("Deve chamar findAll exatamente uma vez")
	void deveChamarFindAllExatamenteUmaVez() {
		when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

		service.todos();

		verify(clienteRepository, times(1)).findAll();
		verifyNoMoreInteractions(clienteRepository);
	}

	// Testes para método ativos()

	@Test
	@DisplayName("Deve listar apenas clientes ativos")
	void deveListarApenasClientesAtivos() {
		Cliente cliente1 = criarCliente("cliente-1", "João Silva", true);
		Cliente cliente2 = criarCliente("cliente-2", "Maria Santos", true);

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		when(clienteRepository.findAllAtivos()).thenReturn(clientes);

		List<Cliente> resultado = service.ativos();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertTrue(resultado.stream().allMatch(Cliente::getAtivo));
		verify(clienteRepository).findAllAtivos();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes ativos")
	void deveRetornarListaVaziaQuandoNaoHaClientesAtivos() {
		when(clienteRepository.findAllAtivos()).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.ativos();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(clienteRepository).findAllAtivos();
	}

	@Test
	@DisplayName("Deve chamar findAllAtivos exatamente uma vez")
	void deveChamarFindAllAtivosExatamenteUmaVez() {
		when(clienteRepository.findAllAtivos()).thenReturn(Collections.emptyList());

		service.ativos();

		verify(clienteRepository, times(1)).findAllAtivos();
		verifyNoMoreInteractions(clienteRepository);
	}

	// Testes para método inativos()

	@Test
	@DisplayName("Deve listar apenas clientes inativos")
	void deveListarApenasClientesInativos() {
		Cliente cliente1 = criarCliente("cliente-1", "João Silva", false);
		Cliente cliente2 = criarCliente("cliente-2", "Maria Santos", false);

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		when(clienteRepository.findAllInativos()).thenReturn(clientes);

		List<Cliente> resultado = service.inativos();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertTrue(resultado.stream().noneMatch(Cliente::getAtivo));
		verify(clienteRepository).findAllInativos();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes inativos")
	void deveRetornarListaVaziaQuandoNaoHaClientesInativos() {
		when(clienteRepository.findAllInativos()).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.inativos();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(clienteRepository).findAllInativos();
	}

	@Test
	@DisplayName("Deve chamar findAllInativos exatamente uma vez")
	void deveChamarFindAllInativosExatamenteUmaVez() {
		when(clienteRepository.findAllInativos()).thenReturn(Collections.emptyList());

		service.inativos();

		verify(clienteRepository, times(1)).findAllInativos();
		verifyNoMoreInteractions(clienteRepository);
	}

	// Testes para método porCidade()

	@Test
	@DisplayName("Deve listar clientes ativos por cidade")
	void deveListarClientesAtivosPorCidade() {
		String cidade = "São Paulo";
		Cliente cliente1 = criarClienteComEndereco("cliente-1", "João Silva", cidade, "SP", true);
		Cliente cliente2 = criarClienteComEndereco("cliente-2", "Maria Santos", cidade, "SP", true);

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		when(clienteRepository.findClientesAtivosPorCidade(cidade)).thenReturn(clientes);

		List<Cliente> resultado = service.porCidade(cidade);

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertTrue(resultado.stream()
				.allMatch(c -> c.getEndereco() != null && cidade.equals(c.getEndereco().getCidade())));
		verify(clienteRepository).findClientesAtivosPorCidade(cidade);
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes na cidade")
	void deveRetornarListaVaziaQuandoNaoHaClientesNaCidade() {
		String cidade = "Rio de Janeiro";
		when(clienteRepository.findClientesAtivosPorCidade(cidade)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porCidade(cidade);

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(clienteRepository).findClientesAtivosPorCidade(cidade);
	}

	@Test
	@DisplayName("Deve aceitar cidade null ao buscar por cidade")
	void deveAceitarCidadeNullAoBuscarPorCidade() {
		when(clienteRepository.findClientesAtivosPorCidade(null)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porCidade(null);

		assertNotNull(resultado);
		verify(clienteRepository).findClientesAtivosPorCidade(null);
	}

	@Test
	@DisplayName("Deve aceitar cidade vazia ao buscar por cidade")
	void deveAceitarCidadeVaziaAoBuscarPorCidade() {
		String cidade = "";
		when(clienteRepository.findClientesAtivosPorCidade(cidade)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porCidade(cidade);

		assertNotNull(resultado);
		verify(clienteRepository).findClientesAtivosPorCidade(cidade);
	}

	// Testes para método vipAtivos()

	@Test
	@DisplayName("Deve listar clientes VIP ativos")
	void deveListarClientesVipAtivos() {
		Cliente cliente1 = criarClienteVip("cliente-1", "João Silva", true);
		Cliente cliente2 = criarClienteVip("cliente-2", "Maria Santos", true);

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		when(clienteRepository.findClientesVipAtivos()).thenReturn(clientes);

		List<Cliente> resultado = service.vipAtivos();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertTrue(resultado.stream().allMatch(Cliente::getAtivo));
		verify(clienteRepository).findClientesVipAtivos();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes VIP ativos")
	void deveRetornarListaVaziaQuandoNaoHaClientesVipAtivos() {
		when(clienteRepository.findClientesVipAtivos()).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.vipAtivos();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(clienteRepository).findClientesVipAtivos();
	}

	@Test
	@DisplayName("Deve chamar findClientesVipAtivos exatamente uma vez")
	void deveChamarFindClientesVipAtivosExatamenteUmaVez() {
		when(clienteRepository.findClientesVipAtivos()).thenReturn(Collections.emptyList());

		service.vipAtivos();

		verify(clienteRepository, times(1)).findClientesVipAtivos();
		verifyNoMoreInteractions(clienteRepository);
	}

	// Testes para método porCidadeEEstado()

	@Test
	@DisplayName("Deve listar clientes por cidade e estado")
	void deveListarClientesPorCidadeEEstado() {
		String cidade = "São Paulo";
		String estado = "SP";
		Cliente cliente1 = criarClienteComEndereco("cliente-1", "João Silva", cidade, estado, true);
		Cliente cliente2 = criarClienteComEndereco("cliente-2", "Maria Santos", cidade, estado, true);

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		when(clienteRepository.findByCidadeAndEstado(cidade, estado)).thenReturn(clientes);

		List<Cliente> resultado = service.porCidadeEEstado(cidade, estado);

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertTrue(resultado.stream().allMatch(c -> c.getEndereco() != null &&
				cidade.equals(c.getEndereco().getCidade()) &&
				estado.equals(c.getEndereco().getEstado())));
		verify(clienteRepository).findByCidadeAndEstado(cidade, estado);
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes na cidade e estado")
	void deveRetornarListaVaziaQuandoNaoHaClientesNaCidadeEEstado() {
		String cidade = "Rio de Janeiro";
		String estado = "RJ";
		when(clienteRepository.findByCidadeAndEstado(cidade, estado)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porCidadeEEstado(cidade, estado);

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(clienteRepository).findByCidadeAndEstado(cidade, estado);
	}

	@Test
	@DisplayName("Deve aceitar cidade e estado null ao buscar")
	void deveAceitarCidadeEEstadoNullAoBuscar() {
		when(clienteRepository.findByCidadeAndEstado(null, null)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porCidadeEEstado(null, null);

		assertNotNull(resultado);
		verify(clienteRepository).findByCidadeAndEstado(null, null);
	}

	@Test
	@DisplayName("Deve aceitar apenas cidade null ao buscar")
	void deveAceitarApenasCidadeNullAoBuscar() {
		String estado = "SP";
		when(clienteRepository.findByCidadeAndEstado(null, estado)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porCidadeEEstado(null, estado);

		assertNotNull(resultado);
		verify(clienteRepository).findByCidadeAndEstado(null, estado);
	}

	@Test
	@DisplayName("Deve aceitar apenas estado null ao buscar")
	void deveAceitarApenasEstadoNullAoBuscar() {
		String cidade = "São Paulo";
		when(clienteRepository.findByCidadeAndEstado(cidade, null)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porCidadeEEstado(cidade, null);

		assertNotNull(resultado);
		verify(clienteRepository).findByCidadeAndEstado(cidade, null);
	}

	// Testes para método porTag()

	@Test
	@DisplayName("Deve listar clientes por tag")
	void deveListarClientesPorTag() {
		String tag = "premium";
		Cliente cliente1 = criarClienteComTag("cliente-1", "João Silva", tag);
		Cliente cliente2 = criarClienteComTag("cliente-2", "Maria Santos", tag);

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		when(clienteRepository.findByTag(tag)).thenReturn(clientes);

		List<Cliente> resultado = service.porTag(tag);

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		verify(clienteRepository).findByTag(tag);
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes com a tag")
	void deveRetornarListaVaziaQuandoNaoHaClientesComATag() {
		String tag = "inexistente";
		when(clienteRepository.findByTag(tag)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porTag(tag);

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(clienteRepository).findByTag(tag);
	}

	@Test
	@DisplayName("Deve aceitar tag null ao buscar")
	void deveAceitarTagNullAoBuscar() {
		when(clienteRepository.findByTag(null)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porTag(null);

		assertNotNull(resultado);
		verify(clienteRepository).findByTag(null);
	}

	@Test
	@DisplayName("Deve aceitar tag vazia ao buscar")
	void deveAceitarTagVaziaAoBuscar() {
		String tag = "";
		when(clienteRepository.findByTag(tag)).thenReturn(Collections.emptyList());

		List<Cliente> resultado = service.porTag(tag);

		assertNotNull(resultado);
		verify(clienteRepository).findByTag(tag);
	}

	// Testes para método contarAtivos()

	@Test
	@DisplayName("Deve contar clientes ativos")
	void deveContarClientesAtivos() {
		when(clienteRepository.countAtivos()).thenReturn(5L);

		long resultado = service.contarAtivos();

		assertEquals(5L, resultado);
		verify(clienteRepository).countAtivos();
	}

	@Test
	@DisplayName("Deve retornar zero quando não há clientes ativos")
	void deveRetornarZeroQuandoNaoHaClientesAtivos() {
		when(clienteRepository.countAtivos()).thenReturn(0L);

		long resultado = service.contarAtivos();

		assertEquals(0L, resultado);
		verify(clienteRepository).countAtivos();
	}

	@Test
	@DisplayName("Deve retornar contagem grande de clientes ativos")
	void deveRetornarContagemGrandeDeClientesAtivos() {
		when(clienteRepository.countAtivos()).thenReturn(1000L);

		long resultado = service.contarAtivos();

		assertEquals(1000L, resultado);
		verify(clienteRepository).countAtivos();
	}

	@Test
	@DisplayName("Deve chamar countAtivos exatamente uma vez")
	void deveChamarCountAtivosExatamenteUmaVez() {
		when(clienteRepository.countAtivos()).thenReturn(10L);

		service.contarAtivos();

		verify(clienteRepository, times(1)).countAtivos();
		verifyNoMoreInteractions(clienteRepository);
	}

	// Testes gerais

	@Test
	@DisplayName("Deve criar instância do serviço com construtor")
	void deveCriarInstanciaDoServicoComConstrutor() {
		ListarClientesService novoService = new ListarClientesService(clienteRepository);
		assertNotNull(novoService);
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório no método todos")
	void devePropagararExcecaoDoRepositorioNoMetodoTodos() {
		when(clienteRepository.findAll()).thenThrow(new RuntimeException("Erro no banco"));

		assertThrows(RuntimeException.class, () -> service.todos());
		verify(clienteRepository).findAll();
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório no método ativos")
	void devePropagararExcecaoDoRepositorioNoMetodoAtivos() {
		when(clienteRepository.findAllAtivos()).thenThrow(new RuntimeException("Erro no banco"));

		assertThrows(RuntimeException.class, () -> service.ativos());
		verify(clienteRepository).findAllAtivos();
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório no método inativos")
	void devePropagararExcecaoDoRepositorioNoMetodoInativos() {
		when(clienteRepository.findAllInativos()).thenThrow(new RuntimeException("Erro no banco"));

		assertThrows(RuntimeException.class, () -> service.inativos());
		verify(clienteRepository).findAllInativos();
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório no método porCidade")
	void devePropagararExcecaoDoRepositorioNoMetodoPorCidade() {
		when(clienteRepository.findClientesAtivosPorCidade(anyString()))
				.thenThrow(new RuntimeException("Erro no banco"));

		assertThrows(RuntimeException.class, () -> service.porCidade("São Paulo"));
		verify(clienteRepository).findClientesAtivosPorCidade(anyString());
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório no método vipAtivos")
	void devePropagararExcecaoDoRepositorioNoMetodoVipAtivos() {
		when(clienteRepository.findClientesVipAtivos()).thenThrow(new RuntimeException("Erro no banco"));

		assertThrows(RuntimeException.class, () -> service.vipAtivos());
		verify(clienteRepository).findClientesVipAtivos();
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório no método porCidadeEEstado")
	void devePropagararExcecaoDoRepositorioNoMetodoPorCidadeEEstado() {
		when(clienteRepository.findByCidadeAndEstado(anyString(), anyString()))
				.thenThrow(new RuntimeException("Erro no banco"));

		assertThrows(RuntimeException.class, () -> service.porCidadeEEstado("São Paulo", "SP"));
		verify(clienteRepository).findByCidadeAndEstado(anyString(), anyString());
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório no método porTag")
	void devePropagararExcecaoDoRepositorioNoMetodoPorTag() {
		when(clienteRepository.findByTag(anyString())).thenThrow(new RuntimeException("Erro no banco"));

		assertThrows(RuntimeException.class, () -> service.porTag("premium"));
		verify(clienteRepository).findByTag(anyString());
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório no método contarAtivos")
	void devePropagararExcecaoDoRepositorioNoMetodoContarAtivos() {
		when(clienteRepository.countAtivos()).thenThrow(new RuntimeException("Erro no banco"));

		assertThrows(RuntimeException.class, () -> service.contarAtivos());
		verify(clienteRepository).countAtivos();
	}

	// Métodos auxiliares

	private Cliente criarCliente(String clienteId, String nome, boolean ativo) {
		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente(nome);
		cliente.setAtivo(ativo);
		return cliente;
	}

	private Cliente criarClienteComEndereco(String clienteId, String nome, String cidade, String estado,
			boolean ativo) {
		Cliente cliente = criarCliente(clienteId, nome, ativo);

		Endereco endereco = new Endereco();
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setRua("Rua Teste");
		endereco.setNumero("123");
		endereco.setCep("12345678");

		cliente.setEndereco(endereco);
		return cliente;
	}

	private Cliente criarClienteVip(String clienteId, String nome, boolean ativo) {
		Cliente cliente = criarCliente(clienteId, nome, ativo);
		// Assume que VIP é identificado por alguma propriedade/metadata
		return cliente;
	}

	private Cliente criarClienteComTag(String clienteId, String nome, String tag) {
		Cliente cliente = criarCliente(clienteId, nome, true);

		Metadata metadata = new Metadata();
		metadata.setTags(Collections.singletonList(tag));
		cliente.setMetadata(metadata);

		return cliente;
	}
}
