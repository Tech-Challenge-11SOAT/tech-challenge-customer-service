package br.com.postech.techchallange_customer.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DomainExceptionTest {

	@Test
	@DisplayName("Deve criar DomainException com mensagem")
	void deveCriarDomainExceptionComMensagem() {
		String mensagem = "Erro de domínio";
		DomainException exception = new DomainException(mensagem);

		assertNotNull(exception);
		assertEquals(mensagem, exception.getMessage());
		assertNull(exception.getCause());
	}

	@Test
	@DisplayName("Deve criar DomainException com mensagem e causa")
	void deveCriarDomainExceptionComMensagemECausa() {
		String mensagem = "Erro de domínio";
		Throwable causa = new IllegalArgumentException("Argumento inválido");
		DomainException exception = new DomainException(mensagem, causa);

		assertNotNull(exception);
		assertEquals(mensagem, exception.getMessage());
		assertEquals(causa, exception.getCause());
		assertTrue(exception.getCause() instanceof IllegalArgumentException);
	}

	@Test
	@DisplayName("Deve ser uma RuntimeException")
	void deveSerUmaRuntimeException() {
		DomainException exception = new DomainException("Erro");

		assertTrue(exception instanceof RuntimeException);
	}

	@Test
	@DisplayName("Deve manter a mensagem original quando passada com causa")
	void deveManterMensagemOriginalQuandoPassadaComCausa() {
		String mensagemOriginal = "Mensagem de erro específica";
		Throwable causa = new NullPointerException("NPE");
		DomainException exception = new DomainException(mensagemOriginal, causa);

		assertEquals(mensagemOriginal, exception.getMessage());
		assertNotEquals(causa.getMessage(), exception.getMessage());
	}

	@Test
	@DisplayName("Deve permitir mensagem nula")
	void devePermitirMensagemNula() {
		DomainException exception = new DomainException(null);

		assertNotNull(exception);
		assertNull(exception.getMessage());
	}

	@Test
	@DisplayName("Deve permitir mensagem vazia")
	void devePermitirMensagemVazia() {
		DomainException exception = new DomainException("");

		assertNotNull(exception);
		assertEquals("", exception.getMessage());
	}

	@Test
	@DisplayName("Deve permitir causa nula")
	void devePermitirCausaNula() {
		DomainException exception = new DomainException("Erro", null);

		assertNotNull(exception);
		assertEquals("Erro", exception.getMessage());
		assertNull(exception.getCause());
	}

	@Test
	@DisplayName("Deve ser lançada e capturada corretamente")
	void deveSerLancadaECapturadaCorretamente() {
		assertThrows(DomainException.class, () -> {
			throw new DomainException("Erro de teste");
		});
	}

	@Test
	@DisplayName("Deve capturar a mensagem ao lançar exceção")
	void deveCapturarMensagemAoLancarExcecao() {
		DomainException exception = assertThrows(DomainException.class, () -> {
			throw new DomainException("Erro específico");
		});

		assertEquals("Erro específico", exception.getMessage());
	}

	@Test
	@DisplayName("Deve preservar stack trace")
	void devePreservarStackTrace() {
		DomainException exception = new DomainException("Erro");

		assertNotNull(exception.getStackTrace());
		assertTrue(exception.getStackTrace().length > 0);
	}

	@Test
	@DisplayName("Deve encadear exceções corretamente")
	void deveEncadearExcecoesCorretamente() {
		Exception causaRaiz = new IllegalStateException("Estado inválido");
		RuntimeException causaIntermediaria = new RuntimeException("Erro intermediário", causaRaiz);
		DomainException exception = new DomainException("Erro de domínio", causaIntermediaria);

		assertEquals(causaIntermediaria, exception.getCause());
		assertEquals(causaRaiz, exception.getCause().getCause());
	}
}
