package br.com.postech.techchallange_customer.infrastructure.persistence.adapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.infrastructure.persistence.document.ClienteDocument;
import br.com.postech.techchallange_customer.infrastructure.persistence.repository.ClienteMongoRepository;

@ExtendWith(MockitoExtension.class)
class ClienteRepositoryAdapterTest {

	@Mock
	private ClienteMongoRepository mongoRepository;

	@InjectMocks
	private ClienteRepositoryAdapter adapter;

	private Cliente cliente;
	private ClienteDocument clienteDocument;

	@BeforeEach
	void setUp() {
		cliente = new Cliente("João Silva", "joao@email.com", "12345678901");
		cliente.setId("id-123");
		cliente.setClienteId("cliente-uuid-123");
		cliente.setTelefone("11987654321");
		cliente.setAtivo(true);

		Endereco endereco = new Endereco("Rua das Flores", "123", "São Paulo", "SP", "01234567");
		cliente.setEndereco(endereco);

		Metadata metadata = new Metadata("Web", "Site");
		metadata.adicionarTag("VIP");
		cliente.setMetadata(metadata);

		clienteDocument = new ClienteDocument();
		clienteDocument.setId("id-123");
		clienteDocument.setClienteId("cliente-uuid-123");
		clienteDocument.setNomeCliente("João Silva");
		clienteDocument.setEmailCliente("joao@email.com");
		clienteDocument.setCpfCliente("12345678901");
		clienteDocument.setTelefone("11987654321");
		clienteDocument.setAtivo(true);
	}

	@Test
	@DisplayName("Deve salvar cliente com sucesso")
	void deveSalvarClienteComSucesso() {
		when(mongoRepository.save(any(ClienteDocument.class))).thenReturn(clienteDocument);

		Cliente resultado = adapter.save(cliente);

		assertNotNull(resultado);
		assertEquals("João Silva", resultado.getNomeCliente());
		assertEquals("joao@email.com", resultado.getEmailCliente());
		assertEquals("12345678901", resultado.getCpfCliente());
		verify(mongoRepository, times(1)).save(any(ClienteDocument.class));
	}

	@Test
	@DisplayName("Deve atualizar cliente com sucesso")
	void deveAtualizarClienteComSucesso() {
		when(mongoRepository.save(any(ClienteDocument.class))).thenReturn(clienteDocument);

		Cliente resultado = adapter.update(cliente);

		assertNotNull(resultado);
		assertEquals("João Silva", resultado.getNomeCliente());
		verify(mongoRepository, times(1)).save(any(ClienteDocument.class));
	}

