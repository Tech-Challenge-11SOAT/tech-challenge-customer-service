package br.com.postech.techchallange_customer.application.service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.domain.exception.ClienteAlreadyExistsException;
import br.com.postech.techchallange_customer.domain.exception.InvalidClienteException;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("CriarClienteService Tests")
class CriarClienteServiceTest {

	@Mock
	private ClienteRepositoryPort clienteRepository;

	private CriarClienteService service;

	@BeforeEach
	void setUp() {
		service = new CriarClienteService(clienteRepository);
	}

	@Test
	@DisplayName("Deve criar cliente com sucesso com dados válidos")
	void deveCriarClienteComSucessoComDadosValidos() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertNotNull(resultado);
		assertEquals("João Silva", resultado.getNomeCliente());
		assertEquals("joao@example.com", resultado.getEmailCliente());
		assertEquals("12345678901", resultado.getCpfCliente());
		assertNotNull(resultado.getClienteId());
		assertTrue(resultado.getAtivo());
		assertNotNull(resultado.getDataCadastro());
		assertNotNull(resultado.getDataUltimaAtualizacao());

		verify(clienteRepository).existsByCpf("12345678901");
		verify(clienteRepository).existsByEmail("joao@example.com");
		verify(clienteRepository).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve criar cliente com todos os campos opcionais")
	void deveCriarClienteComTodosOsCamposOpcionais() {
		Endereco endereco = new Endereco();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("12345678");

		Metadata metadata = new Metadata();
		metadata.setOrigem("Web");
		metadata.setCanal("Online");

		Cliente cliente = new Cliente();
		cliente.setNomeCliente("Maria Santos");
		cliente.setEmailCliente("maria@example.com");
		cliente.setCpfCliente("98765432109");
		cliente.setTelefone("11987654321");
		cliente.setEndereco(endereco);
		cliente.setMetadata(metadata);

		when(clienteRepository.existsByCpf("98765432109")).thenReturn(false);
		when(clienteRepository.existsByEmail("maria@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertNotNull(resultado);
		assertEquals("Maria Santos", resultado.getNomeCliente());
		assertEquals("11987654321", resultado.getTelefone());
		assertNotNull(resultado.getEndereco());
		assertEquals("Rua das Flores", resultado.getEndereco().getRua());
		assertNotNull(resultado.getMetadata());
		assertEquals("Web", resultado.getMetadata().getOrigem());
	}

	@Test
	@DisplayName("Deve lançar exceção quando cliente é null")
	void deveLancarExcecaoQuandoClienteEhNull() {
		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(null));

		assertEquals("Cliente não pode ser nulo", exception.getMessage());
		verify(clienteRepository, never()).save(any(Cliente.class));
		verify(clienteRepository, never()).existsByCpf(anyString());
		verify(clienteRepository, never()).existsByEmail(anyString());
	}

	@Test
	@DisplayName("Deve lançar exceção quando dados do cliente são inválidos")
	void deveLancarExcecaoQuandoDadosDoClienteSaoInvalidos() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("");
		cliente.setEmailCliente("invalido");
		cliente.setCpfCliente("123");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertEquals("Dados do cliente inválidos", exception.getMessage());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando nome do cliente é null")
	void deveLancarExcecaoQuandoNomeDoClienteEhNull() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente(null);
		cliente.setEmailCliente("teste@example.com");
		cliente.setCpfCliente("12345678901");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertEquals("Dados do cliente inválidos", exception.getMessage());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando nome do cliente é vazio")
	void deveLancarExcecaoQuandoNomeDoClienteEhVazio() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("");
		cliente.setEmailCliente("teste@example.com");
		cliente.setCpfCliente("12345678901");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertEquals("Dados do cliente inválidos", exception.getMessage());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando email do cliente é null")
	void deveLancarExcecaoQuandoEmailDoClienteEhNull() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente(null);
		cliente.setCpfCliente("12345678901");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertEquals("Dados do cliente inválidos", exception.getMessage());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando email do cliente é vazio")
	void deveLancarExcecaoQuandoEmailDoClienteEhVazio() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("");
		cliente.setCpfCliente("12345678901");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertEquals("Dados do cliente inválidos", exception.getMessage());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando CPF do cliente é null")
	void deveLancarExcecaoQuandoCpfDoClienteEhNull() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente(null);

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertEquals("Dados do cliente inválidos", exception.getMessage());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando CPF do cliente é vazio")
	void deveLancarExcecaoQuandoCpfDoClienteEhVazio() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertEquals("Dados do cliente inválidos", exception.getMessage());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando CPF tem formato inválido")
	void deveLancarExcecaoQuandoCpfTemFormatoInvalido() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("123456789");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertTrue(exception.getMessage().contains("CPF") || exception.getMessage().contains("inválido"));
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando CPF contém caracteres não numéricos")
	void deveLancarExcecaoQuandoCpfContemCaracteresNaoNumericos() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("123.456.789-01");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertTrue(exception.getMessage().contains("CPF") || exception.getMessage().contains("inválido"));
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando email tem formato inválido")
	void deveLancarExcecaoQuandoEmailTemFormatoInvalido() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("email-invalido");
		cliente.setCpfCliente("12345678901");

		InvalidClienteException exception = assertThrows(
				InvalidClienteException.class,
				() -> service.execute(cliente));

		assertTrue(exception.getMessage().contains("E-mail") || exception.getMessage().contains("inválido"));
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando CPF já existe no sistema")
	void deveLancarExcecaoQuandoCpfJaExisteNoSistema() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(true);

		ClienteAlreadyExistsException exception = assertThrows(
				ClienteAlreadyExistsException.class,
				() -> service.execute(cliente));

		assertTrue(exception.getMessage().contains("CPF"));
		assertTrue(exception.getMessage().contains("12345678901"));
		verify(clienteRepository).existsByCpf("12345678901");
		verify(clienteRepository, never()).existsByEmail(anyString());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção quando email já existe no sistema")
	void deveLancarExcecaoQuandoEmailJaExisteNoSistema() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(true);

		ClienteAlreadyExistsException exception = assertThrows(
				ClienteAlreadyExistsException.class,
				() -> service.execute(cliente));

		assertTrue(exception.getMessage().contains("e-mail"));
		assertTrue(exception.getMessage().contains("joao@example.com"));
		verify(clienteRepository).existsByCpf("12345678901");
		verify(clienteRepository).existsByEmail("joao@example.com");
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve gerar clienteId quando não fornecido")
	void deveGerarClienteIdQuandoNaoFornecido() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setClienteId(null);

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertNotNull(resultado.getClienteId());
		assertFalse(resultado.getClienteId().isEmpty());
	}

