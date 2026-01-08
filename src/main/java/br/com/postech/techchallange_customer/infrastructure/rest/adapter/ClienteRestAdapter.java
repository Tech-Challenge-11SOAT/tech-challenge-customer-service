package br.com.postech.techchallange_customer.infrastructure.rest.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallange_customer.application.dto.ClienteDTO;
import br.com.postech.techchallange_customer.application.mapper.ClienteMapper;
import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.port.in.AtualizarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.BuscarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.CriarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.DeletarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.DesativarClienteUseCase;
import br.com.postech.techchallange_customer.domain.port.in.ListarClientesUseCase;
import br.com.postech.techchallange_customer.domain.port.in.ReativarClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Adaptador REST (Controller)
 * Expõe as APIs REST e delega para os casos de uso
 */
@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteRestAdapter {

	private static final Logger log = LoggerFactory.getLogger(ClienteRestAdapter.class);

	private final CriarClienteUseCase criarClienteUseCase;
	private final BuscarClienteUseCase buscarClienteUseCase;
	private final ListarClientesUseCase listarClientesUseCase;
	private final AtualizarClienteUseCase atualizarClienteUseCase;
	private final DesativarClienteUseCase desativarClienteUseCase;
	private final ReativarClienteUseCase reativarClienteUseCase;
	private final DeletarClienteUseCase deletarClienteUseCase;

	public ClienteRestAdapter(
			CriarClienteUseCase criarClienteUseCase,
			BuscarClienteUseCase buscarClienteUseCase,
			ListarClientesUseCase listarClientesUseCase,
			AtualizarClienteUseCase atualizarClienteUseCase,
			DesativarClienteUseCase desativarClienteUseCase,
			ReativarClienteUseCase reativarClienteUseCase,
			DeletarClienteUseCase deletarClienteUseCase) {
		this.criarClienteUseCase = criarClienteUseCase;
		this.buscarClienteUseCase = buscarClienteUseCase;
		this.listarClientesUseCase = listarClientesUseCase;
		this.atualizarClienteUseCase = atualizarClienteUseCase;
		this.desativarClienteUseCase = desativarClienteUseCase;
		this.reativarClienteUseCase = reativarClienteUseCase;
		this.deletarClienteUseCase = deletarClienteUseCase;
	}

	@Operation(summary = "Criar novo cliente", description = "Cria um novo cliente no sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos"),
			@ApiResponse(responseCode = "409", description = "CPF ou e-mail já cadastrado")
	})
	@PostMapping
	public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteDTO clienteDTO) {
		log.info("POST /api/v1/clientes - Criar novo cliente");

		Cliente cliente = ClienteMapper.toDomain(clienteDTO);
		Cliente created = criarClienteUseCase.execute(cliente);
		ClienteDTO response = ClienteMapper.toDTO(created);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente pelo clienteId (UUID)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente encontrado"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	@GetMapping("/{clienteId}")
	public ResponseEntity<ClienteDTO> buscarPorId(
			@Parameter(description = "UUID do cliente") @PathVariable String clienteId) {
		log.info("GET /api/v1/clientes/{} - Buscar por ID", clienteId);

		return buscarClienteUseCase.porClienteId(clienteId)
				.map(ClienteMapper::toDTO)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Buscar cliente por CPF", description = "Retorna os dados de um cliente pelo CPF")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente encontrado"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<ClienteDTO> buscarPorCpf(
			@Parameter(description = "CPF do cliente (11 dígitos)") @PathVariable String cpf) {
		log.info("GET /api/v1/clientes/cpf/{} - Buscar por CPF", cpf);

		return buscarClienteUseCase.porCpf(cpf)
				.map(ClienteMapper::toDTO)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Buscar cliente por e-mail", description = "Retorna os dados de um cliente pelo e-mail")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente encontrado"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	@GetMapping("/email/{email}")
	public ResponseEntity<ClienteDTO> buscarPorEmail(
			@Parameter(description = "E-mail do cliente") @PathVariable String email) {
		log.info("GET /api/v1/clientes/email/{} - Buscar por e-mail", email);

		return buscarClienteUseCase.porEmail(email)
				.map(ClienteMapper::toDTO)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Listar clientes ativos", description = "Retorna lista de todos os clientes ativos")
	@GetMapping("/ativos")
	public ResponseEntity<List<ClienteDTO>> listarAtivos() {
		log.info("GET /api/v1/clientes/ativos - Listar clientes ativos");

		List<ClienteDTO> clientes = listarClientesUseCase.ativos().stream()
				.map(ClienteMapper::toDTO)
				.collect(Collectors.toList());

		return ResponseEntity.ok(clientes);
	}

	@Operation(summary = "Listar todos os clientes", description = "Retorna lista de todos os clientes")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listarTodos() {
		log.info("GET /api/v1/clientes - Listar todos");

		List<ClienteDTO> clientes = listarClientesUseCase.todos().stream()
				.map(ClienteMapper::toDTO)
				.collect(Collectors.toList());

		return ResponseEntity.ok(clientes);
	}

	@Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos")
	})
	@PutMapping("/{clienteId}")
	public ResponseEntity<ClienteDTO> atualizar(
			@Parameter(description = "UUID do cliente") @PathVariable String clienteId,
			@Valid @RequestBody ClienteDTO clienteDTO) {
		log.info("PUT /api/v1/clientes/{} - Atualizar", clienteId);

		Cliente cliente = ClienteMapper.toDomain(clienteDTO);
		Cliente updated = atualizarClienteUseCase.execute(clienteId, cliente);
		ClienteDTO response = ClienteMapper.toDTO(updated);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Desativar cliente", description = "Desativa um cliente (soft delete)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cliente desativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	@PatchMapping("/{clienteId}/desativar")
	public ResponseEntity<Void> desativar(@PathVariable String clienteId) {
		log.info("PATCH /api/v1/clientes/{}/desativar", clienteId);

		desativarClienteUseCase.execute(clienteId);

		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Reativar cliente", description = "Reativa um cliente desativado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cliente reativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	@PatchMapping("/{clienteId}/reativar")
	public ResponseEntity<Void> reativar(@PathVariable String clienteId) {
		log.info("PATCH /api/v1/clientes/{}/reativar", clienteId);

		reativarClienteUseCase.execute(clienteId);

		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Deletar cliente permanentemente", description = "Remove permanentemente um cliente do banco (CUIDADO!)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cliente deletado permanentemente"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deletar(@PathVariable String clienteId) {
		log.warn("DELETE /api/v1/clientes/{} - DELETANDO PERMANENTEMENTE", clienteId);

		deletarClienteUseCase.execute(clienteId);

		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Buscar clientes por cidade", description = "Lista clientes ativos de uma cidade")
	@GetMapping("/cidade/{cidade}")
	public ResponseEntity<List<ClienteDTO>> buscarPorCidade(@PathVariable String cidade) {
		log.info("GET /api/v1/clientes/cidade/{}", cidade);

		List<ClienteDTO> clientes = listarClientesUseCase.porCidade(cidade).stream()
				.map(ClienteMapper::toDTO)
				.collect(Collectors.toList());

		return ResponseEntity.ok(clientes);
	}

	@Operation(summary = "Buscar clientes VIP", description = "Lista todos os clientes VIP ativos")
	@GetMapping("/vip")
	public ResponseEntity<List<ClienteDTO>> buscarClientesVip() {
		log.info("GET /api/v1/clientes/vip");

		List<ClienteDTO> clientes = listarClientesUseCase.vipAtivos().stream()
				.map(ClienteMapper::toDTO)
				.collect(Collectors.toList());

		return ResponseEntity.ok(clientes);
	}

	@Operation(summary = "Contar clientes ativos", description = "Retorna o total de clientes ativos")
	@GetMapping("/count/ativos")
	public ResponseEntity<Long> contarAtivos() {
		log.info("GET /api/v1/clientes/count/ativos");

		return ResponseEntity.ok(listarClientesUseCase.contarAtivos());
	}
}
