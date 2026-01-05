package br.com.postech.techchallange_customer.infrastructure.rest.adapter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.postech.techchallange_customer.application.dto.ClienteDTO;
import br.com.postech.techchallange_customer.application.dto.EnderecoDTO;
import br.com.postech.techchallange_customer.application.dto.MetadataDTO;
import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.domain.port.in.AtualizarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.BuscarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.CriarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.DeletarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.DesativarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.ListarClientesUseCase;
import br.com.postech.techchallange_customer.domain.port.in.ReativarClienteUseCase;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - ClienteRestAdapter")
class ClienteRestAdapterTest {

	@Mock
	private CriarClienteUseCase criarClienteUseCase;

	@Mock
	private BuscarClienteUseCase buscarClienteUseCase;

	@Mock
	private ListarClientesUseCase listarClientesUseCase;

	@Mock
	private AtualizarClienteUseCase atualizarClienteUseCase;

	@Mock
	private DesativarClienteUseCase desativarClienteUseCase;

	@Mock
	private ReativarClienteUseCase reativarClienteUseCase;

	@Mock
	private DeletarClienteUseCase deletarClienteUseCase;

	@InjectMocks
	private ClienteRestAdapter adapter;

	private Cliente cliente;
	private ClienteDTO clienteDTO;
	private Endereco endereco;
	private EnderecoDTO enderecoDTO;
	private Metadata metadata;
	private MetadataDTO metadataDTO;

	@BeforeEach
	void setUp() {
		// Setup Endereco
		endereco = new Endereco();
		endereco.setRua("Rua Teste");
		endereco.setNumero("123");
		endereco.setComplemento("Apto 1");
		endereco.setBairro("Centro");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		// Setup EnderecoDTO
		enderecoDTO = new EnderecoDTO();
		enderecoDTO.setRua("Rua Teste");
		enderecoDTO.setNumero("123");
		enderecoDTO.setComplemento("Apto 1");
		enderecoDTO.setBairro("Centro");
		enderecoDTO.setCidade("São Paulo");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("01234567");

		// Setup Metadata
		metadata = new Metadata();
		metadata.setOrigem("Web");
		metadata.setCanal("Site");
		metadata.setTags(Arrays.asList("VIP"));
		metadata.setNotas("Cliente importante");

		// Setup MetadataDTO
		metadataDTO = new MetadataDTO();
		metadataDTO.setOrigem("Web");
		metadataDTO.setCanal("Site");
		metadataDTO.setTags(Arrays.asList("VIP"));
		metadataDTO.setNotas("Cliente importante");

		// Setup Cliente
		cliente = new Cliente();
		cliente.setId("1");
		cliente.setClienteId("cliente-uuid-123");
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setTelefone("11999999999");
		cliente.setEndereco(endereco);
		cliente.setAtivo(true);
		cliente.setDataCadastro(LocalDateTime.now());
		cliente.setDataUltimaAtualizacao(LocalDateTime.now());
		cliente.setVersao(1);
		cliente.setMetadata(metadata);

		// Setup ClienteDTO
		clienteDTO = new ClienteDTO();
		clienteDTO.setId("1");
		clienteDTO.setClienteId("cliente-uuid-123");
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");
		clienteDTO.setTelefone("11999999999");
		clienteDTO.setEndereco(enderecoDTO);
		clienteDTO.setAtivo(true);
		clienteDTO.setDataCadastro(LocalDateTime.now());
		clienteDTO.setDataUltimaAtualizacao(LocalDateTime.now());
		clienteDTO.setVersao(1);
		clienteDTO.setMetadata(metadataDTO);
	}

	// ==================== Testes do método criar ====================

