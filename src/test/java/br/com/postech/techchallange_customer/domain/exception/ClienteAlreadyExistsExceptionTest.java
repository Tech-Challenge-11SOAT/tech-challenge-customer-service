package br.com.postech.techchallange_customer.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClienteAlreadyExistsExceptionTest {

	@Test
	@DisplayName("Deve criar ClienteAlreadyExistsException com campo e valor")
	void deveCriarClienteAlreadyExistsExceptionComCampoEValor() {
		String campo = "CPF";
		String valor = "12345678901";
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException(campo, valor);

		assertNotNull(exception);
		assertEquals("Já existe um cliente cadastrado com CPF: 12345678901", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem corretamente para CPF")
	void deveFormatarMensagemCorretamenteParaCpf() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "12345678901");

		assertEquals("Já existe um cliente cadastrado com CPF: 12345678901", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem corretamente para email")
	void deveFormatarMensagemCorretamenteParaEmail() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("email", "teste@email.com");

		assertEquals("Já existe um cliente cadastrado com email: teste@email.com", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem corretamente para telefone")
	void deveFormatarMensagemCorretamenteParaTelefone() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("telefone", "11987654321");

		assertEquals("Já existe um cliente cadastrado com telefone: 11987654321", exception.getMessage());
	}

	@Test
	@DisplayName("Deve ser uma DomainException")
	void deveSerUmaDomainException() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "12345678901");

		assertTrue(exception instanceof DomainException);
	}

	@Test
	@DisplayName("Deve ser uma RuntimeException")
	void deveSerUmaRuntimeException() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "12345678901");

		assertTrue(exception instanceof RuntimeException);
	}

	@Test
	@DisplayName("Deve ser lançada e capturada corretamente")
	void deveSerLancadaECapturadaCorretamente() {
		assertThrows(ClienteAlreadyExistsException.class, () -> {
			throw new ClienteAlreadyExistsException("CPF", "12345678901");
		});
	}

	@Test
	@DisplayName("Deve capturar a mensagem ao lançar exceção")
	void deveCapturarMensagemAoLancarExcecao() {
		ClienteAlreadyExistsException exception = assertThrows(ClienteAlreadyExistsException.class, () -> {
			throw new ClienteAlreadyExistsException("email", "joao@email.com");
		});

		assertEquals("Já existe um cliente cadastrado com email: joao@email.com", exception.getMessage());
	}

	@Test
	@DisplayName("Deve preservar stack trace")
	void devePreservarStackTrace() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "12345678901");

		assertNotNull(exception.getStackTrace());
		assertTrue(exception.getStackTrace().length > 0);
	}

	@Test
	@DisplayName("Deve ser capturada como DomainException")
	void deveSerCapturadaComoDomainException() {
		DomainException exception = assertThrows(DomainException.class, () -> {
			throw new ClienteAlreadyExistsException("CPF", "12345678901");
		});

		assertTrue(exception instanceof ClienteAlreadyExistsException);
	}

	@Test
	@DisplayName("Deve ser capturada como RuntimeException")
	void deveSerCapturadaComoRuntimeException() {
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			throw new ClienteAlreadyExistsException("CPF", "12345678901");
		});

		assertTrue(exception instanceof ClienteAlreadyExistsException);
	}

	@Test
	@DisplayName("Deve aceitar campo com letras maiúsculas")
	void deveAceitarCampoComLetrasMaiusculas() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "12345678901");

		assertTrue(exception.getMessage().contains("CPF"));
	}

	@Test
	@DisplayName("Deve aceitar campo com letras minúsculas")
	void deveAceitarCampoComLetrasMinusculas() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("cpf", "12345678901");

		assertTrue(exception.getMessage().contains("cpf"));
	}

	@Test
	@DisplayName("Deve formatar mensagem com valor contendo caracteres especiais")
	void deveFormatarMensagemComValorContendoCaracteresEspeciais() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("email",
				"usuario+tag@dominio.com.br");

		assertEquals("Já existe um cliente cadastrado com email: usuario+tag@dominio.com.br", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem com CPF formatado")
	void deveFormatarMensagemComCpfFormatado() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "123.456.789-01");

		assertEquals("Já existe um cliente cadastrado com CPF: 123.456.789-01", exception.getMessage());
	}

	@Test
	@DisplayName("Deve aceitar campo vazio")
	void deveAceitarCampoVazio() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("", "valor");

		assertEquals("Já existe um cliente cadastrado com : valor", exception.getMessage());
	}

	@Test
	@DisplayName("Deve aceitar valor vazio")
	void deveAceitarValorVazio() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("campo", "");

		assertEquals("Já existe um cliente cadastrado com campo: ", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem com espaços no campo")
	void deveFormatarMensagemComEspacosNoCampo() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("número de telefone",
				"11987654321");

		assertEquals("Já existe um cliente cadastrado com número de telefone: 11987654321", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem com espaços no valor")
	void deveFormatarMensagemComEspacosNoValor() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("nome", "João Silva Santos");

		assertEquals("Já existe um cliente cadastrado com nome: João Silva Santos", exception.getMessage());
	}

	@Test
	@DisplayName("Deve manter formato da mensagem consistente")
	void deveManterFormatoDaMensagemConsistente() {
		ClienteAlreadyExistsException exception1 = new ClienteAlreadyExistsException("CPF", "12345678901");
		ClienteAlreadyExistsException exception2 = new ClienteAlreadyExistsException("email", "teste@email.com");

		assertTrue(exception1.getMessage().startsWith("Já existe um cliente cadastrado com"));
		assertTrue(exception2.getMessage().startsWith("Já existe um cliente cadastrado com"));
	}
}
