package br.com.postech.techchallange_customer.infrastructure.rest.exception;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.postech.techchallange_customer.domain.exception.ClienteAlreadyExistsException;
import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.exception.DomainException;
import br.com.postech.techchallange_customer.domain.exception.InvalidClienteException;
import jakarta.servlet.http.HttpServletRequest;

@DisplayName("Testes Unitários - GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

	private GlobalExceptionHandler handler;
	private HttpServletRequest request;

	@BeforeEach
	void setUp() {
		handler = new GlobalExceptionHandler();
		request = mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("/api/v1/clientes");
	}

	// ==================== Testes ClienteNotFoundException ====================

	@Test
	@DisplayName("Deve tratar ClienteNotFoundException e retornar 404")
	void deveTratarClienteNotFoundExceptionRetornar404() {
		ClienteNotFoundException exception = new ClienteNotFoundException("123");

		ResponseEntity<ErrorResponse> response = handler.handleClienteNotFound(exception, request);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(404, response.getBody().getStatus());
		assertEquals("Not Found", response.getBody().getError());
		assertEquals("Cliente não encontrado com ID: 123", response.getBody().getMessage());
		assertEquals("/api/v1/clientes", response.getBody().getPath());
	}

	@Test
	@DisplayName("Deve tratar ClienteNotFoundException com dois parâmetros")
	void deveTratarClienteNotFoundExceptionComDoisParametros() {
		ClienteNotFoundException exception = new ClienteNotFoundException("CPF", "12345678901");

		ResponseEntity<ErrorResponse> response = handler.handleClienteNotFound(exception, request);

		assertNotNull(response);
		assertEquals("Cliente não encontrado com CPF: 12345678901", response.getBody().getMessage());
	}

	@Test
	@DisplayName("Deve tratar ClienteNotFoundException com timestamp")
	void deveTratarClienteNotFoundExceptionComTimestamp() {
		ClienteNotFoundException exception = new ClienteNotFoundException("123");

		ResponseEntity<ErrorResponse> response = handler.handleClienteNotFound(exception, request);

		assertNotNull(response.getBody().getTimestamp());
	}

	@Test
	@DisplayName("Deve tratar ClienteNotFoundException com URI diferente")
	void deveTratarClienteNotFoundExceptionComUri() {
		when(request.getRequestURI()).thenReturn("/api/v1/clientes/123");
		ClienteNotFoundException exception = new ClienteNotFoundException("123");

		ResponseEntity<ErrorResponse> response = handler.handleClienteNotFound(exception, request);

		assertEquals("/api/v1/clientes/123", response.getBody().getPath());
	}

	// ==================== Testes ClienteAlreadyExistsException
	// ====================

	@Test
	@DisplayName("Deve tratar ClienteAlreadyExistsException e retornar 409")
	void deveTratarClienteAlreadyExistsExceptionRetornar409() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "12345678901");

		ResponseEntity<ErrorResponse> response = handler.handleClienteAlreadyExists(exception, request);

		assertNotNull(response);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals(409, response.getBody().getStatus());
		assertEquals("Conflict", response.getBody().getError());
		assertTrue(response.getBody().getMessage().contains("CPF"));
		assertEquals("/api/v1/clientes", response.getBody().getPath());
	}

	@Test
	@DisplayName("Deve tratar ClienteAlreadyExistsException com email duplicado")
	void deveTratarClienteAlreadyExistsExceptionEmail() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("EMAIL", "teste@example.com");

		ResponseEntity<ErrorResponse> response = handler.handleClienteAlreadyExists(exception, request);

		assertTrue(response.getBody().getMessage().contains("EMAIL"));
		assertTrue(response.getBody().getMessage().contains("teste@example.com"));
	}

	@Test
	@DisplayName("Deve tratar ClienteAlreadyExistsException com timestamp")
	void deveTratarClienteAlreadyExistsExceptionComTimestamp() {
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "12345678901");

		ResponseEntity<ErrorResponse> response = handler.handleClienteAlreadyExists(exception, request);

		assertNotNull(response.getBody().getTimestamp());
	}

	@Test
	@DisplayName("Deve tratar ClienteAlreadyExistsException com URI específica")
	void deveTratarClienteAlreadyExistsExceptionComUri() {
		when(request.getRequestURI()).thenReturn("/api/v1/clientes/criar");
		ClienteAlreadyExistsException exception = new ClienteAlreadyExistsException("CPF", "12345678901");

		ResponseEntity<ErrorResponse> response = handler.handleClienteAlreadyExists(exception, request);

		assertEquals("/api/v1/clientes/criar", response.getBody().getPath());
	}

	// ==================== Testes InvalidClienteException ====================

	@Test
	@DisplayName("Deve tratar InvalidClienteException e retornar 400")
	void deveTratarInvalidClienteExceptionRetornar400() {
		InvalidClienteException exception = new InvalidClienteException("CPF inválido");

		ResponseEntity<ErrorResponse> response = handler.handleInvalidCliente(exception, request);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(400, response.getBody().getStatus());
		assertEquals("Bad Request", response.getBody().getError());
		assertEquals("CPF inválido", response.getBody().getMessage());
	}

	@Test
	@DisplayName("Deve tratar InvalidClienteException com mensagem específica")
	void deveTratarInvalidClienteExceptionComMensagem() {
		InvalidClienteException exception = new InvalidClienteException("Email em formato inválido");

		ResponseEntity<ErrorResponse> response = handler.handleInvalidCliente(exception, request);

		assertEquals("Email em formato inválido", response.getBody().getMessage());
	}

	@Test
	@DisplayName("Deve tratar InvalidClienteException com timestamp")
	void deveTratarInvalidClienteExceptionComTimestamp() {
		InvalidClienteException exception = new InvalidClienteException("Dados inválidos");

		ResponseEntity<ErrorResponse> response = handler.handleInvalidCliente(exception, request);

		assertNotNull(response.getBody().getTimestamp());
	}

	@Test
	@DisplayName("Deve tratar InvalidClienteException com path correto")
	void deveTratarInvalidClienteExceptionComPath() {
		when(request.getRequestURI()).thenReturn("/api/v1/clientes/atualizar");
		InvalidClienteException exception = new InvalidClienteException("Telefone inválido");

		ResponseEntity<ErrorResponse> response = handler.handleInvalidCliente(exception, request);

		assertEquals("/api/v1/clientes/atualizar", response.getBody().getPath());
	}

	// ==================== Testes DomainException ====================

	@Test
	@DisplayName("Deve tratar DomainException e retornar 400")
	void deveTratarDomainExceptionRetornar400() {
		DomainException exception = new DomainException("Erro de domínio");

		ResponseEntity<ErrorResponse> response = handler.handleDomainException(exception, request);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(400, response.getBody().getStatus());
		assertEquals("Domain Error", response.getBody().getError());
		assertEquals("Erro de domínio", response.getBody().getMessage());
	}

	@Test
	@DisplayName("Deve tratar DomainException com mensagem específica")
	void deveTratarDomainExceptionComMensagem() {
		DomainException exception = new DomainException("Cliente inativo não pode ser atualizado");

		ResponseEntity<ErrorResponse> response = handler.handleDomainException(exception, request);

		assertEquals("Cliente inativo não pode ser atualizado", response.getBody().getMessage());
	}

	@Test
	@DisplayName("Deve tratar DomainException com timestamp")
	void deveTratarDomainExceptionComTimestamp() {
		DomainException exception = new DomainException("Erro");

		ResponseEntity<ErrorResponse> response = handler.handleDomainException(exception, request);

		assertNotNull(response.getBody().getTimestamp());
	}

	@Test
	@DisplayName("Deve tratar DomainException com URI diferente")
	void deveTratarDomainExceptionComUri() {
		when(request.getRequestURI()).thenReturn("/api/v1/clientes/desativar");
		DomainException exception = new DomainException("Operação não permitida");

		ResponseEntity<ErrorResponse> response = handler.handleDomainException(exception, request);

		assertEquals("/api/v1/clientes/desativar", response.getBody().getPath());
	}

	// ==================== Testes MethodArgumentNotValidException
	// ====================

	@Test
	@DisplayName("Deve tratar MethodArgumentNotValidException e retornar 400")
	void deveTratarMethodArgumentNotValidExceptionRetornar400() {
		MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(exception.getBindingResult()).thenReturn(bindingResult);
		when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());

		ResponseEntity<ErrorResponse> response = handler.handleValidationErrors(exception, request);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(400, response.getBody().getStatus());
		assertEquals("Validation Error", response.getBody().getError());
		assertEquals("Erro de validação nos campos", response.getBody().getMessage());
	}

	@Test
	@DisplayName("Deve tratar MethodArgumentNotValidException com múltiplos erros")
	void deveTratarMethodArgumentNotValidExceptionComMultiplosErros() {
		MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = mock(BindingResult.class);

		FieldError error1 = new FieldError("clienteDTO", "nome", "Nome é obrigatório");
		FieldError error2 = new FieldError("clienteDTO", "cpf", "CPF inválido");

		when(exception.getBindingResult()).thenReturn(bindingResult);
		when(bindingResult.getFieldErrors()).thenReturn(List.of(error1, error2));

		ResponseEntity<ErrorResponse> response = handler.handleValidationErrors(exception, request);

		assertNotNull(response.getBody().getFieldErrors());
		assertEquals(2, response.getBody().getFieldErrors().size());
	}

	@Test
	@DisplayName("Deve tratar MethodArgumentNotValidException com rejected value")
	void deveTratarMethodArgumentNotValidExceptionComRejectedValue() {
		MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = mock(BindingResult.class);

		FieldError error = new FieldError("clienteDTO", "email", "abc", false, null, null, "Email inválido");

		when(exception.getBindingResult()).thenReturn(bindingResult);
		when(bindingResult.getFieldErrors()).thenReturn(List.of(error));

		ResponseEntity<ErrorResponse> response = handler.handleValidationErrors(exception, request);

		assertEquals(1, response.getBody().getFieldErrors().size());
		assertEquals("email", response.getBody().getFieldErrors().get(0).getField());
		assertEquals("Email inválido", response.getBody().getFieldErrors().get(0).getMessage());
	}

	@Test
	@DisplayName("Deve tratar MethodArgumentNotValidException sem erros")
	void deveTratarMethodArgumentNotValidExceptionSemErros() {
		MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = mock(BindingResult.class);

		when(exception.getBindingResult()).thenReturn(bindingResult);
		when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());

		ResponseEntity<ErrorResponse> response = handler.handleValidationErrors(exception, request);

		assertTrue(response.getBody().getFieldErrors() == null || response.getBody().getFieldErrors().isEmpty());
	}

	@Test
	@DisplayName("Deve tratar MethodArgumentNotValidException com timestamp")
	void deveTratarMethodArgumentNotValidExceptionComTimestamp() {
		MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = mock(BindingResult.class);

		when(exception.getBindingResult()).thenReturn(bindingResult);
		when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());

		ResponseEntity<ErrorResponse> response = handler.handleValidationErrors(exception, request);

		assertNotNull(response.getBody().getTimestamp());
	}

	@Test
	@DisplayName("Deve tratar MethodArgumentNotValidException com campos de endereço")
	void deveTratarMethodArgumentNotValidExceptionComCamposEndereco() {
		MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = mock(BindingResult.class);

		FieldError error = new FieldError("clienteDTO", "endereco.rua", "Rua é obrigatória");

		when(exception.getBindingResult()).thenReturn(bindingResult);
		when(bindingResult.getFieldErrors()).thenReturn(List.of(error));

		ResponseEntity<ErrorResponse> response = handler.handleValidationErrors(exception, request);

		assertEquals("endereco.rua", response.getBody().getFieldErrors().get(0).getField());
	}

	// ==================== Testes Exception genérica ====================

	@Test
	@DisplayName("Deve tratar Exception genérica e retornar 500")
	void deveTratarExceptionGenericaRetornar500() {
		Exception exception = new Exception("Erro inesperado");

		ResponseEntity<ErrorResponse> response = handler.handleGenericException(exception, request);

		assertNotNull(response);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(500, response.getBody().getStatus());
		assertEquals("Internal Server Error", response.getBody().getError());
		assertEquals("Ocorreu um erro interno no servidor", response.getBody().getMessage());
	}

	@Test
	@DisplayName("Deve tratar RuntimeException e retornar 500")
	void deveTratarRuntimeExceptionRetornar500() {
		RuntimeException exception = new RuntimeException("Erro de runtime");

		ResponseEntity<ErrorResponse> response = handler.handleGenericException(exception, request);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve tratar NullPointerException e retornar 500")
	void deveTratarNullPointerExceptionRetornar500() {
		NullPointerException exception = new NullPointerException("Referência nula");

		ResponseEntity<ErrorResponse> response = handler.handleGenericException(exception, request);

		assertEquals(500, response.getBody().getStatus());
	}

	@Test
	@DisplayName("Deve tratar Exception genérica com timestamp")
	void deveTratarExceptionGenericaComTimestamp() {
		Exception exception = new Exception("Erro");

		ResponseEntity<ErrorResponse> response = handler.handleGenericException(exception, request);

		assertNotNull(response.getBody().getTimestamp());
	}

	@Test
	@DisplayName("Deve tratar IllegalArgumentException e retornar 500")
	void deveTratarIllegalArgumentExceptionRetornar500() {
		IllegalArgumentException exception = new IllegalArgumentException("Argumento inválido");

		ResponseEntity<ErrorResponse> response = handler.handleGenericException(exception, request);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Internal Server Error", response.getBody().getError());
	}

	@Test
	@DisplayName("Deve tratar Exception com URI diferente")
	void deveTratarExceptionComUri() {
		when(request.getRequestURI()).thenReturn("/api/v1/outra-rota");
		Exception exception = new Exception("Erro genérico");

		ResponseEntity<ErrorResponse> response = handler.handleGenericException(exception, request);

		assertEquals("/api/v1/outra-rota", response.getBody().getPath());
	}

	// ==================== Testes de integração/validação ====================

	@Test
	@DisplayName("Deve validar que todas as exceptions retornam ErrorResponse")
	void deveValidarQueTodasExceptionsRetornamErrorResponse() {
		ClienteNotFoundException ex1 = new ClienteNotFoundException("123");
		ClienteAlreadyExistsException ex2 = new ClienteAlreadyExistsException("CPF", "123");
		InvalidClienteException ex3 = new InvalidClienteException("Erro");
		DomainException ex4 = new DomainException("Erro");
		Exception ex5 = new Exception("Erro");

		assertNotNull(handler.handleClienteNotFound(ex1, request).getBody());
		assertNotNull(handler.handleClienteAlreadyExists(ex2, request).getBody());
		assertNotNull(handler.handleInvalidCliente(ex3, request).getBody());
		assertNotNull(handler.handleDomainException(ex4, request).getBody());
		assertNotNull(handler.handleGenericException(ex5, request).getBody());
	}

	@Test
	@DisplayName("Deve validar códigos de status corretos")
	void deveValidarCodigosStatusCorretos() {
		ClienteNotFoundException ex1 = new ClienteNotFoundException("123");
		ClienteAlreadyExistsException ex2 = new ClienteAlreadyExistsException("CPF", "123");
		InvalidClienteException ex3 = new InvalidClienteException("Erro");

		assertEquals(404, handler.handleClienteNotFound(ex1, request).getBody().getStatus());
		assertEquals(409, handler.handleClienteAlreadyExists(ex2, request).getBody().getStatus());
		assertEquals(400, handler.handleInvalidCliente(ex3, request).getBody().getStatus());
	}

	@Test
	@DisplayName("Deve validar que handlers incluem path")
	void deveValidarQueHandlersIncluemPath() {
		ClienteNotFoundException ex = new ClienteNotFoundException("123");

		ResponseEntity<ErrorResponse> response = handler.handleClienteNotFound(ex, request);

		assertNotNull(response.getBody().getPath());
		assertEquals("/api/v1/clientes", response.getBody().getPath());
	}
}