	@Test
	@DisplayName("Deve encontrar cliente por clienteId")
	void deveEncontrarClientePorClienteId() {
		when(mongoRepository.findByClienteId("cliente-uuid-123")).thenReturn(Optional.of(clienteDocument));

		Optional<Cliente> resultado = adapter.findByClienteId("cliente-uuid-123");

		assertTrue(resultado.isPresent());
		assertEquals("João Silva", resultado.get().getNomeCliente());
		verify(mongoRepository, times(1)).findByClienteId("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve retornar vazio quando cliente não encontrado por clienteId")
	void deveRetornarVazioQuandoClienteNaoEncontradoPorClienteId() {
		when(mongoRepository.findByClienteId("cliente-inexistente")).thenReturn(Optional.empty());

		Optional<Cliente> resultado = adapter.findByClienteId("cliente-inexistente");

		assertFalse(resultado.isPresent());
		verify(mongoRepository, times(1)).findByClienteId("cliente-inexistente");
	}

	@Test
	@DisplayName("Deve encontrar cliente por CPF")
	void deveEncontrarClientePorCpf() {
		when(mongoRepository.findByCpfCliente("12345678901")).thenReturn(Optional.of(clienteDocument));

		Optional<Cliente> resultado = adapter.findByCpf("12345678901");

		assertTrue(resultado.isPresent());
		assertEquals("12345678901", resultado.get().getCpfCliente());
		verify(mongoRepository, times(1)).findByCpfCliente("12345678901");
	}

	@Test
	@DisplayName("Deve retornar vazio quando cliente não encontrado por CPF")
	void deveRetornarVazioQuandoClienteNaoEncontradoPorCpf() {
		when(mongoRepository.findByCpfCliente("99999999999")).thenReturn(Optional.empty());

		Optional<Cliente> resultado = adapter.findByCpf("99999999999");

		assertFalse(resultado.isPresent());
		verify(mongoRepository, times(1)).findByCpfCliente("99999999999");
	}

	@Test
	@DisplayName("Deve encontrar cliente por email")
	void deveEncontrarClientePorEmail() {
		when(mongoRepository.findByEmailCliente("joao@email.com")).thenReturn(Optional.of(clienteDocument));

		Optional<Cliente> resultado = adapter.findByEmail("joao@email.com");

		assertTrue(resultado.isPresent());
		assertEquals("joao@email.com", resultado.get().getEmailCliente());
		verify(mongoRepository, times(1)).findByEmailCliente("joao@email.com");
	}

	@Test
	@DisplayName("Deve retornar vazio quando cliente não encontrado por email")
	void deveRetornarVazioQuandoClienteNaoEncontradoPorEmail() {
		when(mongoRepository.findByEmailCliente("inexistente@email.com")).thenReturn(Optional.empty());

		Optional<Cliente> resultado = adapter.findByEmail("inexistente@email.com");

		assertFalse(resultado.isPresent());
		verify(mongoRepository, times(1)).findByEmailCliente("inexistente@email.com");
	}

	@Test
	@DisplayName("Deve encontrar cliente por ID")
	void deveEncontrarClientePorId() {
		when(mongoRepository.findById("id-123")).thenReturn(Optional.of(clienteDocument));

		Optional<Cliente> resultado = adapter.findById("id-123");

		assertTrue(resultado.isPresent());
		assertEquals("id-123", resultado.get().getId());
		verify(mongoRepository, times(1)).findById("id-123");
	}

	@Test
	@DisplayName("Deve retornar vazio quando cliente não encontrado por ID")
	void deveRetornarVazioQuandoClienteNaoEncontradoPorId() {
		when(mongoRepository.findById("id-inexistente")).thenReturn(Optional.empty());

		Optional<Cliente> resultado = adapter.findById("id-inexistente");

		assertFalse(resultado.isPresent());
		verify(mongoRepository, times(1)).findById("id-inexistente");
	}

	@Test
	@DisplayName("Deve listar todos os clientes")
	void deveListarTodosOsClientes() {
		ClienteDocument doc2 = new ClienteDocument();
		doc2.setId("id-456");
		doc2.setNomeCliente("Maria Santos");
		doc2.setEmailCliente("maria@email.com");
		doc2.setCpfCliente("98765432109");

		when(mongoRepository.findAll()).thenReturn(Arrays.asList(clienteDocument, doc2));

		List<Cliente> resultado = adapter.findAll();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		verify(mongoRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes")
	void deveRetornarListaVaziaQuandoNaoHaClientes() {
		when(mongoRepository.findAll()).thenReturn(Collections.emptyList());

		List<Cliente> resultado = adapter.findAll();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(mongoRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Deve listar clientes ativos")
	void deveListarClientesAtivos() {
		when(mongoRepository.findByAtivoTrue()).thenReturn(Collections.singletonList(clienteDocument));

		List<Cliente> resultado = adapter.findAllAtivos();

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		assertTrue(resultado.get(0).getAtivo());
		verify(mongoRepository, times(1)).findByAtivoTrue();
	}

	@Test
	@DisplayName("Deve listar clientes inativos")
	void deveListarClientesInativos() {
		clienteDocument.setAtivo(false);
		when(mongoRepository.findByAtivoFalse()).thenReturn(Collections.singletonList(clienteDocument));

		List<Cliente> resultado = adapter.findAllInativos();

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		verify(mongoRepository, times(1)).findByAtivoFalse();
	}

	@Test
	@DisplayName("Deve buscar clientes por cidade e estado")
	void deveBuscarClientesPorCidadeEEstado() {
		when(mongoRepository.findByEnderecoCidadeAndEnderecoEstado("São Paulo", "SP"))
				.thenReturn(Collections.singletonList(clienteDocument));

		List<Cliente> resultado = adapter.findByCidadeAndEstado("São Paulo", "SP");

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		verify(mongoRepository, times(1)).findByEnderecoCidadeAndEnderecoEstado("São Paulo", "SP");
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes na cidade e estado")
	void deveRetornarListaVaziaQuandoNaoHaClientesNaCidadeEEstado() {
		when(mongoRepository.findByEnderecoCidadeAndEnderecoEstado("Rio de Janeiro", "RJ"))
				.thenReturn(Collections.emptyList());

		List<Cliente> resultado = adapter.findByCidadeAndEstado("Rio de Janeiro", "RJ");

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(mongoRepository, times(1)).findByEnderecoCidadeAndEnderecoEstado("Rio de Janeiro", "RJ");
	}

	@Test
	@DisplayName("Deve buscar clientes por tag")
	void deveBuscarClientesPorTag() {
		when(mongoRepository.findByMetadataTagsContaining("VIP"))
				.thenReturn(Collections.singletonList(clienteDocument));

		List<Cliente> resultado = adapter.findByTag("VIP");

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		verify(mongoRepository, times(1)).findByMetadataTagsContaining("VIP");
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes com a tag")
	void deveRetornarListaVaziaQuandoNaoHaClientesComTag() {
		when(mongoRepository.findByMetadataTagsContaining("Premium"))
				.thenReturn(Collections.emptyList());

		List<Cliente> resultado = adapter.findByTag("Premium");

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(mongoRepository, times(1)).findByMetadataTagsContaining("Premium");
	}

	@Test
	@DisplayName("Deve buscar clientes ativos por cidade")
	void deveBuscarClientesAtivosPorCidade() {
		when(mongoRepository.findClientesAtivosPorCidade("São Paulo"))
				.thenReturn(Collections.singletonList(clienteDocument));

		List<Cliente> resultado = adapter.findClientesAtivosPorCidade("São Paulo");

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		verify(mongoRepository, times(1)).findClientesAtivosPorCidade("São Paulo");
	}

	@Test
	@DisplayName("Deve buscar clientes VIP ativos")
	void deveBuscarClientesVipAtivos() {
		when(mongoRepository.findClientesVipAtivos())
				.thenReturn(Collections.singletonList(clienteDocument));

		List<Cliente> resultado = adapter.findClientesVipAtivos();

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		verify(mongoRepository, times(1)).findClientesVipAtivos();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há clientes VIP ativos")
	void deveRetornarListaVaziaQuandoNaoHaClientesVipAtivos() {
		when(mongoRepository.findClientesVipAtivos()).thenReturn(Collections.emptyList());

		List<Cliente> resultado = adapter.findClientesVipAtivos();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(mongoRepository, times(1)).findClientesVipAtivos();
	}

	@Test
	@DisplayName("Deve contar clientes ativos")
	void deveContarClientesAtivos() {
		when(mongoRepository.countByAtivoTrue()).thenReturn(5L);

		long resultado = adapter.countAtivos();

		assertEquals(5L, resultado);
		verify(mongoRepository, times(1)).countByAtivoTrue();
	}

	@Test
	@DisplayName("Deve retornar zero quando não há clientes ativos")
	void deveRetornarZeroQuandoNaoHaClientesAtivos() {
		when(mongoRepository.countByAtivoTrue()).thenReturn(0L);

		long resultado = adapter.countAtivos();

		assertEquals(0L, resultado);
		verify(mongoRepository, times(1)).countByAtivoTrue();
	}

	@Test
	@DisplayName("Deve verificar se existe cliente por CPF")
	void deveVerificarSeExisteClientePorCpf() {
		when(mongoRepository.existsByCpfCliente("12345678901")).thenReturn(true);

		boolean resultado = adapter.existsByCpf("12345678901");

		assertTrue(resultado);
		verify(mongoRepository, times(1)).existsByCpfCliente("12345678901");
	}

	@Test
	@DisplayName("Deve retornar falso quando não existe cliente por CPF")
	void deveRetornarFalsoQuandoNaoExisteClientePorCpf() {
		when(mongoRepository.existsByCpfCliente("99999999999")).thenReturn(false);

		boolean resultado = adapter.existsByCpf("99999999999");

		assertFalse(resultado);
		verify(mongoRepository, times(1)).existsByCpfCliente("99999999999");
	}

	@Test
	@DisplayName("Deve verificar se existe cliente por email")
	void deveVerificarSeExisteClientePorEmail() {
		when(mongoRepository.existsByEmailCliente("joao@email.com")).thenReturn(true);

		boolean resultado = adapter.existsByEmail("joao@email.com");

		assertTrue(resultado);
		verify(mongoRepository, times(1)).existsByEmailCliente("joao@email.com");
	}

	@Test
	@DisplayName("Deve retornar falso quando não existe cliente por email")
	void deveRetornarFalsoQuandoNaoExisteClientePorEmail() {
		when(mongoRepository.existsByEmailCliente("inexistente@email.com")).thenReturn(false);

		boolean resultado = adapter.existsByEmail("inexistente@email.com");

		assertFalse(resultado);
		verify(mongoRepository, times(1)).existsByEmailCliente("inexistente@email.com");
	}

	@Test
	@DisplayName("Deve deletar cliente")
	void deveDeletarCliente() {
		doNothing().when(mongoRepository).delete(any(ClienteDocument.class));

		adapter.delete(cliente);

		verify(mongoRepository, times(1)).delete(any(ClienteDocument.class));
	}

	@Test
	@DisplayName("Deve deletar cliente por clienteId")
	void deveDeletarClientePorClienteId() {
		doNothing().when(mongoRepository).deleteByClienteId("cliente-uuid-123");

		adapter.deleteByClienteId("cliente-uuid-123");

		verify(mongoRepository, times(1)).deleteByClienteId("cliente-uuid-123");
	}

	@Test
	@DisplayName("Deve listar múltiplos clientes ativos")
	void deveListarMultiplosClientesAtivos() {
		ClienteDocument doc2 = new ClienteDocument();
		doc2.setId("id-456");
		doc2.setNomeCliente("Maria Santos");
		doc2.setAtivo(true);

		ClienteDocument doc3 = new ClienteDocument();
		doc3.setId("id-789");
		doc3.setNomeCliente("Pedro Oliveira");
		doc3.setAtivo(true);

		when(mongoRepository.findByAtivoTrue()).thenReturn(Arrays.asList(clienteDocument, doc2, doc3));

		List<Cliente> resultado = adapter.findAllAtivos();

		assertNotNull(resultado);
		assertEquals(3, resultado.size());
		verify(mongoRepository, times(1)).findByAtivoTrue();
	}

	@Test
	@DisplayName("Deve listar múltiplos clientes por tag")
	void deveListarMultiplosClientesPorTag() {
		ClienteDocument doc2 = new ClienteDocument();
		doc2.setId("id-456");
		doc2.setNomeCliente("Maria Santos");

		when(mongoRepository.findByMetadataTagsContaining("VIP"))
				.thenReturn(Arrays.asList(clienteDocument, doc2));

		List<Cliente> resultado = adapter.findByTag("VIP");

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		verify(mongoRepository, times(1)).findByMetadataTagsContaining("VIP");
	}

	@Test
	@DisplayName("Deve buscar clientes por diferentes cidades e estados")
	void deveBuscarClientesPorDiferentesCidadesEEstados() {
		when(mongoRepository.findByEnderecoCidadeAndEnderecoEstado("Rio de Janeiro", "RJ"))
				.thenReturn(Collections.singletonList(clienteDocument));

		List<Cliente> resultado = adapter.findByCidadeAndEstado("Rio de Janeiro", "RJ");

		assertNotNull(resultado);
		verify(mongoRepository, times(1)).findByEnderecoCidadeAndEnderecoEstado("Rio de Janeiro", "RJ");
	}

	@Test
	@DisplayName("Deve retornar contagem maior que zero para clientes ativos")
	void deveRetornarContagemMaiorQueZeroParaClientesAtivos() {
		when(mongoRepository.countByAtivoTrue()).thenReturn(10L);

		long resultado = adapter.countAtivos();

		assertEquals(10L, resultado);
		assertTrue(resultado > 0);
		verify(mongoRepository, times(1)).countByAtivoTrue();
	}
}
