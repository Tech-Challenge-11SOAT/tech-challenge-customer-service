package br.com.postech.techchallange_customer.infrastructure.rest.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.postech.techchallange_customer.domain.exception.ClienteAlreadyExistsException;
import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.exception.DomainException;
import br.com.postech.techchallange_customer.domain.exception.InvalidClienteException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Handler global de exceções para a API REST
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Trata exceção de cliente não encontrado
	 */
	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleClienteNotFound(
			ClienteNotFoundException ex,
			HttpServletRequest request) {

		log.error("Cliente não encontrado: {}", ex.getMessage());

		ErrorResponse error = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				"Not Found",
				ex.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	/**
	 * Trata exceção de cliente já existente (conflito)
	 */
	@ExceptionHandler(ClienteAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleClienteAlreadyExists(
			ClienteAlreadyExistsException ex,
			HttpServletRequest request) {

		log.error("Conflito: {}", ex.getMessage());

		ErrorResponse error = new ErrorResponse(
				HttpStatus.CONFLICT.value(),
				"Conflict",
				ex.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	/**
	 * Trata exceção de dados inválidos
	 */
	@ExceptionHandler(InvalidClienteException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCliente(
			InvalidClienteException ex,
			HttpServletRequest request) {

		log.error("Dados inválidos: {}", ex.getMessage());

		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				ex.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Trata exceções gerais de domínio
	 */
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ErrorResponse> handleDomainException(
			DomainException ex,
			HttpServletRequest request) {

		log.error("Erro de domínio: {}", ex.getMessage());

		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Domain Error",
				ex.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Trata erros de validação do Bean Validation
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(
			MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		log.error("Erro de validação: {}", ex.getMessage());

		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Validation Error",
				"Erro de validação nos campos",
				request.getRequestURI());

		// Adicionar erros de campo
		List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			fieldErrors.add(new ErrorResponse.FieldError(
					fieldError.getField(),
					fieldError.getDefaultMessage(),
					fieldError.getRejectedValue()));
		}
		error.setFieldErrors(fieldErrors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Trata exceções genéricas não tratadas
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(
			Exception ex,
			HttpServletRequest request) {

		log.error("Erro interno: ", ex);

		ErrorResponse error = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error",
				"Ocorreu um erro interno no servidor",
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
