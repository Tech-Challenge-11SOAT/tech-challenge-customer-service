package br.com.postech.techchallange_customer.application.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeletarClienteService Tests")
class DeletarClienteServiceTest {

	@Mock
	private ClienteRepositoryPort clienteRepository;

	private DeletarClienteService service;

	@BeforeEach
	void setUp() {
		service = new DeletarClienteService(clienteRepository);
	}

	@Test
	@DisplayName("Deve deletar cliente com sucesso quando cliente existe")
	void deveDeletarClienteComSucessoQuandoClienteExiste() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		assertDoesNotThrow(() -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository).deleteByClienteId(clienteId);
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
		verify(clienteRepository, never()).deleteByClienteId(anyString());
	}

	@Test
	@DisplayName("Deve verificar existência do cliente antes de deletar")
	void deveVerificarExistenciaDoClienteAntesDeDeletar() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		service.execute(clienteId);

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository).deleteByClienteId(clienteId);
		verifyNoMoreInteractions(clienteRepository);
	}

	@Test
	@DisplayName("Deve deletar cliente ativo")
	void deveDeletarClienteAtivo() {
		String clienteId = "cliente-ativo";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("Cliente Ativo");
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		assertDoesNotThrow(() -> service.execute(clienteId));

		verify(clienteRepository).deleteByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve deletar cliente inativo")
	void deveDeletarClienteInativo() {
		String clienteId = "cliente-inativo";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("Cliente Inativo");
		cliente.setAtivo(false);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		assertDoesNotThrow(() -> service.execute(clienteId));

		verify(clienteRepository).deleteByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve chamar deleteByClienteId com ID correto")
	void deveChamarDeleteByClienteIdComIdCorreto() {
		String clienteId = "cliente-especifico-456";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		service.execute(clienteId);

		verify(clienteRepository).deleteByClienteId(clienteId);
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
		verify(clienteRepository, never()).deleteByClienteId(anyString());
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
		verify(clienteRepository, never()).deleteByClienteId(anyString());
	}

	@Test
	@DisplayName("Deve deletar cliente com dados completos")
	void deveDeletarClienteComDadosCompletos() {
		String clienteId = "cliente-completo";

		Cliente cliente = new Cliente();
		cliente.setId("id-123");
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("João Silva Completo");
		cliente.setEmailCliente("completo@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setTelefone("11987654321");
		cliente.setAtivo(true);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		assertDoesNotThrow(() -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository).deleteByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve deletar cliente com dados mínimos")
	void deveDeletarClienteComDadosMinimos() {
		String clienteId = "cliente-minimo";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		assertDoesNotThrow(() -> service.execute(clienteId));

		verify(clienteRepository).deleteByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve executar deleção sem retornar valor")
	void deveExecutarDelecaoSemRetornarValor() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		// O método execute é void, então não deve retornar nada
		service.execute(clienteId);

		verify(clienteRepository).deleteByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve chamar repositório exatamente uma vez para verificar e uma vez para deletar")
	void deveChamarRepositorioExatamenteUmaVezParaVerificarEUmaVezParaDeletar() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		service.execute(clienteId);

		verify(clienteRepository, times(1)).findByClienteId(clienteId);
		verify(clienteRepository, times(1)).deleteByClienteId(clienteId);
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
		assertTrue(exception.getMessage().contains("não encontrado") ||
				exception.getMessage().contains("não existe"));
	}

	@Test
	@DisplayName("Deve deletar múltiplos clientes em sequência")
	void deveDeletarMultiplosClientesEmSequencia() {
		String clienteId1 = "cliente-1";
		String clienteId2 = "cliente-2";
		String clienteId3 = "cliente-3";

		Cliente cliente1 = new Cliente();
		cliente1.setClienteId(clienteId1);

		Cliente cliente2 = new Cliente();
		cliente2.setClienteId(clienteId2);

		Cliente cliente3 = new Cliente();
		cliente3.setClienteId(clienteId3);

		when(clienteRepository.findByClienteId(clienteId1)).thenReturn(Optional.of(cliente1));
		when(clienteRepository.findByClienteId(clienteId2)).thenReturn(Optional.of(cliente2));
		when(clienteRepository.findByClienteId(clienteId3)).thenReturn(Optional.of(cliente3));

		doNothing().when(clienteRepository).deleteByClienteId(anyString());

		assertDoesNotThrow(() -> service.execute(clienteId1));
		assertDoesNotThrow(() -> service.execute(clienteId2));
		assertDoesNotThrow(() -> service.execute(clienteId3));

		verify(clienteRepository).deleteByClienteId(clienteId1);
		verify(clienteRepository).deleteByClienteId(clienteId2);
		verify(clienteRepository).deleteByClienteId(clienteId3);
	}

	@Test
	@DisplayName("Deve criar instância do serviço com construtor")
	void deveCriarInstanciaDoServicoComConstrutor() {
		DeletarClienteService novoService = new DeletarClienteService(clienteRepository);
		assertNotNull(novoService);
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório se houver erro ao verificar existência")
	void devePropagararExcecaoDoRepositorioSeHouverErroAoVerificarExistencia() {
		String clienteId = "cliente-com-erro";

		when(clienteRepository.findByClienteId(clienteId))
				.thenThrow(new RuntimeException("Erro no banco de dados"));

		assertThrows(RuntimeException.class, () -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository, never()).deleteByClienteId(anyString());
	}

	@Test
	@DisplayName("Deve propagar exceção do repositório se houver erro ao deletar")
	void devePropagararExcecaoDoRepositorioSeHouverErroAoDeletar() {
		String clienteId = "cliente-erro-delete";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doThrow(new RuntimeException("Erro ao deletar")).when(clienteRepository).deleteByClienteId(clienteId);

		assertThrows(RuntimeException.class, () -> service.execute(clienteId));

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository).deleteByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve deletar cliente independente do estado dos dados")
	void deveDeletarClienteIndependenteDoEstadoDosDados() {
		String clienteId = "cliente-qualquer-estado";

		// Cliente com alguns campos null
		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente(null);
		cliente.setEmailCliente(null);
		cliente.setCpfCliente(null);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		assertDoesNotThrow(() -> service.execute(clienteId));

		verify(clienteRepository).deleteByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve usar Optional.isPresent para verificar existência")
	void deveUsarOptionalIsPresentParaVerificarExistencia() {
		String clienteId = "cliente-present";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);

		Optional<Cliente> optionalCliente = Optional.of(cliente);
		when(clienteRepository.findByClienteId(clienteId)).thenReturn(optionalCliente);
		doNothing().when(clienteRepository).deleteByClienteId(clienteId);

		service.execute(clienteId);

		// Verifica que o método foi chamado e que isPresent() retornou true
		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository).deleteByClienteId(clienteId);
	}
}
