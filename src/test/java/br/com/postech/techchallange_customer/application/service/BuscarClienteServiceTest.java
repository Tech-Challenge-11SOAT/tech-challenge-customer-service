package br.com.postech.techchallange_customer.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("BuscarClienteService Tests")
class BuscarClienteServiceTest {

	@Mock
	private ClienteRepositoryPort clienteRepository;

	private BuscarClienteService service;

	@BeforeEach
	void setUp() {
		service = new BuscarClienteService(clienteRepository);
	}

	@Test
	@DisplayName("Deve buscar cliente por clienteId com sucesso")
	void deveBuscarClientePorClienteIdComSucesso() {
		String clienteId = "cliente-123";

		Cliente cliente = new Cliente();
		cliente.setId("id-123");
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = service.porClienteId(clienteId);

		assertTrue(resultado.isPresent());
		assertEquals(clienteId, resultado.get().getClienteId());
		assertEquals("João Silva", resultado.get().getNomeCliente());
		assertEquals("joao@example.com", resultado.get().getEmailCliente());
		assertEquals("12345678901", resultado.get().getCpfCliente());

		verify(clienteRepository).findByClienteId(clienteId);
		verify(clienteRepository, never()).findByCpf(anyString());
		verify(clienteRepository, never()).findByEmail(anyString());
	}

