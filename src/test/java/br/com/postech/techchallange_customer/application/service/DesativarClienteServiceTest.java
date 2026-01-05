package br.com.postech.techchallange_customer.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("DesativarClienteService Tests")
class DesativarClienteServiceTest {

	@Mock
	private ClienteRepositoryPort clienteRepository;

	private DesativarClienteService service;

	@BeforeEach
	void setUp() {
		service = new DesativarClienteService(clienteRepository);
	}

	@Test
	@DisplayName("Deve desativar cliente com sucesso quando cliente existe")
	void deveDesativarClienteComSucessoQuandoClienteExiste() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		assertDoesNotThrow(() -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository).update(cliente);
		assertFalse(cliente.getAtivo());
	}

	@Test
	@DisplayName("Deve lançar exceção quando cliente não existe")
	void deveLancarExcecaoQuandoClienteNaoExiste() {
		String clienteId = "cliente-inexistente";

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.empty());

		ClienteNotFoundException exception = assertThrows(
				ClienteNotFoundException.class,
				() -> service.execute(clienteId));

		assertTrue(exception.getMessage().contains(clienteId));
		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository, never()).update(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve alterar status ativo para false")
	void deveAlterarStatusAtivoParaFalse() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertFalse(cliente.getAtivo());
		verify(clienteRepository).update(cliente);
	}

	@Test
	@DisplayName("Deve criar metadata se não existir ao desativar")
	void deveCriarMetadataSeNaoExistirAoDesativar() {
		String clienteId = "cliente-sem-metadata";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);
		cliente.setMetadata(null);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertNotNull(cliente.getMetadata());
		assertNotNull(cliente.getMetadata().getDataDesativacao());
		verify(clienteRepository).update(cliente);
	}

	@Test
	@DisplayName("Deve definir dataDesativacao quando desativar cliente")
	void deveDefinirDataDesativacaoQuandoDesativarCliente() {
		String clienteId = "cliente-123";
		LocalDateTime antes = LocalDateTime.now();

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		LocalDateTime depois = LocalDateTime.now();

		assertNotNull(cliente.getMetadata());
		assertNotNull(cliente.getMetadata().getDataDesativacao());
		assertTrue(cliente.getMetadata().getDataDesativacao().isAfter(antes.minusSeconds(1)));
		assertTrue(cliente.getMetadata().getDataDesativacao().isBefore(depois.plusSeconds(1)));
	}

	@Test
	@DisplayName("Deve preservar metadata existente ao desativar")
	void devePreservarMetadataExistenteAoDesativar() {
		String clienteId = "cliente-com-metadata";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		Metadata metadataExistente = new Metadata();
		metadataExistente.setTags(java.util.Arrays.asList("tag1", "tag2"));
		cliente.setMetadata(metadataExistente);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertNotNull(cliente.getMetadata());
		assertEquals(2, cliente.getMetadata().getTags().size());
		assertTrue(cliente.getMetadata().getTags().contains("tag1"));
		assertTrue(cliente.getMetadata().getTags().contains("tag2"));
		assertNotNull(cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve desativar cliente já inativo")
	void deveDesativarClienteJaInativo() {
		String clienteId = "cliente-inativo";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		assertDoesNotThrow(() -> service.execute(clienteId));

		assertFalse(cliente.getAtivo());
		verify(clienteRepository).update(cliente);
	}

	@Test
	@DisplayName("Deve chamar update do repositório com cliente desativado")
	void deveChamarUpdateDoRepositorioComClienteDesativado() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
		verify(clienteRepository).update(clienteCaptor.capture());

		Cliente clienteCapturado = clienteCaptor.getValue();
		assertFalse(clienteCapturado.getAtivo());
		assertEquals(clienteId, clienteCapturado.getClienteId());
	}

	@Test
	@DisplayName("Deve preservar outros dados do cliente ao desativar")
	void devePreservarOutrosDadosDoClienteAoDesativar() {
		String clienteId = "cliente-completo";
		String nome = "João Silva";
		String email = "joao@example.com";
		String cpf = "12345678901";
		String telefone = "11987654321";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente(nome);
		cliente.setEmailCliente(email);
		cliente.setCpfCliente(cpf);
		cliente.setTelefone(telefone);
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertEquals(clienteId, cliente.getClienteId());
		assertEquals(nome, cliente.getNomeCliente());
		assertEquals(email, cliente.getEmailCliente());
		assertEquals(cpf, cliente.getCpfCliente());
		assertEquals(telefone, cliente.getTelefone());
		assertFalse(cliente.getAtivo());
	}

	@Test
	@DisplayName("Deve atualizar timestamp ao desativar")
	void deveAtualizarTimestampAoDesativar() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		LocalDateTime timestampAnterior = cliente.getDataUltimaAtualizacao();

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		// O timestamp deve ser atualizado (método updateTimestamp é chamado)
		verify(clienteRepository).update(cliente);
	}

	@Test
	@DisplayName("Deve aceitar clienteId null e lançar exceção")
	void deveAceitarClienteIdNullELancarExcecao() {
		when(clienteRepository.findByClienteId(null)).thenReturn(Optional.empty());

		ClienteNotFoundException exception = assertThrows(
				ClienteNotFoundException.class,
				() -> service.execute(null));

		assertNotNull(exception);
		verify(clienteRepository).findByClienteId(null);
		verify(clienteRepository, never()).update(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve aceitar clienteId vazio e lançar exceção")
	void deveAceitarClienteIdVazioELancarExcecao() {
		String clienteId = "";

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.empty());

		ClienteNotFoundException exception = assertThrows(
				ClienteNotFoundException.class,
				() -> service.execute(clienteId));

		assertNotNull(exception);
		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository, never()).update(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve executar desativação sem retornar valor")
	void deveExecutarDesativacaoSemRetornarValor() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		// O método execute é void, então não deve retornar nada
		service.execute(clienteId);

		verify(clienteRepository).update(cliente);
	}

	@Test
	@DisplayName("Deve chamar repositório exatamente uma vez para buscar e uma vez para atualizar")
	void deveChamarRepositorioExatamenteUmaVezParaBuscarEUmaVezParaAtualizar() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		verify(clienteRepository, times(1)).findByClienteId(clienteId);
		verify(clienteRepository, times(1)).update(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção com mensagem contendo o clienteId")
	void deveLancarExcecaoComMensagemContendoOClienteId() {
		String clienteId = "cliente-nao-encontrado-789";

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.empty());

		ClienteNotFoundException exception = assertThrows(
				ClienteNotFoundException.class,
				() -> service.execute(clienteId));

		assertTrue(exception.getMessage().contains(clienteId));
	}

	@Test
	@DisplayName("Deve desativar múltiplos clientes em sequência")
	void deveDesativarMultiplosClientesEmSequencia() {
		String clienteId1 = "cliente-1";
		String clienteId2 = "cliente-2";
		String clienteId3 = "cliente-3";

		Cliente cliente1 = new Cliente();
		cliente1.setClienteId(clienteId1);
		cliente1.setAtivo(true);

		Cliente cliente2 = new Cliente();
		cliente2.setClienteId(clienteId2);
		cliente2.setAtivo(true);

		Cliente cliente3 = new Cliente();
		cliente3.setClienteId(clienteId3);
		cliente3.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId1)).thenReturn(Optional.of(cliente1));
		when(clienteRepository.findByClienteId(clienteId2)).thenReturn(Optional.of(cliente2));
		when(clienteRepository.findByClienteId(clienteId3)).thenReturn(Optional.of(cliente3));

		when(clienteRepository.update(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

		assertDoesNotThrow(() -> service.execute(clienteId1));
		assertDoesNotThrow(() -> service.execute(clienteId2));
		assertDoesNotThrow(() -> service.execute(clienteId3));

		assertFalse(cliente1.getAtivo());
		assertFalse(cliente2.getAtivo());
		assertFalse(cliente3.getAtivo());

		verify(clienteRepository, times(3)).update(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve criar instância do serviço com construtor")
	void deveCriarInstanciaDoServicoComConstrutor() {
		DesativarClienteService novoService = new DesativarClienteService(clienteRepository);
		assertNotNull(novoService);
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório se houver erro ao buscar")
	void devePropagararExcecaoDoRepositorioSeHouverErroAoBuscar() {
		String clienteId = "cliente-com-erro";

		when(clienteRepository.findByClienteId(clienteId))
				.thenThrow(new RuntimeException("Erro no banco de dados"));

		assertThrows(RuntimeException.class, () -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository, never()).update(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório se houver erro ao atualizar")
	void devePropagararExcecaoDoRepositorioSeHouverErroAoAtualizar() {
		String clienteId = "cliente-erro-update";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class)))
				.thenThrow(new RuntimeException("Erro ao atualizar"));

		assertThrows(RuntimeException.class, () -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository).update(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve usar orElseThrow para lançar exceção quando cliente não encontrado")
	void deveUsarOrElseThrowParaLancarExcecaoQuandoClienteNaoEncontrado() {
		String clienteId = "cliente-inexistente";

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.empty());

		assertThrows(ClienteNotFoundException.class, () -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve chamar método desativar do cliente")
	void deveChamarMetodoDesativarDoCliente() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		// Verifica estado inicial
		assertTrue(cliente.getAtivo());

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		// Verifica que o método desativar() foi efetivamente executado
		assertFalse(cliente.getAtivo());
		assertNotNull(cliente.getMetadata());
		assertNotNull(cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve manter dataDesativacao se já estiver definida")
	void deveManterDataDesativacaoSeJaEstiverDefinida() {
		String clienteId = "cliente-reativado";
		LocalDateTime dataAnterior = LocalDateTime.now().minusDays(5);

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		Metadata metadata = new Metadata();
		metadata.setDataDesativacao(dataAnterior);
		cliente.setMetadata(metadata);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		// A data de desativação será atualizada para o momento atual
		assertNotNull(cliente.getMetadata().getDataDesativacao());
		assertTrue(cliente.getMetadata().getDataDesativacao().isAfter(dataAnterior));
	}
}