	@Test
	@DisplayName("Deve criar cliente com sucesso e retornar status 201")
	void deveCriarClienteComSucesso() {
		when(criarClienteUseCase.execute(any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.criar(clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("João Silva", response.getBody().getNomeCliente());
		assertEquals("joao@example.com", response.getBody().getEmailCliente());
		verify(criarClienteUseCase).execute(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve criar cliente sem endereco")
	void deveCriarClienteSemEndereco() {
		cliente.setEndereco(null);
		clienteDTO.setEndereco(null);

		when(criarClienteUseCase.execute(any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.criar(clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		verify(criarClienteUseCase).execute(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve criar cliente sem metadata")
	void deveCriarClienteSemMetadata() {
		cliente.setMetadata(null);
		clienteDTO.setMetadata(null);

		when(criarClienteUseCase.execute(any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.criar(clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		verify(criarClienteUseCase).execute(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve criar cliente com todos os campos preenchidos")
	void deveCriarClienteComTodosCamposPreenchidos() {
		when(criarClienteUseCase.execute(any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.criar(clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getEndereco());
		assertNotNull(response.getBody().getMetadata());
		assertEquals("12345678901", response.getBody().getCpfCliente());
		verify(criarClienteUseCase).execute(any(Cliente.class));
	}

	// ==================== Testes do método buscarPorId ====================

	@Test
	@DisplayName("Deve buscar cliente por ID com sucesso e retornar 200")
	void deveBuscarClientePorIdComSucesso() {
		when(buscarClienteUseCase.porClienteId("cliente-uuid-123")).thenReturn(Optional.of(cliente));

		ResponseEntity<ClienteDTO> response = adapter.buscarPorId("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("João Silva", response.getBody().getNomeCliente());
		verify(buscarClienteUseCase).porClienteId("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve retornar 404 quando cliente não encontrado por ID")
	void deveRetornar404QuandoClienteNaoEncontradoPorId() {
		when(buscarClienteUseCase.porClienteId("cliente-inexistente")).thenReturn(Optional.empty());

		ResponseEntity<ClienteDTO> response = adapter.buscarPorId("cliente-inexistente");

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(buscarClienteUseCase).porClienteId("cliente-inexistente");
	}

	@Test
	@DisplayName("Deve buscar cliente por ID sem endereco")
	void deveBuscarClientePorIdSemEndereco() {
		cliente.setEndereco(null);
		when(buscarClienteUseCase.porClienteId("cliente-uuid-123")).thenReturn(Optional.of(cliente));

		ResponseEntity<ClienteDTO> response = adapter.buscarPorId("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(buscarClienteUseCase).porClienteId("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve buscar cliente por ID sem metadata")
	void deveBuscarClientePorIdSemMetadata() {
		cliente.setMetadata(null);
		when(buscarClienteUseCase.porClienteId("cliente-uuid-123")).thenReturn(Optional.of(cliente));

		ResponseEntity<ClienteDTO> response = adapter.buscarPorId("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(buscarClienteUseCase).porClienteId("cliente-uuid-123");
	}

	// ==================== Testes do método buscarPorCpf ====================

	@Test
	@DisplayName("Deve buscar cliente por CPF com sucesso e retornar 200")
	void deveBuscarClientePorCpfComSucesso() {
		when(buscarClienteUseCase.porCpf("12345678901")).thenReturn(Optional.of(cliente));

		ResponseEntity<ClienteDTO> response = adapter.buscarPorCpf("12345678901");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("12345678901", response.getBody().getCpfCliente());
		verify(buscarClienteUseCase).porCpf("12345678901");
	}

	@Test
	@DisplayName("Deve retornar 404 quando cliente não encontrado por CPF")
	void deveRetornar404QuandoClienteNaoEncontradoPorCpf() {
		when(buscarClienteUseCase.porCpf("99999999999")).thenReturn(Optional.empty());

		ResponseEntity<ClienteDTO> response = adapter.buscarPorCpf("99999999999");

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(buscarClienteUseCase).porCpf("99999999999");
	}

	@Test
	@DisplayName("Deve buscar cliente por CPF com endereco completo")
	void deveBuscarClientePorCpfComEnderecoCompleto() {
		when(buscarClienteUseCase.porCpf("12345678901")).thenReturn(Optional.of(cliente));

		ResponseEntity<ClienteDTO> response = adapter.buscarPorCpf("12345678901");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody().getEndereco());
		assertEquals("São Paulo", response.getBody().getEndereco().getCidade());
		verify(buscarClienteUseCase).porCpf("12345678901");
	}

	// ==================== Testes do método buscarPorEmail ====================

	@Test
	@DisplayName("Deve buscar cliente por email com sucesso e retornar 200")
	void deveBuscarClientePorEmailComSucesso() {
		when(buscarClienteUseCase.porEmail("joao@example.com")).thenReturn(Optional.of(cliente));

		ResponseEntity<ClienteDTO> response = adapter.buscarPorEmail("joao@example.com");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("joao@example.com", response.getBody().getEmailCliente());
		verify(buscarClienteUseCase).porEmail("joao@example.com");
	}

	@Test
	@DisplayName("Deve retornar 404 quando cliente não encontrado por email")
	void deveRetornar404QuandoClienteNaoEncontradoPorEmail() {
		when(buscarClienteUseCase.porEmail("inexistente@example.com")).thenReturn(Optional.empty());

		ResponseEntity<ClienteDTO> response = adapter.buscarPorEmail("inexistente@example.com");

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(buscarClienteUseCase).porEmail("inexistente@example.com");
	}

	@Test
	@DisplayName("Deve buscar cliente por email com metadata completa")
	void deveBuscarClientePorEmailComMetadataCompleta() {
		when(buscarClienteUseCase.porEmail("joao@example.com")).thenReturn(Optional.of(cliente));

		ResponseEntity<ClienteDTO> response = adapter.buscarPorEmail("joao@example.com");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody().getMetadata());
		assertEquals("Web", response.getBody().getMetadata().getOrigem());
		verify(buscarClienteUseCase).porEmail("joao@example.com");
	}

	// ==================== Testes do método listarAtivos ====================

	@Test
	@DisplayName("Deve listar clientes ativos com sucesso")
	void deveListarClientesAtivosComSucesso() {
		Cliente cliente2 = new Cliente();
		cliente2.setId("2");
		cliente2.setNomeCliente("Maria Santos");
		cliente2.setEmailCliente("maria@example.com");
		cliente2.setCpfCliente("98765432100");

		when(listarClientesUseCase.ativos()).thenReturn(Arrays.asList(cliente, cliente2));

		ResponseEntity<List<ClienteDTO>> response = adapter.listarAtivos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size());
		assertEquals("João Silva", response.getBody().get(0).getNomeCliente());
		assertEquals("Maria Santos", response.getBody().get(1).getNomeCliente());
		verify(listarClientesUseCase).ativos();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes ativos")
	void deveRetornarListaVaziaQuandoNaoHaClientesAtivos() {
		when(listarClientesUseCase.ativos()).thenReturn(Collections.emptyList());

		ResponseEntity<List<ClienteDTO>> response = adapter.listarAtivos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isEmpty());
		verify(listarClientesUseCase).ativos();
	}

	@Test
	@DisplayName("Deve listar um único cliente ativo")
	void deveListarUmUnicoClienteAtivo() {
		when(listarClientesUseCase.ativos()).thenReturn(Collections.singletonList(cliente));

		ResponseEntity<List<ClienteDTO>> response = adapter.listarAtivos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().size());
		verify(listarClientesUseCase).ativos();
	}

	// ==================== Testes do método listarTodos ====================

	@Test
	@DisplayName("Deve listar todos os clientes com sucesso")
	void deveListarTodosClientesComSucesso() {
		Cliente cliente2 = new Cliente();
		cliente2.setId("2");
		cliente2.setNomeCliente("Maria Santos");
		cliente2.setEmailCliente("maria@example.com");
		cliente2.setCpfCliente("98765432100");
		cliente2.setAtivo(false);

		Cliente cliente3 = new Cliente();
		cliente3.setId("3");
		cliente3.setNomeCliente("Pedro Costa");
		cliente3.setEmailCliente("pedro@example.com");
		cliente3.setCpfCliente("11111111111");
		cliente3.setAtivo(true);

		when(listarClientesUseCase.todos()).thenReturn(Arrays.asList(cliente, cliente2, cliente3));

		ResponseEntity<List<ClienteDTO>> response = adapter.listarTodos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(3, response.getBody().size());
		verify(listarClientesUseCase).todos();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes cadastrados")
	void deveRetornarListaVaziaQuandoNaoHaClientes() {
		when(listarClientesUseCase.todos()).thenReturn(Collections.emptyList());

		ResponseEntity<List<ClienteDTO>> response = adapter.listarTodos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isEmpty());
		verify(listarClientesUseCase).todos();
	}

	@Test
	@DisplayName("Deve listar todos incluindo ativos e inativos")
	void deveListarTodosInclindoAtivosEInativos() {
		Cliente clienteAtivo = new Cliente();
		clienteAtivo.setId("1");
		clienteAtivo.setNomeCliente("Cliente Ativo");
		clienteAtivo.setAtivo(true);

		Cliente clienteInativo = new Cliente();
		clienteInativo.setId("2");
		clienteInativo.setNomeCliente("Cliente Inativo");
		clienteInativo.setAtivo(false);

		when(listarClientesUseCase.todos()).thenReturn(Arrays.asList(clienteAtivo, clienteInativo));

		ResponseEntity<List<ClienteDTO>> response = adapter.listarTodos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		verify(listarClientesUseCase).todos();
	}

	// ==================== Testes do método atualizar ====================

	@Test
	@DisplayName("Deve atualizar cliente com sucesso e retornar 200")
	void deveAtualizarClienteComSucesso() {
		when(atualizarClienteUseCase.execute(eq("cliente-uuid-123"), any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.atualizar("cliente-uuid-123", clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("João Silva", response.getBody().getNomeCliente());
		verify(atualizarClienteUseCase).execute(eq("cliente-uuid-123"), any(Cliente.class));
	}

	@Test
	@DisplayName("Deve atualizar cliente alterando nome")
	void deveAtualizarClienteAlterandoNome() {
		cliente.setNomeCliente("João Silva Atualizado");
		clienteDTO.setNomeCliente("João Silva Atualizado");

		when(atualizarClienteUseCase.execute(eq("cliente-uuid-123"), any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.atualizar("cliente-uuid-123", clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("João Silva Atualizado", response.getBody().getNomeCliente());
		verify(atualizarClienteUseCase).execute(eq("cliente-uuid-123"), any(Cliente.class));
	}

	@Test
	@DisplayName("Deve atualizar cliente alterando endereco")
	void deveAtualizarClienteAlterandoEndereco() {
		endereco.setCidade("Rio de Janeiro");
		enderecoDTO.setCidade("Rio de Janeiro");

		when(atualizarClienteUseCase.execute(eq("cliente-uuid-123"), any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.atualizar("cliente-uuid-123", clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Rio de Janeiro", response.getBody().getEndereco().getCidade());
		verify(atualizarClienteUseCase).execute(eq("cliente-uuid-123"), any(Cliente.class));
	}

	@Test
	@DisplayName("Deve atualizar cliente removendo endereco")
	void deveAtualizarClienteRemovendoEndereco() {
		cliente.setEndereco(null);
		clienteDTO.setEndereco(null);

		when(atualizarClienteUseCase.execute(eq("cliente-uuid-123"), any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.atualizar("cliente-uuid-123", clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(atualizarClienteUseCase).execute(eq("cliente-uuid-123"), any(Cliente.class));
	}

	@Test
	@DisplayName("Deve atualizar cliente com todos os campos")
	void deveAtualizarClienteComTodosCampos() {
		when(atualizarClienteUseCase.execute(eq("cliente-uuid-123"), any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<ClienteDTO> response = adapter.atualizar("cliente-uuid-123", clienteDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody().getEndereco());
		assertNotNull(response.getBody().getMetadata());
		verify(atualizarClienteUseCase).execute(eq("cliente-uuid-123"), any(Cliente.class));
	}

	// ==================== Testes do método desativar ====================

	@Test
	@DisplayName("Deve desativar cliente com sucesso e retornar 204")
	void deveDesativarClienteComSucesso() {
		ResponseEntity<Void> response = adapter.desativar("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(desativarClienteUseCase).execute("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve desativar cliente e não retornar corpo na resposta")
	void deveDesativarClienteSemCorpoResposta() {
		ResponseEntity<Void> response = adapter.desativar("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertEquals(null, response.getBody());
		verify(desativarClienteUseCase).execute("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve desativar cliente com ID diferente")
	void deveDesativarClienteComIdDiferente() {
		ResponseEntity<Void> response = adapter.desativar("outro-cliente-uuid");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(desativarClienteUseCase).execute("outro-cliente-uuid");
	}

	// ==================== Testes do método reativar ====================

	@Test
	@DisplayName("Deve reativar cliente com sucesso e retornar 204")
	void deveReativarClienteComSucesso() {
		ResponseEntity<Void> response = adapter.reativar("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(reativarClienteUseCase).execute("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve reativar cliente e não retornar corpo na resposta")
	void deveReativarClienteSemCorpoResposta() {
		ResponseEntity<Void> response = adapter.reativar("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertEquals(null, response.getBody());
		verify(reativarClienteUseCase).execute("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve reativar cliente com ID diferente")
	void deveReativarClienteComIdDiferente() {
		ResponseEntity<Void> response = adapter.reativar("outro-cliente-uuid");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(reativarClienteUseCase).execute("outro-cliente-uuid");
	}

	// ==================== Testes do método deletar ====================

	@Test
	@DisplayName("Deve deletar cliente permanentemente com sucesso e retornar 204")
	void deveDeletarClienteComSucesso() {
		ResponseEntity<Void> response = adapter.deletar("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(deletarClienteUseCase).execute("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve deletar cliente e não retornar corpo na resposta")
	void deveDeletarClienteSemCorpoResposta() {
		ResponseEntity<Void> response = adapter.deletar("cliente-uuid-123");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertEquals(null, response.getBody());
		verify(deletarClienteUseCase).execute("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve deletar cliente com ID diferente")
	void deveDeletarClienteComIdDiferente() {
		ResponseEntity<Void> response = adapter.deletar("outro-cliente-uuid");

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(deletarClienteUseCase).execute("outro-cliente-uuid");
	}

	// ==================== Testes do método buscarPorCidade ====================

	@Test
	@DisplayName("Deve buscar clientes por cidade com sucesso")
	void deveBuscarClientesPorCidadeComSucesso() {
		Cliente cliente2 = new Cliente();
		cliente2.setId("2");
		cliente2.setNomeCliente("Maria Santos");
		Endereco endereco2 = new Endereco();
		endereco2.setCidade("São Paulo");
		cliente2.setEndereco(endereco2);

		when(listarClientesUseCase.porCidade("São Paulo")).thenReturn(Arrays.asList(cliente, cliente2));

		ResponseEntity<List<ClienteDTO>> response = adapter.buscarPorCidade("São Paulo");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size());
		verify(listarClientesUseCase).porCidade("São Paulo");
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes na cidade")
	void deveRetornarListaVaziaQuandoNaoHaClientesNaCidade() {
		when(listarClientesUseCase.porCidade("Cidade Inexistente")).thenReturn(Collections.emptyList());

		ResponseEntity<List<ClienteDTO>> response = adapter.buscarPorCidade("Cidade Inexistente");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isEmpty());
		verify(listarClientesUseCase).porCidade("Cidade Inexistente");
	}

	@Test
	@DisplayName("Deve buscar clientes de cidade diferente")
	void deveBuscarClientesDeCidadeDiferente() {
		Cliente clienteRio = new Cliente();
		clienteRio.setId("3");
		clienteRio.setNomeCliente("Pedro Rio");
		Endereco enderecoRio = new Endereco();
		enderecoRio.setCidade("Rio de Janeiro");
		clienteRio.setEndereco(enderecoRio);

		when(listarClientesUseCase.porCidade("Rio de Janeiro")).thenReturn(Collections.singletonList(clienteRio));

		ResponseEntity<List<ClienteDTO>> response = adapter.buscarPorCidade("Rio de Janeiro");

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
		verify(listarClientesUseCase).porCidade("Rio de Janeiro");
	}

	// ==================== Testes do método buscarClientesVip ====================

	@Test
	@DisplayName("Deve buscar clientes VIP com sucesso")
	void deveBuscarClientesVipComSucesso() {
		Cliente clienteVip2 = new Cliente();
		clienteVip2.setId("2");
		clienteVip2.setNomeCliente("Maria VIP");
		Metadata metadataVip2 = new Metadata();
		metadataVip2.setTags(Arrays.asList("VIP"));
		clienteVip2.setMetadata(metadataVip2);

		when(listarClientesUseCase.vipAtivos()).thenReturn(Arrays.asList(cliente, clienteVip2));

		ResponseEntity<List<ClienteDTO>> response = adapter.buscarClientesVip();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size());
		verify(listarClientesUseCase).vipAtivos();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes VIP")
	void deveRetornarListaVaziaQuandoNaoHaClientesVip() {
		when(listarClientesUseCase.vipAtivos()).thenReturn(Collections.emptyList());

		ResponseEntity<List<ClienteDTO>> response = adapter.buscarClientesVip();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isEmpty());
		verify(listarClientesUseCase).vipAtivos();
	}

	@Test
	@DisplayName("Deve buscar apenas um cliente VIP")
	void deveBuscarApenasUmClienteVip() {
		when(listarClientesUseCase.vipAtivos()).thenReturn(Collections.singletonList(cliente));

		ResponseEntity<List<ClienteDTO>> response = adapter.buscarClientesVip();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
		verify(listarClientesUseCase).vipAtivos();
	}

	// ==================== Testes do método contarAtivos ====================

	@Test
	@DisplayName("Deve contar clientes ativos e retornar 200")
	void deveContarClientesAtivosComSucesso() {
		when(listarClientesUseCase.contarAtivos()).thenReturn(5L);

		ResponseEntity<Long> response = adapter.contarAtivos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(5L, response.getBody());
		verify(listarClientesUseCase).contarAtivos();
	}

	@Test
	@DisplayName("Deve retornar zero quando não há clientes ativos")
	void deveRetornarZeroQuandoNaoHaClientesAtivos() {
		when(listarClientesUseCase.contarAtivos()).thenReturn(0L);

		ResponseEntity<Long> response = adapter.contarAtivos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0L, response.getBody());
		verify(listarClientesUseCase).contarAtivos();
	}

	@Test
	@DisplayName("Deve contar um único cliente ativo")
	void deveContarUmUnicoClienteAtivo() {
		when(listarClientesUseCase.contarAtivos()).thenReturn(1L);

		ResponseEntity<Long> response = adapter.contarAtivos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody());
		verify(listarClientesUseCase).contarAtivos();
	}

	@Test
	@DisplayName("Deve contar muitos clientes ativos")
	void deveContarMuitosClientesAtivos() {
		when(listarClientesUseCase.contarAtivos()).thenReturn(100L);

		ResponseEntity<Long> response = adapter.contarAtivos();

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(100L, response.getBody());
		verify(listarClientesUseCase).contarAtivos();
	}

	// ==================== Testes do construtor ====================

	@Test
	@DisplayName("Deve criar instância do adapter com todos os use cases")
	void deveCriarInstanciaDoAdapterComTodosUseCases() {
		ClienteRestAdapter newAdapter = new ClienteRestAdapter(
				criarClienteUseCase,
				buscarClienteUseCase,
				listarClientesUseCase,
				atualizarClienteUseCase,
				desativarClienteUseCase,
				reativarClienteUseCase,
				deletarClienteUseCase);

		assertNotNull(newAdapter);
	}
}
