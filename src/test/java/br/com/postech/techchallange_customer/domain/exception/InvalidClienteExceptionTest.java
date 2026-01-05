package br.com.postech.techchallange_customer.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvalidClienteExceptionTest {

	@Test
	@DisplayName("Deve criar InvalidClienteException com mensagem simples")
	void deveCriarInvalidClienteExceptionComMensagemSimples() {
		String mensagem = "Cliente inválido";
		InvalidClienteException exception = new InvalidClienteException(mensagem);

		assertNotNull(exception);
		assertEquals(mensagem, exception.getMessage());
	}

	@Test
	@DisplayName("Deve criar InvalidClienteException com campo e motivo")
	void deveCriarInvalidClienteExceptionComCampoEMotivo() {
		String campo = "email";
		String motivo = "formato inválido";
		InvalidClienteException exception = new InvalidClienteException(campo, motivo);

		assertNotNull(exception);
		assertEquals("Campo 'email' inválido: formato inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Deve formatar mensagem corretamente com campo e motivo")
	void deveFormatarMensagemCorretamenteComCampoEMotivo() {
		InvalidClienteException exception = new InvalidClienteException("cpf", "deve conter 11 dígitos");

		assertEquals("Campo 'cpf' inválido: deve conter 11 dígitos", exception.getMessage());
	}

	@Test
	@DisplayName("Deve ser uma DomainException")
	void deveSerUmaDomainException() {
		InvalidClienteException exception = new InvalidClienteException("Erro");

		assertTrue(exception instanceof DomainException);
	}

	@Test
	@DisplayName("Deve ser uma RuntimeException")
	void deveSerUmaRuntimeException() {
		InvalidClienteException exception = new InvalidClienteException("Erro");

		assertTrue(exception instanceof RuntimeException);
	}

	@Test
	@DisplayName("Deve criar exceção para campo nome inválido")
	void deveCriarExcecaoParaCampoNomeInvalido() {
		InvalidClienteException exception = new InvalidClienteException("nome", "não pode ser vazio");

		assertEquals("Campo 'nome' inválido: não pode ser vazio", exception.getMessage());
	}

	@Test
	@DisplayName("Deve criar exceção para campo CPF inválido")
	void deveCriarExcecaoParaCampoCpfInvalido() {
		InvalidClienteException exception = new InvalidClienteException("cpf", "formato inválido");

		assertEquals("Campo 'cpf' inválido: formato inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Deve criar exceção para campo email inválido")
	void deveCriarExcecaoParaCampoEmailInvalido() {
		InvalidClienteException exception = new InvalidClienteException("email", "formato inválido");

		assertEquals("Campo 'email' inválido: formato inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Deve criar exceção para campo telefone inválido")
	void deveCriarExcecaoParaCampoTelefoneInvalido() {
		InvalidClienteException exception = new InvalidClienteException("telefone", "deve conter apenas números");

		assertEquals("Campo 'telefone' inválido: deve conter apenas números", exception.getMessage());
	}

	@Test
	@DisplayName("Deve ser lançada e capturada corretamente")
	void deveSerLancadaECapturadaCorretamente() {
		assertThrows(InvalidClienteException.class, () -> {
			throw new InvalidClienteException("campo", "motivo");
		});
	}

	@Test
	@DisplayName("Deve capturar a mensagem ao lançar exceção")
	void deveCapturarMensagemAoLancarExcecao() {
		InvalidClienteException exception = assertThrows(InvalidClienteException.class, () -> {
			throw new InvalidClienteException("endereco", "incompleto");
		});

		assertEquals("Campo 'endereco' inválido: incompleto", exception.getMessage());
	}

	@Test
	@DisplayName("Deve permitir campo com espaços")
	void devePermitirCampoComEspacos() {
		InvalidClienteException exception = new InvalidClienteException("nome completo",
				"deve ter pelo menos 3 caracteres");

		assertEquals("Campo 'nome completo' inválido: deve ter pelo menos 3 caracteres", exception.getMessage());
	}

	@Test
	@DisplayName("Deve permitir motivo com múltiplas palavras")
	void devePermitirMotivoComMultiplasPalavras() {
		InvalidClienteException exception = new InvalidClienteException("cpf",
				"deve conter exatamente 11 dígitos numéricos");

		assertEquals("Campo 'cpf' inválido: deve conter exatamente 11 dígitos numéricos", exception.getMessage());
	}

	@Test
	@DisplayName("Deve criar exceção com mensagem customizada")
	void deveCriarExcecaoComMensagemCustomizada() {
		String mensagemCustomizada = "Validação falhou: dados inconsistentes";
		InvalidClienteException exception = new InvalidClienteException(mensagemCustomizada);

		assertEquals(mensagemCustomizada, exception.getMessage());
	}

	@Test
	@DisplayName("Deve preservar stack trace")
	void devePreservarStackTrace() {
		InvalidClienteException exception = new InvalidClienteException("campo", "motivo");

		assertNotNull(exception.getStackTrace());
		assertTrue(exception.getStackTrace().length > 0);
	}

	@Test
	@DisplayName("Deve ser capturada como DomainException")
	void deveSerCapturadaComoDomainException() {
		DomainException exception = assertThrows(DomainException.class, () -> {
			throw new InvalidClienteException("campo", "motivo");
		});

		assertTrue(exception instanceof InvalidClienteException);
	}

	@Test
	@DisplayName("Deve ser capturada como RuntimeException")
	void deveSerCapturadaComoRuntimeException() {
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			throw new InvalidClienteException("campo", "motivo");
		});

		assertTrue(exception instanceof InvalidClienteException);
	}

	@Test
	@DisplayName("Deve formatar mensagem com caracteres especiais")
	void deveFormatarMensagemComCaracteresEspeciais() {
		InvalidClienteException exception = new InvalidClienteException("email", "não pode conter '@' duplicado");

		assertTrue(exception.getMessage().contains("'email'"));
		assertTrue(exception.getMessage().contains("não pode conter '@' duplicado"));
	}

	@Test
	@DisplayName("Deve aceitar campo vazio")
	void deveAceitarCampoVazio() {
		InvalidClienteException exception = new InvalidClienteException("", "motivo qualquer");

		assertEquals("Campo '' inválido: motivo qualquer", exception.getMessage());
	}

	@Test
	@DisplayName("Deve aceitar motivo vazio")
	void deveAceitarMotivoVazio() {
		InvalidClienteException exception = new InvalidClienteException("campo", "");

		assertEquals("Campo 'campo' inválido: ", exception.getMessage());
	}
}
