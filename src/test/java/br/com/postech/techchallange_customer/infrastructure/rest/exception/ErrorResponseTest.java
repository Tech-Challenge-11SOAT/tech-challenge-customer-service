package br.com.postech.techchallange_customer.infrastructure.rest.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorResponse Tests")
class ErrorResponseTest {

	// ==================== Testes do construtor padrão ====================

	@Test
	@DisplayName("Deve criar ErrorResponse usando construtor padrão")
	void deveCriarErrorResponseComConstrutorPadrao() {
		ErrorResponse error = new ErrorResponse();

		assertNotNull(error);
		assertNotNull(error.getTimestamp());
		assertEquals(0, error.getStatus());
		assertNull(error.getError());
		assertNull(error.getMessage());
		assertNull(error.getPath());
		assertNull(error.getFieldErrors());
	}

	// ==================== Testes do construtor parametrizado ====================

	@Test
	@DisplayName("Deve criar ErrorResponse com construtor parametrizado")
	void deveCriarErrorResponseComConstrutorParametrizado() {
		ErrorResponse error = new ErrorResponse(404, "Not Found", "Cliente não encontrado", "/api/v1/clientes/1");

		assertNotNull(error.getTimestamp());
		assertEquals(404, error.getStatus());
		assertEquals("Not Found", error.getError());
		assertEquals("Cliente não encontrado", error.getMessage());
		assertEquals("/api/v1/clientes/1", error.getPath());
		assertNull(error.getFieldErrors());
	}

	@Test
	@DisplayName("Deve criar ErrorResponse com status 400")
	void deveCriarErrorResponseComStatus400() {
		ErrorResponse error = new ErrorResponse(400, "Bad Request", "Dados inválidos", "/api/v1/clientes");

		assertEquals(400, error.getStatus());
		assertEquals("Bad Request", error.getError());
	}

	@Test
	@DisplayName("Deve criar ErrorResponse com status 409")
	void deveCriarErrorResponseComStatus409() {
		ErrorResponse error = new ErrorResponse(409, "Conflict", "Cliente já existe", "/api/v1/clientes");

		assertEquals(409, error.getStatus());
		assertEquals("Conflict", error.getError());
		assertEquals("Cliente já existe", error.getMessage());
	}

	@Test
	@DisplayName("Deve criar ErrorResponse com status 500")
	void deveCriarErrorResponseComStatus500() {
		ErrorResponse error = new ErrorResponse(500, "Internal Server Error", "Erro inesperado", "/api/v1/clientes");

		assertEquals(500, error.getStatus());
		assertEquals("Internal Server Error", error.getError());
	}

	@Test
	@DisplayName("Timestamp deve ser preenchido automaticamente no construtor parametrizado")
	void timestampDeveSerPreenchidoAutomaticamente() {
		LocalDateTime antes = LocalDateTime.now();
		ErrorResponse error = new ErrorResponse(404, "Not Found", "Não encontrado", "/test");
		LocalDateTime depois = LocalDateTime.now();

		assertNotNull(error.getTimestamp());
		assertTrue(!error.getTimestamp().isBefore(antes));
		assertTrue(!error.getTimestamp().isAfter(depois));
	}

	// ==================== Testes de getters e setters de ErrorResponse
	// ====================

	@Test
	@DisplayName("Deve definir e obter timestamp")
	void deveDefinirEObterTimestamp() {
		ErrorResponse error = new ErrorResponse();
		LocalDateTime agora = LocalDateTime.now();

		error.setTimestamp(agora);

		assertEquals(agora, error.getTimestamp());
	}

	@Test
	@DisplayName("Deve definir e obter status")
	void deveDefinirEObterStatus() {
		ErrorResponse error = new ErrorResponse();

		error.setStatus(404);

		assertEquals(404, error.getStatus());
	}

	@Test
	@DisplayName("Deve definir e obter error")
	void deveDefinirEObterError() {
		ErrorResponse error = new ErrorResponse();

		error.setError("Not Found");

		assertEquals("Not Found", error.getError());
	}

	@Test
	@DisplayName("Deve definir e obter message")
	void deveDefinirEObterMessage() {
		ErrorResponse error = new ErrorResponse();

		error.setMessage("Cliente não encontrado");

		assertEquals("Cliente não encontrado", error.getMessage());
	}