	@Test
	@DisplayName("Deve retornar Optional vazio quando cliente não existe por clienteId")
	void deveRetornarOptionalVazioQuandoClienteNaoExistePorClienteId() {
		String clienteId = "cliente-inexistente";

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porClienteId(clienteId);

		assertFalse(resultado.isPresent());
		assertTrue(resultado.isEmpty());

		verify(clienteRepository).findByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve buscar cliente por CPF com sucesso")
	void deveBuscarClientePorCpfComSucesso() {
		String cpf = "12345678901";

		Cliente cliente = new Cliente();
		cliente.setId("id-456");
		cliente.setClienteId("cliente-456");
		cliente.setNomeCliente("Maria Santos");
		cliente.setEmailCliente("maria@example.com");
		cliente.setCpfCliente(cpf);

		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = service.porCpf(cpf);

		assertTrue(resultado.isPresent());
		assertEquals(cpf, resultado.get().getCpfCliente());
		assertEquals("Maria Santos", resultado.get().getNomeCliente());
		assertEquals("maria@example.com", resultado.get().getEmailCliente());

		verify(clienteRepository).findByCpf(cpf);
		verify(clienteRepository, never()).findByClienteId(anyString());
		verify(clienteRepository, never()).findByEmail(anyString());
	}

	@Test
	@DisplayName("Deve retornar Optional vazio quando cliente não existe por CPF")
	void deveRetornarOptionalVazioQuandoClienteNaoExistePorCpf() {
		String cpf = "99999999999";

		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porCpf(cpf);

		assertFalse(resultado.isPresent());
		assertTrue(resultado.isEmpty());

		verify(clienteRepository).findByCpf(cpf);
	}

	@Test
	@DisplayName("Deve buscar cliente por email com sucesso")
	void deveBuscarClientePorEmailComSucesso() {
		String email = "pedro@example.com";

		Cliente cliente = new Cliente();
		cliente.setId("id-789");
		cliente.setClienteId("cliente-789");
		cliente.setNomeCliente("Pedro Oliveira");
		cliente.setEmailCliente(email);
		cliente.setCpfCliente("98765432109");

		when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = service.porEmail(email);

		assertTrue(resultado.isPresent());
		assertEquals(email, resultado.get().getEmailCliente());
		assertEquals("Pedro Oliveira", resultado.get().getNomeCliente());
		assertEquals("98765432109", resultado.get().getCpfCliente());

		verify(clienteRepository).findByEmail(email);
		verify(clienteRepository, never()).findByClienteId(anyString());
		verify(clienteRepository, never()).findByCpf(anyString());
	}

	@Test
	@DisplayName("Deve retornar Optional vazio quando cliente não existe por email")
	void deveRetornarOptionalVazioQuandoClienteNaoExistePorEmail() {
		String email = "inexistente@example.com";

		when(clienteRepository.findByEmail(email)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porEmail(email);

		assertFalse(resultado.isPresent());
		assertTrue(resultado.isEmpty());

		verify(clienteRepository).findByEmail(email);
	}

	@Test
	@DisplayName("Deve buscar cliente com todos os dados completos por clienteId")
	void deveBuscarClienteComTodosDadosCompletosPorClienteId() {
		String clienteId = "cliente-completo";
		LocalDateTime agora = LocalDateTime.now();

		Endereco endereco = new Endereco();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setComplemento("Apto 45");
		endereco.setBairro("Centro");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("12345678");

		Metadata metadata = new Metadata();
		metadata.setOrigem("Web");
		metadata.setCanal("Online");

		Cliente cliente = new Cliente();
		cliente.setId("id-completo");
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("Cliente Completo");
		cliente.setEmailCliente("completo@example.com");
		cliente.setCpfCliente("11122233344");
		cliente.setTelefone("11987654321");
		cliente.setEndereco(endereco);
		cliente.setMetadata(metadata);
		cliente.setAtivo(true);
		cliente.setDataCadastro(agora);
		cliente.setDataUltimaAtualizacao(agora);
		cliente.setVersao(1);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = service.porClienteId(clienteId);

		assertTrue(resultado.isPresent());
		Cliente clienteEncontrado = resultado.get();

		assertEquals(clienteId, clienteEncontrado.getClienteId());
		assertEquals("Cliente Completo", clienteEncontrado.getNomeCliente());
		assertEquals("completo@example.com", clienteEncontrado.getEmailCliente());
		assertEquals("11122233344", clienteEncontrado.getCpfCliente());
		assertEquals("11987654321", clienteEncontrado.getTelefone());
		assertTrue(clienteEncontrado.getAtivo());
		assertEquals(1, clienteEncontrado.getVersao());

		assertNotNull(clienteEncontrado.getEndereco());
		assertEquals("Rua das Flores", clienteEncontrado.getEndereco().getRua());
		assertEquals("SP", clienteEncontrado.getEndereco().getEstado());

		assertNotNull(clienteEncontrado.getMetadata());
		assertEquals("Web", clienteEncontrado.getMetadata().getOrigem());
		assertEquals("Online", clienteEncontrado.getMetadata().getCanal());
	}

	@Test
	@DisplayName("Deve buscar cliente com dados mínimos por clienteId")
	void deveBuscarClienteComDadosMinimosPorClienteId() {
		String clienteId = "cliente-minimo";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("Cliente Mínimo");
		cliente.setEmailCliente("minimo@example.com");
		cliente.setCpfCliente("55566677788");

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = service.porClienteId(clienteId);

		assertTrue(resultado.isPresent());
		Cliente clienteEncontrado = resultado.get();

		assertEquals(clienteId, clienteEncontrado.getClienteId());
		assertEquals("Cliente Mínimo", clienteEncontrado.getNomeCliente());
		assertEquals("minimo@example.com", clienteEncontrado.getEmailCliente());
		assertEquals("55566677788", clienteEncontrado.getCpfCliente());
		assertNull(clienteEncontrado.getTelefone());
		assertNull(clienteEncontrado.getEndereco());
		assertNull(clienteEncontrado.getMetadata());
	}

	@Test
	@DisplayName("Deve buscar cliente inativo por clienteId")
	void deveBuscarClienteInativoPorClienteId() {
		String clienteId = "cliente-inativo";

		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);
		cliente.setNomeCliente("Cliente Inativo");
		cliente.setEmailCliente("inativo@example.com");
		cliente.setCpfCliente("99988877766");
		cliente.setAtivo(false);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = service.porClienteId(clienteId);

		assertTrue(resultado.isPresent());
		assertFalse(resultado.get().getAtivo());
	}

	@Test
	@DisplayName("Deve aceitar clienteId null ao buscar")
	void deveAceitarClienteIdNullAoBuscar() {
		when(clienteRepository.findByClienteId(null)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porClienteId(null);

		assertFalse(resultado.isPresent());
		verify(clienteRepository).findByClienteId(null);
	}

	@Test
	@DisplayName("Deve aceitar CPF null ao buscar")
	void deveAceitarCpfNullAoBuscar() {
		when(clienteRepository.findByCpf(null)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porCpf(null);

		assertFalse(resultado.isPresent());
		verify(clienteRepository).findByCpf(null);
	}

	@Test
	@DisplayName("Deve aceitar email null ao buscar")
	void deveAceitarEmailNullAoBuscar() {
		when(clienteRepository.findByEmail(null)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porEmail(null);

		assertFalse(resultado.isPresent());
		verify(clienteRepository).findByEmail(null);
	}

	@Test
	@DisplayName("Deve aceitar clienteId vazio ao buscar")
	void deveAceitarClienteIdVazioAoBuscar() {
		String clienteId = "";
		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porClienteId(clienteId);

		assertFalse(resultado.isPresent());
		verify(clienteRepository).findByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve aceitar CPF vazio ao buscar")
	void deveAceitarCpfVazioAoBuscar() {
		String cpf = "";
		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porCpf(cpf);

		assertFalse(resultado.isPresent());
		verify(clienteRepository).findByCpf(cpf);
	}

	@Test
	@DisplayName("Deve aceitar email vazio ao buscar")
	void deveAceitarEmailVazioAoBuscar() {
		String email = "";
		when(clienteRepository.findByEmail(email)).thenReturn(Optional.empty());

		Optional<Cliente> resultado = service.porEmail(email);

		assertFalse(resultado.isPresent());
		verify(clienteRepository).findByEmail(email);
	}

	@Test
	@DisplayName("Deve buscar cliente por CPF com formato com pontos e traço")
	void deveBuscarClientePorCpfComFormatoComPontosETraco() {
		String cpfFormatado = "123.456.789-01";

		Cliente cliente = new Cliente();
		cliente.setCpfCliente("12345678901");
		cliente.setNomeCliente("Cliente Teste");
		cliente.setEmailCliente("teste@example.com");

		when(clienteRepository.findByCpf(cpfFormatado)).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = service.porCpf(cpfFormatado);

		assertTrue(resultado.isPresent());
		verify(clienteRepository).findByCpf(cpfFormatado);
	}

	@Test
	@DisplayName("Deve buscar cliente por email com diferentes formatos")
	void deveBuscarClientePorEmailComDiferentesFormatos() {
		String email = "user.name+tag@example.co.uk";

		Cliente cliente = new Cliente();
		cliente.setEmailCliente(email);
		cliente.setNomeCliente("User Name");
		cliente.setCpfCliente("12345678901");

		when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = service.porEmail(email);

		assertTrue(resultado.isPresent());
		assertEquals(email, resultado.get().getEmailCliente());
		verify(clienteRepository).findByEmail(email);
	}

	@Test
	@DisplayName("Deve chamar repositório apenas uma vez para cada método")
	void deveChamarRepositorioApenasUmaVezParaCadaMetodo() {
		String clienteId = "cliente-123";
		String cpf = "12345678901";
		String email = "teste@example.com";

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.empty());
		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());
		when(clienteRepository.findByEmail(email)).thenReturn(Optional.empty());

		service.porClienteId(clienteId);
		service.porCpf(cpf);
		service.porEmail(email);

		verify(clienteRepository, times(1)).findByClienteId(clienteId);
		verify(clienteRepository, times(1)).findByCpf(cpf);
		verify(clienteRepository, times(1)).findByEmail(email);
	}

	@Test
	@DisplayName("Deve retornar o mesmo Optional retornado pelo repositório")
	void deveRetornarOMesmoOptionalRetornadoPeloRepositorio() {
		String clienteId = "cliente-123";
		Cliente cliente = new Cliente();
		cliente.setClienteId(clienteId);

		Optional<Cliente> optionalRepositorio = Optional.of(cliente);
		when(clienteRepository.findByClienteId(clienteId)).thenReturn(optionalRepositorio);

		Optional<Cliente> resultado = service.porClienteId(clienteId);

		assertSame(optionalRepositorio, resultado);
	}

	@Test
	@DisplayName("Deve criar instância do serviço com construtor")
	void deveCriarInstanciaDoServicoComConstrutor() {
		BuscarClienteService novoService = new BuscarClienteService(clienteRepository);
		assertNotNull(novoService);
	}

	@Test
	@DisplayName("Deve delegar busca por clienteId diretamente ao repositório")
	void deveDelegarBuscaPorClienteIdDiretamenteAoRepositorio() {
		String clienteId = "cliente-delegacao";
		service.porClienteId(clienteId);
		verify(clienteRepository).findByClienteId(clienteId);
	}

	@Test
	@DisplayName("Deve delegar busca por CPF diretamente ao repositório")
	void deveDelegarBuscaPorCpfDiretamenteAoRepositorio() {
		String cpf = "11111111111";
		service.porCpf(cpf);
		verify(clienteRepository).findByCpf(cpf);
	}

	@Test
	@DisplayName("Deve delegar busca por email diretamente ao repositório")
	void deveDelegarBuscaPorEmailDiretamenteAoRepositorio() {
		String email = "delegacao@example.com";
		service.porEmail(email);
		verify(clienteRepository).findByEmail(email);
	}

	@Test
	@DisplayName("Deve buscar múltiplos clientes por diferentes critérios")
	void deveBuscarMultiplosClientesPorDiferentesCriterios() {
		String clienteId = "cliente-1";
		String cpf = "11111111111";
		String email = "cliente1@example.com";

		Cliente cliente1 = new Cliente();
		cliente1.setClienteId(clienteId);
		cliente1.setCpfCliente(cpf);
		cliente1.setEmailCliente(email);

		when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(cliente1));
		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(cliente1));
		when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente1));

		Optional<Cliente> porId = service.porClienteId(clienteId);
		Optional<Cliente> porCpf = service.porCpf(cpf);
		Optional<Cliente> porEmail = service.porEmail(email);

		assertTrue(porId.isPresent());
		assertTrue(porCpf.isPresent());
		assertTrue(porEmail.isPresent());

		assertEquals(clienteId, porId.get().getClienteId());
		assertEquals(cpf, porCpf.get().getCpfCliente());
		assertEquals(email, porEmail.get().getEmailCliente());
	}
}
