package br.com.postech.techchallange_customer.domain.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class ClienteNotFoundExceptionTest {

	@Test
	@DisplayName("Deve criar ClienteNotFoundException com clienteId")
	void deveCriarClienteNotFoundExceptionComClienteId() {
		String clienteId = "cliente-123";
		ClienteNotFoundException exception = new ClienteNotFoundException(clienteId);
		
		assertNotNull(exception);
		assertEquals("Cliente não encontrado com ID: cliente-123", exception.getMessage());
	}

	@Test
	@DisplayName("Deve criar ClienteNotFoundException com campo e valor")
	void deveCriarClienteNotFoundExceptionComCampoEValor() {
		String campo = "CPF";
		String valor = "12345678901";
		ClienteNotFoundException exception = new ClienteNotFoundException(campo, valor);
		
		assertNotNull(exception);
		assertEquals("Cliente não encontrado com CPF: 12345678901", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem corretamente com clienteId")
	void deveFormatarMensagemCorretamenteComClienteId() {
		ClienteNotFoundException exception = new ClienteNotFoundException("abc-123-xyz");
		
		assertEquals("Cliente não encontrado com ID: abc-123-xyz", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem corretamente para CPF")
	void deveFormatarMensagemCorretamenteParaCpf() {
		ClienteNotFoundException exception = new ClienteNotFoundException("CPF", "12345678901");
		
		assertEquals("Cliente não encontrado com CPF: 12345678901", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem corretamente para email")
	void deveFormatarMensagemCorretamenteParaEmail() {
		ClienteNotFoundException exception = new ClienteNotFoundException("email", "joao@email.com");
		
		assertEquals("Cliente não encontrado com email: joao@email.com", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem corretamente para telefone")
	void deveFormatarMensagemCorretamenteParaTelefone() {
		ClienteNotFoundException exception = new ClienteNotFoundException("telefone", "11987654321");
		
		assertEquals("Cliente não encontrado com telefone: 11987654321", exception.getMessage());
	}

	@Test
	@DisplayName("Deve ser uma DomainException")
	void deveSerUmaDomainException() {
		ClienteNotFoundException exception = new ClienteNotFoundException("cliente-123");
		
		assertTrue(exception instanceof DomainException);
	}

	@Test
	@DisplayName("Deve ser uma RuntimeException")
	void deveSerUmaRuntimeException() {
		ClienteNotFoundException exception = new ClienteNotFoundException("cliente-123");
		
		assertTrue(exception instanceof RuntimeException);
	}

	@Test
	@DisplayName("Deve ser lançada e capturada corretamente com clienteId")
	void deveSerLancadaECapturadaCorretamenteComClienteId() {
		assertThrows(ClienteNotFoundException.class, () -> {
			throw new ClienteNotFoundException("cliente-456");
		});
	}

	@Test
	@DisplayName("Deve ser lançada e capturada corretamente com campo e valor")
	void deveSerLancadaECapturadaCorretamenteComCampoEValor() {
		assertThrows(ClienteNotFoundException.class, () -> {
			throw new ClienteNotFoundException("CPF", "12345678901");
		});
	}

	@Test
	@DisplayName("Deve capturar a mensagem ao lançar exceção com clienteId")
	void deveCapturarMensagemAoLancarExcecaoComClienteId() {
		ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
			throw new ClienteNotFoundException("cliente-789");
		});
		
		assertEquals("Cliente não encontrado com ID: cliente-789", exception.getMessage());
	}

	@Test
	@DisplayName("Deve capturar a mensagem ao lançar exceção com campo e valor")
	void deveCapturarMensagemAoLancarExcecaoComCampoEValor() {
		ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
			throw new ClienteNotFoundException("email", "maria@email.com");
		});
		
		assertEquals("Cliente não encontrado com email: maria@email.com", exception.getMessage());
	}

	@Test
	@DisplayName("Deve preservar stack trace")
	void devePreservarStackTrace() {
		ClienteNotFoundException exception = new ClienteNotFoundException("cliente-123");
		
		assertNotNull(exception.getStackTrace());
		assertTrue(exception.getStackTrace().length > 0);
	}

	@Test
	@DisplayName("Deve ser capturada como DomainException")
	void deveSerCapturadaComoDomainException() {
		DomainException exception = assertThrows(DomainException.class, () -> {
			throw new ClienteNotFoundException("cliente-123");
		});
		
		assertTrue(exception instanceof ClienteNotFoundException);
	}

	@Test
	@DisplayName("Deve ser capturada como RuntimeException")
	void deveSerCapturadaComoRuntimeException() {
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			throw new ClienteNotFoundException("cliente-123");
		});
		
		assertTrue(exception instanceof ClienteNotFoundException);
	}

	@Test
	@DisplayName("Deve aceitar UUID como clienteId")
	void deveAceitarUuidComoClienteId() {
		String uuid = "550e8400-e29b-41d4-a716-446655440000";
		ClienteNotFoundException exception = new ClienteNotFoundException(uuid);
		
		assertEquals("Cliente não encontrado com ID: 550e8400-e29b-41d4-a716-446655440000", exception.getMessage());
	}

	@Test
	@DisplayName("Deve aceitar campo com letras maiúsculas")
	void deveAceitarCampoComLetrasMaiusculas() {
		ClienteNotFoundException exception = new ClienteNotFoundException("CPF", "12345678901");
		
		assertTrue(exception.getMessage().contains("CPF"));
	}

	@Test
	@DisplayName("Deve aceitar campo com letras minúsculas")
	void deveAceitarCampoComLetrasMinusculas() {
		ClienteNotFoundException exception = new ClienteNotFoundException("cpf", "12345678901");
		
		assertTrue(exception.getMessage().contains("cpf"));
	}

	@Test
	@DisplayName("Deve formatar mensagem com valor contendo caracteres especiais")
	void deveFormatarMensagemComValorContendoCaracteresEspeciais() {
		ClienteNotFoundException exception = new ClienteNotFoundException("email", "usuario+tag@dominio.com.br");
		
		assertEquals("Cliente não encontrado com email: usuario+tag@dominio.com.br", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem com CPF formatado")
	void deveFormatarMensagemComCpfFormatado() {
		ClienteNotFoundException exception = new ClienteNotFoundException("CPF", "123.456.789-01");
		
		assertEquals("Cliente não encontrado com CPF: 123.456.789-01", exception.getMessage());
	}

	@Test
	@DisplayName("Deve aceitar clienteId vazio")
	void deveAceitarClienteIdVazio() {
		ClienteNotFoundException exception = new ClienteNotFoundException("");
		
		assertEquals("Cliente não encontrado com ID: ", exception.getMessage());
	}

	@Test
	@DisplayName("Deve aceitar campo vazio")
	void deveAceitarCampoVazio() {
		ClienteNotFoundException exception = new ClienteNotFoundException("", "valor");
		
		assertEquals("Cliente não encontrado com : valor", exception.getMessage());
	}

	@Test
	@DisplayName("Deve aceitar valor vazio")
	void deveAceitarValorVazio() {
		ClienteNotFoundException exception = new ClienteNotFoundException("campo", "");
		
		assertEquals("Cliente não encontrado com campo: ", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem com espaços no campo")
	void deveFormatarMensagemComEspacosNoCampo() {
		ClienteNotFoundException exception = new ClienteNotFoundException("número do documento", "12345");
		
		assertEquals("Cliente não encontrado com número do documento: 12345", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem com espaços no valor")
	void deveFormatarMensagemComEspacosNoValor() {
		ClienteNotFoundException exception = new ClienteNotFoundException("nome", "João Silva Santos");
		
		assertEquals("Cliente não encontrado com nome: João Silva Santos", exception.getMessage());
	}

	@Test
	@DisplayName("Deve manter formato da mensagem consistente entre construtores")
	void deveManterFormatoDaMensagemConsistenteEntreConstrutores() {
		ClienteNotFoundException exception1 = new ClienteNotFoundException("cliente-123");
		ClienteNotFoundException exception2 = new ClienteNotFoundException("ID", "cliente-123");
		
		assertTrue(exception1.getMessage().contains("cliente-123"));
		assertTrue(exception2.getMessage().contains("cliente-123"));
		assertTrue(exception1.getMessage().contains("ID"));
		assertTrue(exception2.getMessage().contains("ID"));
	}

	@Test
	@DisplayName("Deve diferenciar mensagens dos dois construtores")
	void deveDiferenciarMensagensDosDoisConstrutores() {
		ClienteNotFoundException exception1 = new ClienteNotFoundException("cliente-123");
		ClienteNotFoundException exception2 = new ClienteNotFoundException("CPF", "12345678901");
		
		assertTrue(exception1.getMessage().contains("com ID:"));
		assertTrue(exception2.getMessage().contains("com CPF:"));
		assertNotEquals(exception1.getMessage(), exception2.getMessage());
	}
}