	@Test
	@DisplayName("Deve definir e obter path")
	void deveDefinirEObterPath() {
		ErrorResponse error = new ErrorResponse();

		error.setPath("/api/v1/clientes/123");

		assertEquals("/api/v1/clientes/123", error.getPath());
	}

	@Test
	@DisplayName("Deve definir e obter fieldErrors")
	void deveDefinirEObterFieldErrors() {
		ErrorResponse error = new ErrorResponse();
		List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
		fieldErrors.add(new ErrorResponse.FieldError("nome", "Nome é obrigatório"));

		error.setFieldErrors(fieldErrors);

		assertNotNull(error.getFieldErrors());
		assertEquals(1, error.getFieldErrors().size());
	}

	@Test
	@DisplayName("Deve sobrescrever valores com setters")
	void deveSobrescreverValoresComSetters() {
		ErrorResponse error = new ErrorResponse(404, "Not Found", "Original", "/path1");

		error.setStatus(500);
		error.setError("Internal Server Error");
		error.setMessage("Nova mensagem");
		error.setPath("/path2");

		assertEquals(500, error.getStatus());
		assertEquals("Internal Server Error", error.getError());
		assertEquals("Nova mensagem", error.getMessage());
		assertEquals("/path2", error.getPath());
	}

	// ==================== Testes do construtor padrão de FieldError
	// ====================

	@Test
	@DisplayName("Deve criar FieldError usando construtor padrão")
	void deveCriarFieldErrorComConstrutorPadrao() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError();

