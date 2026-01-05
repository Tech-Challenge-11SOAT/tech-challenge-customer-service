package br.com.postech.techchallange_customer.domain.entity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClienteTest {

	private Cliente cliente;

	@BeforeEach
	void setUp() {
		cliente = new Cliente();
	}

	@Test
	@DisplayName("Deve criar cliente com construtor vazio")
	void deveCriarClienteComConstrutorVazio() {
		assertNotNull(cliente);
		assertNull(cliente.getId());
		assertNull(cliente.getClienteId());
		assertNull(cliente.getNomeCliente());
		assertNull(cliente.getEmailCliente());
		assertNull(cliente.getCpfCliente());
		assertNull(cliente.getTelefone());
		assertNull(cliente.getEndereco());
		assertTrue(cliente.getAtivo());
		assertNotNull(cliente.getDataCadastro());
		assertNotNull(cliente.getDataUltimaAtualizacao());
		assertNull(cliente.getVersao());
		assertNull(cliente.getMetadata());
	}

	@Test
	@DisplayName("Deve criar cliente com construtor parametrizado")
	void deveCriarClienteComConstrutorParametrizado() {
		Cliente clienteCompleto = new Cliente("João Silva", "joao@email.com", "12345678901");

		assertNotNull(clienteCompleto);
		assertEquals("João Silva", clienteCompleto.getNomeCliente());
		assertEquals("joao@email.com", clienteCompleto.getEmailCliente());
		assertEquals("12345678901", clienteCompleto.getCpfCliente());
		assertNotNull(clienteCompleto.getClienteId());
		assertTrue(clienteCompleto.getAtivo());
		assertNotNull(clienteCompleto.getDataCadastro());
		assertNotNull(clienteCompleto.getDataUltimaAtualizacao());
	}

	@Test
	@DisplayName("Deve gerar clienteId automaticamente no construtor parametrizado")
	void deveGerarClienteIdAutomaticamenteNoConstrutorParametrizado() {
		Cliente clienteCompleto = new Cliente("João Silva", "joao@email.com", "12345678901");

		assertNotNull(clienteCompleto.getClienteId());
		assertFalse(clienteCompleto.getClienteId().isEmpty());
	}

	@Test
	@DisplayName("Deve gerar clienteId quando não existe")
	void deveGerarClienteIdQuandoNaoExiste() {
		cliente.setClienteId(null);
		cliente.generateClienteIdIfNeeded();

		assertNotNull(cliente.getClienteId());
		assertFalse(cliente.getClienteId().isEmpty());
	}

	@Test
	@DisplayName("Deve gerar clienteId quando está vazio")
	void deveGerarClienteIdQuandoEstaVazio() {
		cliente.setClienteId("");
		cliente.generateClienteIdIfNeeded();

		assertNotNull(cliente.getClienteId());
		assertFalse(cliente.getClienteId().isEmpty());
	}

	@Test
	@DisplayName("Não deve gerar novo clienteId quando já existe")
	void naoDeveGerarNovoClienteIdQuandoJaExiste() {
		String clienteIdExistente = "cliente-123";
		cliente.setClienteId(clienteIdExistente);
		cliente.generateClienteIdIfNeeded();

		assertEquals(clienteIdExistente, cliente.getClienteId());
	}

	@Test
	@DisplayName("Deve atualizar timestamp")
	void deveAtualizarTimestamp() {
		LocalDateTime dataAnterior = cliente.getDataUltimaAtualizacao();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		cliente.updateTimestamp();

		assertNotNull(cliente.getDataUltimaAtualizacao());
		assertTrue(cliente.getDataUltimaAtualizacao().isAfter(dataAnterior) ||
				cliente.getDataUltimaAtualizacao().isEqual(dataAnterior));
	}

	@Test
	@DisplayName("Deve desativar cliente")
	void deveDesativarCliente() {
		cliente.setAtivo(true);
		cliente.desativar();

		assertFalse(cliente.getAtivo());
		assertNotNull(cliente.getMetadata());
		assertNotNull(cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve desativar cliente e criar metadata se não existir")
	void deveDesativarClienteECriarMetadataSeNaoExistir() {
		cliente.setMetadata(null);
		cliente.desativar();

		assertFalse(cliente.getAtivo());
		assertNotNull(cliente.getMetadata());
		assertNotNull(cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve desativar cliente e manter metadata existente")
	void deveDesativarClienteEManterMetadataExistente() {
		Metadata metadataExistente = new Metadata("Web", "Site");
		metadataExistente.adicionarTag("VIP");
		cliente.setMetadata(metadataExistente);

		cliente.desativar();

		assertFalse(cliente.getAtivo());
		assertEquals("Web", cliente.getMetadata().getOrigem());
		assertEquals("Site", cliente.getMetadata().getCanal());
		assertTrue(cliente.getMetadata().hasTag("VIP"));
		assertNotNull(cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve reativar cliente")
	void deveReativarCliente() {
		cliente.setAtivo(false);
		Metadata metadata = new Metadata();
		metadata.setDataDesativacao(LocalDateTime.now());
		cliente.setMetadata(metadata);

		cliente.reativar();

		assertTrue(cliente.getAtivo());
		assertNull(cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve reativar cliente sem metadata")
	void deveReativarClienteSemMetadata() {
		cliente.setAtivo(false);
		cliente.setMetadata(null);

		cliente.reativar();

		assertTrue(cliente.getAtivo());
	}

	@Test
	@DisplayName("Deve verificar se cliente está ativo")
	void deveVerificarSeClienteEstaAtivo() {
		cliente.setAtivo(true);
		assertTrue(cliente.isAtivo());

		cliente.setAtivo(false);
		assertFalse(cliente.isAtivo());
	}

	@Test
	@DisplayName("Deve verificar que cliente não está ativo quando ativo é nulo")
	void deveVerificarQueClienteNaoEstaAtivoQuandoAtivoNulo() {
		cliente.setAtivo(null);
		assertFalse(cliente.isAtivo());
	}

	@Test
	@DisplayName("Deve validar CPF válido com 11 dígitos")
	void deveValidarCpfValidoCom11Digitos() {
		cliente.setCpfCliente("12345678901");
		assertTrue(cliente.isCpfValido());
	}

	@Test
	@DisplayName("Deve validar CPF inválido com menos de 11 dígitos")
	void deveValidarCpfInvalidoComMenos11Digitos() {
		cliente.setCpfCliente("1234567890");
		assertFalse(cliente.isCpfValido());
	}

	@Test
	@DisplayName("Deve validar CPF inválido com mais de 11 dígitos")
	void deveValidarCpfInvalidoComMais11Digitos() {
		cliente.setCpfCliente("123456789012");
		assertFalse(cliente.isCpfValido());
	}

	@Test
	@DisplayName("Deve validar CPF inválido com caracteres não numéricos")
	void deveValidarCpfInvalidoComCaracteresNaoNumericos() {
		cliente.setCpfCliente("123.456.789-01");
		assertFalse(cliente.isCpfValido());
	}

	@Test
	@DisplayName("Deve validar CPF inválido quando nulo")
	void deveValidarCpfInvalidoQuandoNulo() {
		cliente.setCpfCliente(null);
		assertFalse(cliente.isCpfValido());
	}

	@Test
	@DisplayName("Deve validar email válido")
	void deveValidarEmailValido() {
		cliente.setEmailCliente("joao@email.com");
		assertTrue(cliente.isEmailValido());
	}

	@Test
	@DisplayName("Deve validar email válido com subdomínio")
	void deveValidarEmailValidoComSubdominio() {
		cliente.setEmailCliente("joao@teste.email.com");
		assertTrue(cliente.isEmailValido());
	}

	@Test
	@DisplayName("Deve validar email válido com caracteres especiais")
	void deveValidarEmailValidoComCaracteresEspeciais() {
		cliente.setEmailCliente("joao.silva+tag@email.com");
		assertTrue(cliente.isEmailValido());
	}

	@Test
	@DisplayName("Deve validar email inválido sem arroba")
	void deveValidarEmailInvalidoSemArroba() {
		cliente.setEmailCliente("joaoemail.com");
		assertFalse(cliente.isEmailValido());
	}

	@Test
	@DisplayName("Deve validar email inválido sem domínio")
	void deveValidarEmailInvalidoSemDominio() {
		cliente.setEmailCliente("joao@");
		assertFalse(cliente.isEmailValido());
	}

	@Test
	@DisplayName("Deve validar email inválido quando nulo")
	void deveValidarEmailInvalidoQuandoNulo() {
		cliente.setEmailCliente(null);
		assertFalse(cliente.isEmailValido());
	}

	@Test
	@DisplayName("Deve validar cliente válido com todos os dados obrigatórios")
	void deveValidarClienteValidoComTodosDadosObrigatorios() {
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@email.com");
		cliente.setCpfCliente("12345678901");

		assertTrue(cliente.isValido());
	}

	@Test
	@DisplayName("Deve validar cliente inválido quando nome está vazio")
	void deveValidarClienteInvalidoQuandoNomeVazio() {
		cliente.setNomeCliente("");
		cliente.setEmailCliente("joao@email.com");
		cliente.setCpfCliente("12345678901");

		assertFalse(cliente.isValido());
	}

	@Test
	@DisplayName("Deve validar cliente inválido quando nome é nulo")
	void deveValidarClienteInvalidoQuandoNomeNulo() {
		cliente.setNomeCliente(null);
		cliente.setEmailCliente("joao@email.com");
		cliente.setCpfCliente("12345678901");

		assertFalse(cliente.isValido());
	}

	@Test
	@DisplayName("Deve validar cliente inválido quando email está vazio")
	void deveValidarClienteInvalidoQuandoEmailVazio() {
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("");
		cliente.setCpfCliente("12345678901");

		assertFalse(cliente.isValido());
	}

	@Test
	@DisplayName("Deve validar cliente inválido quando email é nulo")
	void deveValidarClienteInvalidoQuandoEmailNulo() {
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente(null);
		cliente.setCpfCliente("12345678901");

		assertFalse(cliente.isValido());
	}

	@Test
	@DisplayName("Deve validar cliente inválido quando CPF está vazio")
	void deveValidarClienteInvalidoQuandoCpfVazio() {
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@email.com");
		cliente.setCpfCliente("");

		assertFalse(cliente.isValido());
	}

	@Test
	@DisplayName("Deve validar cliente inválido quando CPF é nulo")
	void deveValidarClienteInvalidoQuandoCpfNulo() {
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@email.com");
		cliente.setCpfCliente(null);

		assertFalse(cliente.isValido());
	}

	@Test
	@DisplayName("Deve validar cliente inválido quando CPF tem formato inválido")
	void deveValidarClienteInvalidoQuandoCpfTemFormatoInvalido() {
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@email.com");
		cliente.setCpfCliente("123456789");

		assertFalse(cliente.isValido());
	}

	@Test
	@DisplayName("Deve validar cliente inválido quando email tem formato inválido")
	void deveValidarClienteInvalidoQuandoEmailTemFormatoInvalido() {
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@");
		cliente.setCpfCliente("12345678901");

		assertFalse(cliente.isValido());
	}

	@Test
	@DisplayName("Deve adicionar tag ao cliente")
	void deveAdicionarTagAoCliente() {
		cliente.adicionarTag("VIP");

		assertNotNull(cliente.getMetadata());
		assertTrue(cliente.hasTag("VIP"));
	}

	@Test
	@DisplayName("Deve adicionar tag criando metadata se não existir")
	void deveAdicionarTagCriandoMetadataSeNaoExistir() {
		cliente.setMetadata(null);
		cliente.adicionarTag("VIP");

		assertNotNull(cliente.getMetadata());
		assertTrue(cliente.hasTag("VIP"));
	}

	@Test
	@DisplayName("Deve adicionar múltiplas tags")
	void deveAdicionarMultiplasTags() {
		cliente.adicionarTag("VIP");
		cliente.adicionarTag("Premium");
		cliente.adicionarTag("Especial");

		assertTrue(cliente.hasTag("VIP"));
		assertTrue(cliente.hasTag("Premium"));
		assertTrue(cliente.hasTag("Especial"));
	}

	@Test
	@DisplayName("Deve remover tag do cliente")
	void deveRemoverTagDoCliente() {
		cliente.adicionarTag("VIP");
		cliente.adicionarTag("Premium");

		cliente.removerTag("VIP");

		assertFalse(cliente.hasTag("VIP"));
		assertTrue(cliente.hasTag("Premium"));
	}

	@Test
	@DisplayName("Não deve lançar exceção ao remover tag quando metadata é nulo")
	void naoDeveLancarExcecaoAoRemoverTagQuandoMetadataNulo() {
		cliente.setMetadata(null);

		assertDoesNotThrow(() -> cliente.removerTag("VIP"));
	}

	@Test
	@DisplayName("Deve verificar se cliente tem tag específica")
	void deveVerificarSeClienteTemTagEspecifica() {
		cliente.adicionarTag("VIP");

		assertTrue(cliente.hasTag("VIP"));
		assertFalse(cliente.hasTag("Premium"));
	}

	@Test
	@DisplayName("Deve retornar falso ao verificar tag quando metadata é nulo")
	void deveRetornarFalsoAoVerificarTagQuandoMetadataNulo() {
		cliente.setMetadata(null);

		assertFalse(cliente.hasTag("VIP"));
	}

	@Test
	@DisplayName("Deve setar e obter id corretamente")
	void deveSetarEObterId() {
		cliente.setId("123");
		assertEquals("123", cliente.getId());
	}

	@Test
	@DisplayName("Deve setar e obter clienteId corretamente")
	void deveSetarEObterClienteId() {
		cliente.setClienteId("cliente-456");
		assertEquals("cliente-456", cliente.getClienteId());
	}

	@Test
	@DisplayName("Deve setar e obter nome cliente corretamente")
	void deveSetarEObterNomeCliente() {
		cliente.setNomeCliente("Maria Santos");
		assertEquals("Maria Santos", cliente.getNomeCliente());
	}

	@Test
	@DisplayName("Deve setar e obter email cliente corretamente")
	void deveSetarEObterEmailCliente() {
		cliente.setEmailCliente("maria@email.com");
		assertEquals("maria@email.com", cliente.getEmailCliente());
	}

	@Test
	@DisplayName("Deve setar e obter CPF cliente corretamente")
	void deveSetarEObterCpfCliente() {
		cliente.setCpfCliente("98765432109");
		assertEquals("98765432109", cliente.getCpfCliente());
	}

	@Test
	@DisplayName("Deve setar e obter telefone corretamente")
	void deveSetarEObterTelefone() {
		cliente.setTelefone("11987654321");
		assertEquals("11987654321", cliente.getTelefone());
	}

	@Test
	@DisplayName("Deve setar e obter endereço corretamente")
	void deveSetarEObterEndereco() {
		Endereco endereco = new Endereco("Rua das Flores", "123", "São Paulo", "SP", "01234567");
		cliente.setEndereco(endereco);

		assertEquals(endereco, cliente.getEndereco());
		assertEquals("Rua das Flores", cliente.getEndereco().getRua());
	}

	@Test
	@DisplayName("Deve setar e obter ativo corretamente")
	void deveSetarEObterAtivo() {
		cliente.setAtivo(false);
		assertFalse(cliente.getAtivo());

		cliente.setAtivo(true);
		assertTrue(cliente.getAtivo());
	}

	@Test
	@DisplayName("Deve setar e obter data cadastro corretamente")
	void deveSetarEObterDataCadastro() {
		LocalDateTime dataCadastro = LocalDateTime.of(2026, 1, 1, 10, 0);
		cliente.setDataCadastro(dataCadastro);

		assertEquals(dataCadastro, cliente.getDataCadastro());
	}

	@Test
	@DisplayName("Deve setar e obter data última atualização corretamente")
	void deveSetarEObterDataUltimaAtualizacao() {
		LocalDateTime dataAtualizacao = LocalDateTime.of(2026, 1, 5, 15, 30);
		cliente.setDataUltimaAtualizacao(dataAtualizacao);

		assertEquals(dataAtualizacao, cliente.getDataUltimaAtualizacao());
	}

	@Test
	@DisplayName("Deve setar e obter versão corretamente")
	void deveSetarEObterVersao() {
		cliente.setVersao(1);
		assertEquals(1, cliente.getVersao());

		cliente.setVersao(5);
		assertEquals(5, cliente.getVersao());
	}

	@Test
	@DisplayName("Deve setar e obter metadata corretamente")
	void deveSetarEObterMetadata() {
		Metadata metadata = new Metadata("Web", "Site");
		cliente.setMetadata(metadata);

		assertEquals(metadata, cliente.getMetadata());
		assertEquals("Web", cliente.getMetadata().getOrigem());
		assertEquals("Site", cliente.getMetadata().getCanal());
	}

	@Test
	@DisplayName("Deve manter clienteId quando já existe e gerar se necessário")
	void deveManterClienteIdQuandoJaExisteEGerarSeNecessario() {
		Cliente cliente1 = new Cliente();
		cliente1.setClienteId("id-existente");
		cliente1.generateClienteIdIfNeeded();
		assertEquals("id-existente", cliente1.getClienteId());

		Cliente cliente2 = new Cliente();
		cliente2.generateClienteIdIfNeeded();
		assertNotNull(cliente2.getClienteId());
		assertFalse(cliente2.getClienteId().isEmpty());
	}

	@Test
	@DisplayName("Deve desativar e reativar cliente mantendo consistência")
	void deveDesativarEReativarClienteMantendoConsistencia() {
		cliente.setAtivo(true);

		cliente.desativar();
		assertFalse(cliente.getAtivo());
		assertTrue(cliente.getMetadata().isDesativado());

		cliente.reativar();
		assertTrue(cliente.getAtivo());
		assertFalse(cliente.getMetadata().isDesativado());
	}

	@Test
	@DisplayName("Deve validar todos os cenários de CPF")
	void deveValidarTodosCenariosDeCpf() {
		// CPF válido
		cliente.setCpfCliente("12345678901");
		assertTrue(cliente.isCpfValido());

		// CPF com menos dígitos
		cliente.setCpfCliente("123456789");
		assertFalse(cliente.isCpfValido());

		// CPF com mais dígitos
		cliente.setCpfCliente("123456789012");
		assertFalse(cliente.isCpfValido());

		// CPF com formatação
		cliente.setCpfCliente("123.456.789-01");
		assertFalse(cliente.isCpfValido());

		// CPF nulo
		cliente.setCpfCliente(null);
		assertFalse(cliente.isCpfValido());
	}

	@Test
	@DisplayName("Deve validar todos os cenários de email")
	void deveValidarTodosCenariosDeEmail() {
		// Email válido simples
		cliente.setEmailCliente("teste@email.com");
		assertTrue(cliente.isEmailValido());

		// Email com subdomínio
		cliente.setEmailCliente("teste@sub.email.com");
		assertTrue(cliente.isEmailValido());

		// Email com caracteres especiais
		cliente.setEmailCliente("teste.nome+tag@email.com");
		assertTrue(cliente.isEmailValido());

		// Email sem @
		cliente.setEmailCliente("testeemail.com");
		assertFalse(cliente.isEmailValido());

		// Email sem domínio
		cliente.setEmailCliente("teste@");
		assertFalse(cliente.isEmailValido());

		// Email nulo
		cliente.setEmailCliente(null);
		assertFalse(cliente.isEmailValido());
	}
}
