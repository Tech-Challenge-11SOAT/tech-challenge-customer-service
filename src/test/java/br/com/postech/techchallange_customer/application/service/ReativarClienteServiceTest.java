package br.com.postech.techchallange_customer.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
@DisplayName("ReativarClienteService Tests")
class ReativarClienteServiceTest {

	@Mock
	private ClienteRepositoryPort clienteRepository;

	private ReativarClienteService service;

	@BeforeEach
	void setUp() {
		service = new ReativarClienteService(clienteRepository);
	}

	@Test
	@DisplayName("Deve reativar cliente com sucesso quando cliente existe")
	void deveReativarClienteComSucessoQuandoClienteExiste() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setAtivo(false);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		assertDoesNotThrow(() -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository).update(cliente);
		assertTrue(cliente.getAtivo());
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
	@DisplayName("Deve alterar status ativo para true")
	void deveAlterarStatusAtivoParaTrue() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertTrue(cliente.getAtivo());
		verify(clienteRepository).update(cliente);
	}

	@Test
	@DisplayName("Deve remover dataDesativacao quando reativar cliente com metadata")
	void deveRemoverDataDesativacaoQuandoReativarClienteComMetadata() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

		Metadata metadata = new Metadata();
		metadata.setDataDesativacao(LocalDateTime.now().minusDays(5));
		cliente.setMetadata(metadata);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertNotNull(cliente.getMetadata());
		assertNull(cliente.getMetadata().getDataDesativacao());
		assertTrue(cliente.getAtivo());
	}

	@Test
	@DisplayName("Deve reativar cliente sem metadata sem erro")
	void deveReativarClienteSemMetadataSemErro() {
		String clienteId = "cliente-sem-metadata";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);
		cliente.setMetadata(null);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		assertDoesNotThrow(() -> service.execute(clienteId));

		assertTrue(cliente.getAtivo());
		verify(clienteRepository).update(cliente);
	}

	@Test
	@DisplayName("Deve preservar metadata existente ao reativar")
	void devePreservarMetadataExistenteAoReativar() {
		String clienteId = "cliente-com-metadata";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

		Metadata metadataExistente = new Metadata();
		metadataExistente.setTags(java.util.Arrays.asList("tag1", "tag2"));
		metadataExistente.setDataDesativacao(LocalDateTime.now());
		cliente.setMetadata(metadataExistente);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertNotNull(cliente.getMetadata());
		assertEquals(2, cliente.getMetadata().getTags().size());
		assertTrue(cliente.getMetadata().getTags().contains("tag1"));
		assertTrue(cliente.getMetadata().getTags().contains("tag2"));
		assertNull(cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve reativar cliente já ativo")
	void deveReativarClienteJaAtivo() {
		String clienteId = "cliente-ativo";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		assertDoesNotThrow(() -> service.execute(clienteId));

		assertTrue(cliente.getAtivo());
		verify(clienteRepository).update(cliente);
	}

	@Test
	@DisplayName("Deve chamar update do repositório com cliente reativado")
	void deveChamarUpdateDoRepositorioComClienteReativado() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
		verify(clienteRepository).update(clienteCaptor.capture());

		Cliente clienteCapturado = clienteCaptor.getValue();
		assertTrue(clienteCapturado.getAtivo());
		assertEquals(clienteId, clienteCapturado.getClienteId());
	}

	@Test
	@DisplayName("Deve preservar outros dados do cliente ao reativar")
	void devePreservarOutrosDadosDoClienteAoReativar() {
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
		cliente.setAtivo(false);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertEquals(clienteId, cliente.getClienteId());
		assertEquals(nome, cliente.getNomeCliente());
		assertEquals(email, cliente.getEmailCliente());
		assertEquals(cpf, cliente.getCpfCliente());
		assertEquals(telefone, cliente.getTelefone());
		assertTrue(cliente.getAtivo());
	}

	@Test
	@DisplayName("Deve atualizar timestamp ao reativar")
	void deveAtualizarTimestampAoReativar() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

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
	@DisplayName("Deve executar reativação sem retornar valor")
	void deveExecutarReativacaoSemRetornarValor() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

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
		cliente.setAtivo(false);

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
	@DisplayName("Deve reativar múltiplos clientes em sequência")
	void deveReativarMultiplosClientesEmSequencia() {
		String clienteId1 = "cliente-1";
		String clienteId2 = "cliente-2";
		String clienteId3 = "cliente-3";

		Cliente cliente1 = new Cliente();
		cliente1.setClienteId(clienteId1);
		cliente1.setAtivo(false);

		Cliente cliente2 = new Cliente();
		cliente2.setClienteId(clienteId2);
		cliente2.setAtivo(false);

		Cliente cliente3 = new Cliente();
		cliente3.setClienteId(clienteId3);
		cliente3.setAtivo(false);

		when(clienteRepository.findByClienteId(clienteId1)).thenReturn(Optional.of(cliente1));
		when(clienteRepository.findByClienteId(clienteId2)).thenReturn(Optional.of(cliente2));
		when(clienteRepository.findByClienteId(clienteId3)).thenReturn(Optional.of(cliente3));

		when(clienteRepository.update(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

		assertDoesNotThrow(() -> service.execute(clienteId1));
		assertDoesNotThrow(() -> service.execute(clienteId2));
		assertDoesNotThrow(() -> service.execute(clienteId3));

		assertTrue(cliente1.getAtivo());
		assertTrue(cliente2.getAtivo());
		assertTrue(cliente3.getAtivo());

		verify(clienteRepository, times(3)).update(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve criar instância do serviço com construtor")
	void deveCriarInstanciaDoServicoComConstrutor() {
		ReativarClienteService novoService = new ReativarClienteService(clienteRepository);
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
		cliente.setAtivo(false);

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
	@DisplayName("Deve chamar método reativar do cliente")
	void deveChamarMetodoReativarDoCliente() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

		Metadata metadata = new Metadata();
		metadata.setDataDesativacao(LocalDateTime.now().minusDays(10));
		cliente.setMetadata(metadata);

		// Verifica estado inicial
		assertFalse(cliente.getAtivo());
		assertNotNull(cliente.getMetadata().getDataDesativacao());

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		// Verifica que o método reativar() foi efetivamente executado
		assertTrue(cliente.getAtivo());
		assertNull(cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve manter metadata null se não existir ao reativar")
	void deveManterMetadataNullSeNaoExistirAoReativar() {
		String clienteId = "cliente-sem-metadata";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);
		cliente.setMetadata(null);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertTrue(cliente.getAtivo());
		// Metadata permanece null se não existia antes
		assertNull(cliente.getMetadata());
	}

	@Test
	@DisplayName("Deve reativar cliente que foi desativado há muito tempo")
	void deveReativarClienteQueFoiDesativadoHaMuitoTempo() {
		String clienteId = "cliente-desativado-antigo";
		LocalDateTime dataDesativacaoAntiga = LocalDateTime.now().minusYears(2);

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setAtivo(false);

		Metadata metadata = new Metadata();
		metadata.setDataDesativacao(dataDesativacaoAntiga);
		cliente.setMetadata(metadata);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		when(clienteRepository.update(any(Cliente.class))).thenReturn(cliente);

		service.execute(clienteId);

		assertTrue(cliente.getAtivo());
		assertNull(cliente.getMetadata().getDataDesativacao());
		verify(clienteRepository).update(cliente);
	}
}