		assertNotNull(fieldError);
		assertNull(fieldError.getField());
		assertNull(fieldError.getMessage());
		assertNull(fieldError.getRejectedValue());
	}

	// ==================== Testes dos construtores parametrizados de FieldError
	// ====================

	@Test
	@DisplayName("Deve criar FieldError com field e message")
	void deveCriarFieldErrorComFieldEMessage() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError("nome", "Nome é obrigatório");

		assertEquals("nome", fieldError.getField());
		assertEquals("Nome é obrigatório", fieldError.getMessage());
		assertNull(fieldError.getRejectedValue());
	}

	@Test
	@DisplayName("Deve criar FieldError com todos os parâmetros")
	void deveCriarFieldErrorComTodosParametros() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError("idade", "Idade deve ser maior que zero", 0);

		assertEquals("idade", fieldError.getField());
		assertEquals("Idade deve ser maior que zero", fieldError.getMessage());
		assertEquals(0, fieldError.getRejectedValue());
	}

	@Test
	@DisplayName("Deve criar FieldError com rejectedValue null")
	void deveCriarFieldErrorComRejectedValueNull() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError("telefone", "Telefone é obrigatório", null);

		assertEquals("telefone", fieldError.getField());
		assertEquals("Telefone é obrigatório", fieldError.getMessage());
		assertNull(fieldError.getRejectedValue());
	}

	// ==================== Testes de getters e setters de FieldError
	// ====================

	@Test
	@DisplayName("Deve definir e obter field de FieldError")
	void deveDefinirEObterFieldDeFieldError() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError();

		fieldError.setField("endereco");

		assertEquals("endereco", fieldError.getField());
	}

	@Test
	@DisplayName("Deve definir e obter message de FieldError")
	void deveDefinirEObterMessageDeFieldError() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError();

		fieldError.setMessage("Endereço é obrigatório");

		assertEquals("Endereço é obrigatório", fieldError.getMessage());
	}

	@Test
	@DisplayName("Deve definir e obter rejectedValue de FieldError")
	void deveDefinirEObterRejectedValueDeFieldError() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError();

		fieldError.setRejectedValue("valor inválido");

		assertEquals("valor inválido", fieldError.getRejectedValue());
	}

	@Test
	@DisplayName("Deve sobrescrever field com setter")
	void deveSobrescreverFieldComSetter() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError("nome", "Nome inválido");

		fieldError.setField("nomeCompleto");

		assertEquals("nomeCompleto", fieldError.getField());
	}

	@Test
	@DisplayName("Deve sobrescrever message com setter")
	void deveSobrescreverMessageComSetter() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError("cpf", "CPF inválido");

		fieldError.setMessage("CPF já cadastrado");

		assertEquals("CPF já cadastrado", fieldError.getMessage());
	}

	@Test
	@DisplayName("Deve sobrescrever rejectedValue com setter")
	void deveSobrescreverRejectedValueComSetter() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError("valor", "Valor inválido", 100);

		fieldError.setRejectedValue(200);

		assertEquals(200, fieldError.getRejectedValue());
	}

	// ==================== Testes de integração ====================

	@Test
	@DisplayName("Deve adicionar múltiplos FieldErrors ao ErrorResponse")
	void deveAdicionarMultiplosFieldErrors() {
		ErrorResponse error = new ErrorResponse(400, "Bad Request", "Validação falhou", "/api/v1/clientes");

		List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
		fieldErrors.add(new ErrorResponse.FieldError("nome", "Nome é obrigatório"));
		fieldErrors.add(new ErrorResponse.FieldError("cpf", "CPF inválido"));
		fieldErrors.add(new ErrorResponse.FieldError("email", "Email em formato inválido"));

		error.setFieldErrors(fieldErrors);

		assertEquals(3, error.getFieldErrors().size());
		assertEquals("nome", error.getFieldErrors().get(0).getField());
		assertEquals("cpf", error.getFieldErrors().get(1).getField());
		assertEquals("email", error.getFieldErrors().get(2).getField());
	}

	@Test
	@DisplayName("Deve criar ErrorResponse completo com FieldErrors")
	void deveCriarErrorResponseCompletoComFieldErrors() {
		ErrorResponse error = new ErrorResponse(400, "Validation Error", "Erro de validação", "/api/v1/clientes");

		List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
		fieldErrors.add(new ErrorResponse.FieldError("endereco.rua", "Rua é obrigatória", null));
		fieldErrors.add(new ErrorResponse.FieldError("endereco.numero", "Número deve ser positivo", -1));

		error.setFieldErrors(fieldErrors);

		assertNotNull(error.getTimestamp());
		assertEquals(400, error.getStatus());
		assertEquals("Validation Error", error.getError());
		assertEquals("Erro de validação", error.getMessage());
		assertEquals("/api/v1/clientes", error.getPath());
		assertEquals(2, error.getFieldErrors().size());
	}

	@Test
	@DisplayName("Deve criar ErrorResponse sem FieldErrors")
	void deveCriarErrorResponseSemFieldErrors() {
		ErrorResponse error = new ErrorResponse(404, "Not Found", "Cliente não encontrado", "/api/v1/clientes/123");

		assertNull(error.getFieldErrors());
	}

	@Test
	@DisplayName("Deve modificar todos os campos de ErrorResponse")
	void deveModificarTodosCamposDeErrorResponse() {
		ErrorResponse error = new ErrorResponse();
		LocalDateTime timestamp = LocalDateTime.now();
		List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
		fieldErrors.add(new ErrorResponse.FieldError("campo", "mensagem"));

		error.setTimestamp(timestamp);
		error.setStatus(400);
		error.setError("Bad Request");
		error.setMessage("Mensagem de erro");
		error.setPath("/api/test");
		error.setFieldErrors(fieldErrors);

		assertEquals(timestamp, error.getTimestamp());
		assertEquals(400, error.getStatus());
		assertEquals("Bad Request", error.getError());
		assertEquals("Mensagem de erro", error.getMessage());
		assertEquals("/api/test", error.getPath());
		assertEquals(1, error.getFieldErrors().size());
	}

	@Test
	@DisplayName("Deve criar FieldError com campo aninhado")
	void deveCriarFieldErrorComCampoAninhado() {
		ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError(
				"endereco.cidade.nome",
				"Nome da cidade é obrigatório",
				"");

		assertEquals("endereco.cidade.nome", fieldError.getField());
		assertEquals("Nome da cidade é obrigatório", fieldError.getMessage());
		assertEquals("", fieldError.getRejectedValue());
	}

	@Test
	@DisplayName("Deve manter lista vazia de FieldErrors")
	void deveManterListaVaziaDeFieldErrors() {
		ErrorResponse error = new ErrorResponse(400, "Bad Request", "Validação falhou", "/api/v1/clientes");

		List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
		error.setFieldErrors(fieldErrors);

		assertNotNull(error.getFieldErrors());
		assertEquals(0, error.getFieldErrors().size());
	}
}