	@Test
	@DisplayName("Deve preservar clienteId quando já fornecido")
	void devePreservarClienteIdQuandoJaFornecido() {
		String clienteIdExistente = "cliente-predefinido";

		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setClienteId(clienteIdExistente);

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertEquals(clienteIdExistente, resultado.getClienteId());
	}

	@Test
	@DisplayName("Deve definir ativo como true ao criar cliente")
	void deveDefinirAtivoComoTrueAoCriarCliente() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setAtivo(false);

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertTrue(resultado.getAtivo());
	}

	@Test
	@DisplayName("Deve definir dataCadastro ao criar cliente")
	void deveDefinirDataCadastroAoCriarCliente() {
		LocalDateTime antes = LocalDateTime.now().minusSeconds(1);

		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		LocalDateTime depois = LocalDateTime.now().plusSeconds(1);

		assertNotNull(resultado.getDataCadastro());
		assertTrue(resultado.getDataCadastro().isAfter(antes));
		assertTrue(resultado.getDataCadastro().isBefore(depois));
	}

	@Test
	@DisplayName("Deve definir dataUltimaAtualizacao ao criar cliente")
	void deveDefinirDataUltimaAtualizacaoAoCriarCliente() {
		LocalDateTime antes = LocalDateTime.now().minusSeconds(1);

		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		LocalDateTime depois = LocalDateTime.now().plusSeconds(1);

		assertNotNull(resultado.getDataUltimaAtualizacao());
		assertTrue(resultado.getDataUltimaAtualizacao().isAfter(antes));
		assertTrue(resultado.getDataUltimaAtualizacao().isBefore(depois));
	}

	@Test
	@DisplayName("Deve verificar existência de CPF antes de email")
	void deveVerificarExistenciaDeCpfAntesDeEmail() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(true);

		assertThrows(ClienteAlreadyExistsException.class, () -> service.execute(cliente));

		verify(clienteRepository).existsByCpf("12345678901");
		verify(clienteRepository, never()).existsByEmail(anyString());
	}

	@Test
	@DisplayName("Deve passar cliente correto para repositório ao salvar")
	void devePassarClienteCorretoParaRepositorioAoSalvar() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setTelefone("11987654321");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		service.execute(cliente);

		ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
		verify(clienteRepository).save(clienteCaptor.capture());

		Cliente clienteSalvo = clienteCaptor.getValue();
		assertEquals("João Silva", clienteSalvo.getNomeCliente());
		assertEquals("joao@example.com", clienteSalvo.getEmailCliente());
		assertEquals("12345678901", clienteSalvo.getCpfCliente());
		assertEquals("11987654321", clienteSalvo.getTelefone());
		assertTrue(clienteSalvo.getAtivo());
		assertNotNull(clienteSalvo.getClienteId());
		assertNotNull(clienteSalvo.getDataCadastro());
		assertNotNull(clienteSalvo.getDataUltimaAtualizacao());
	}

	@Test
	@DisplayName("Deve aceitar email válido com caracteres especiais")
	void deveAceitarEmailValidoComCaracteresEspeciais() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("user.name+tag@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("user.name+tag@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertNotNull(resultado);
		assertEquals("user.name+tag@example.com", resultado.getEmailCliente());
	}

	@Test
	@DisplayName("Deve criar cliente sem telefone")
	void deveCriarClienteSemTelefone() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setTelefone(null);

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertNotNull(resultado);
		assertNull(resultado.getTelefone());
	}

	@Test
	@DisplayName("Deve criar cliente sem endereco")
	void deveCriarClienteSemEndereco() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setEndereco(null);

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertNotNull(resultado);
		assertNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve criar cliente sem metadata")
	void deveCriarClienteSemMetadata() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setMetadata(null);

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Cliente resultado = service.execute(cliente);

		assertNotNull(resultado);
		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve validar cliente antes de verificar duplicidade")
	void deveValidarClienteAntesDeVerificarDuplicidade() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("");
		cliente.setEmailCliente("invalido");
		cliente.setCpfCliente("123");

		assertThrows(InvalidClienteException.class, () -> service.execute(cliente));

		verify(clienteRepository, never()).existsByCpf(anyString());
		verify(clienteRepository, never()).existsByEmail(anyString());
		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve criar instância do serviço com construtor")
	void deveCriarInstanciaDoServicoComConstrutor() {
		CriarClienteService novoService = new CriarClienteService(clienteRepository);
		assertNotNull(novoService);
	}

	@Test
	@DisplayName("Deve retornar cliente salvo pelo repositório")
	void deveRetornarClienteSalvoPeloRepositorio() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		Cliente clienteSalvo = new Cliente();
		clienteSalvo.setId("id-gerado-123");
		clienteSalvo.setClienteId("cliente-gerado-456");
		clienteSalvo.setNomeCliente("João Silva");
		clienteSalvo.setEmailCliente("joao@example.com");
		clienteSalvo.setCpfCliente("12345678901");

		when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
		when(clienteRepository.existsByEmail("joao@example.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

		Cliente resultado = service.execute(cliente);

		assertSame(clienteSalvo, resultado);
		assertEquals("id-gerado-123", resultado.getId());
	}
}
