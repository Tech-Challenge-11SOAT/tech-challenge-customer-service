package br.com.postech.techchallange_customer.application.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("ClienteDTO Tests")
class ClienteDTOTest {

	private Validator validator;
	private ClienteDTO clienteDTO;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		clienteDTO = new ClienteDTO();
	}

	@Test
	@DisplayName("Deve criar ClienteDTO com construtor padrão")
	void deveCriarClienteDTOComConstrutorPadrao() {
		ClienteDTO dto = new ClienteDTO();
		assertNotNull(dto);
	}

	@Test
	@DisplayName("Deve validar ClienteDTO válido com todos os campos obrigatórios")
	void deveValidarClienteDTOValido() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve falhar quando nome é null")
	void deveFalharQuandoNomeEhNull() {
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("O nome é obrigatório", violations.iterator().next().getMessage());
	}

	@Test
	@DisplayName("Deve falhar quando nome é vazio")
	void deveFalharQuandoNomeEhVazio() {
		clienteDTO.setNomeCliente("");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O nome é obrigatório")));
	}

	@Test
	@DisplayName("Deve falhar quando nome é apenas espaços em branco")
	void deveFalharQuandoNomeEhApenasEspacos() {
		clienteDTO.setNomeCliente("   ");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O nome é obrigatório")));
	}

	@Test
	@DisplayName("Deve falhar quando email é null")
	void deveFalharQuandoEmailEhNull() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("O e-mail é obrigatório", violations.iterator().next().getMessage());
	}

	@Test
	@DisplayName("Deve falhar quando email é vazio")
	void deveFalharQuandoEmailEhVazio() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O e-mail é obrigatório")));
	}

	@Test
	@DisplayName("Deve falhar quando email tem formato inválido")
	void deveFalharQuandoEmailTemFormatoInvalido() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("email-invalido");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Formato de e-mail inválido")));
	}

	@Test
	@DisplayName("Deve aceitar email válido")
	void deveAceitarEmailValido() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao.silva@example.com");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve falhar quando CPF é null")
	void deveFalharQuandoCpfEhNull() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("O CPF é obrigatório", violations.iterator().next().getMessage());
	}

	@Test
	@DisplayName("Deve falhar quando CPF é vazio")
	void deveFalharQuandoCpfEhVazio() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O CPF é obrigatório")));
	}

	@Test
	@DisplayName("Deve falhar quando CPF tem menos de 11 dígitos")
	void deveFalharQuandoCpfTemMenosDeOnzeDigitos() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("123456789");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream()
				.anyMatch(v -> v.getMessage().equals("O CPF deve conter exatamente 11 dígitos numéricos")));
	}

	@Test
	@DisplayName("Deve falhar quando CPF tem mais de 11 dígitos")
	void deveFalharQuandoCpfTemMaisDeOnzeDigitos() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("123456789012");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream()
				.anyMatch(v -> v.getMessage().equals("O CPF deve conter exatamente 11 dígitos numéricos")));
	}

	@Test
	@DisplayName("Deve falhar quando CPF contém caracteres não numéricos")
	void deveFalharQuandoCpfContemCaracteresNaoNumericos() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("123.456.789-01");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream()
				.anyMatch(v -> v.getMessage().equals("O CPF deve conter exatamente 11 dígitos numéricos")));
	}

	@Test
	@DisplayName("Deve aceitar CPF válido com 11 dígitos")
	void deveAceitarCpfValido() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve validar endereço quando presente e válido")
	void deveValidarEnderecoQuandoPresenteEValido() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setBairro("Centro");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("12345678");

		clienteDTO.setEndereco(endereco);

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve falhar quando endereço tem estado inválido")
	void deveFalharQuandoEnderecoTemEstadoInvalido() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setEstado("São Paulo");
		endereco.setCep("12345678");

		clienteDTO.setEndereco(endereco);

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve falhar quando endereço tem CEP inválido")
	void deveFalharQuandoEnderecoTemCepInvalido() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setEstado("SP");
		endereco.setCep("12345");

		clienteDTO.setEndereco(endereco);

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertFalse(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve validar metadata quando presente e válida")
	void deveValidarMetadataQuandoPresenteEValida() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");

		MetadataDTO metadata = new MetadataDTO();
		metadata.setOrigem("Web");
		metadata.setCanal("Online");
		metadata.setTags(Arrays.asList("premium", "vip"));
		metadata.setNotas("Cliente especial");

		clienteDTO.setMetadata(metadata);

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve testar getter e setter de id")
	void deveTestarGetterSetterDeId() {
		String id = "id-12345";
		clienteDTO.setId(id);
		assertEquals(id, clienteDTO.getId());
	}

	@Test
	@DisplayName("Deve testar getter e setter de clienteId")
	void deveTestarGetterSetterDeClienteId() {
		String clienteId = "cliente-67890";
		clienteDTO.setClienteId(clienteId);
		assertEquals(clienteId, clienteDTO.getClienteId());
	}

	@Test
	@DisplayName("Deve testar getter e setter de nomeCliente")
	void deveTestarGetterSetterDeNomeCliente() {
		String nome = "João Silva";
		clienteDTO.setNomeCliente(nome);
		assertEquals(nome, clienteDTO.getNomeCliente());
	}

	@Test
	@DisplayName("Deve testar getter e setter de emailCliente")
	void deveTestarGetterSetterDeEmailCliente() {
		String email = "joao@example.com";
		clienteDTO.setEmailCliente(email);
		assertEquals(email, clienteDTO.getEmailCliente());
	}

	@Test
	@DisplayName("Deve testar getter e setter de cpfCliente")
	void deveTestarGetterSetterDeCpfCliente() {
		String cpf = "12345678901";
		clienteDTO.setCpfCliente(cpf);
		assertEquals(cpf, clienteDTO.getCpfCliente());
	}

	@Test
	@DisplayName("Deve testar getter e setter de telefone")
	void deveTestarGetterSetterDeTelefone() {
		String telefone = "11987654321";
		clienteDTO.setTelefone(telefone);
		assertEquals(telefone, clienteDTO.getTelefone());
	}

	@Test
	@DisplayName("Deve testar getter e setter de endereco")
	void deveTestarGetterSetterDeEndereco() {
		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setRua("Rua Teste");
		clienteDTO.setEndereco(endereco);
		assertEquals(endereco, clienteDTO.getEndereco());
		assertEquals("Rua Teste", clienteDTO.getEndereco().getRua());
	}

	@Test
	@DisplayName("Deve testar getter e setter de ativo")
	void deveTestarGetterSetterDeAtivo() {
		clienteDTO.setAtivo(true);
		assertTrue(clienteDTO.getAtivo());

		clienteDTO.setAtivo(false);
		assertFalse(clienteDTO.getAtivo());

		clienteDTO.setAtivo(null);
		assertNull(clienteDTO.getAtivo());
	}

	@Test
	@DisplayName("Deve testar getter e setter de dataCadastro")
	void deveTestarGetterSetterDeDataCadastro() {
		LocalDateTime data = LocalDateTime.of(2024, 1, 1, 10, 30, 0);
		clienteDTO.setDataCadastro(data);
		assertEquals(data, clienteDTO.getDataCadastro());
	}

	@Test
	@DisplayName("Deve testar getter e setter de dataUltimaAtualizacao")
	void deveTestarGetterSetterDeDataUltimaAtualizacao() {
		LocalDateTime data = LocalDateTime.of(2024, 6, 15, 14, 45, 30);
		clienteDTO.setDataUltimaAtualizacao(data);
		assertEquals(data, clienteDTO.getDataUltimaAtualizacao());
	}

	@Test
	@DisplayName("Deve testar getter e setter de versao")
	void deveTestarGetterSetterDeVersao() {
		Integer versao = 5;
		clienteDTO.setVersao(versao);
		assertEquals(versao, clienteDTO.getVersao());
	}

	@Test
	@DisplayName("Deve testar getter e setter de metadata")
	void deveTestarGetterSetterDeMetadata() {
		MetadataDTO metadata = new MetadataDTO();
		metadata.setOrigem("App Mobile");
		clienteDTO.setMetadata(metadata);
		assertEquals(metadata, clienteDTO.getMetadata());
		assertEquals("App Mobile", clienteDTO.getMetadata().getOrigem());
	}

	@Test
	@DisplayName("Deve aceitar telefone null")
	void deveAceitarTelefoneNull() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");
		clienteDTO.setTelefone(null);

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar endereco null")
	void deveAceitarEnderecoNull() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");
		clienteDTO.setEndereco(null);

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar metadata null")
	void deveAceitarMetadataNull() {
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");
		clienteDTO.setMetadata(null);

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve validar múltiplos erros simultaneamente")
	void deveValidarMultiplosErrosSimultaneamente() {
		// Não define nada - todos os campos obrigatórios estão null
		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertEquals(3, violations.size());
	}

	@Test
	@DisplayName("Deve criar ClienteDTO completo com todos os campos")
	void deveCriarClienteDTOCompletoComTodosOsCampos() {
		LocalDateTime agora = LocalDateTime.now();

		EnderecoDTO endereco = new EnderecoDTO();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setComplemento("Apto 45");
		endereco.setBairro("Centro");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("12345678");

		MetadataDTO metadata = new MetadataDTO();
		metadata.setOrigem("Web");
		metadata.setCanal("Online");
		metadata.setTags(Arrays.asList("premium", "vip"));
		metadata.setNotas("Cliente especial");

		clienteDTO.setId("id-123");
		clienteDTO.setClienteId("cliente-456");
		clienteDTO.setNomeCliente("João Silva");
		clienteDTO.setEmailCliente("joao@example.com");
		clienteDTO.setCpfCliente("12345678901");
		clienteDTO.setTelefone("11987654321");
		clienteDTO.setEndereco(endereco);
		clienteDTO.setAtivo(true);
		clienteDTO.setDataCadastro(agora);
		clienteDTO.setDataUltimaAtualizacao(agora);
		clienteDTO.setVersao(1);
		clienteDTO.setMetadata(metadata);

		Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
		assertTrue(violations.isEmpty());

		assertEquals("id-123", clienteDTO.getId());
		assertEquals("cliente-456", clienteDTO.getClienteId());
		assertEquals("João Silva", clienteDTO.getNomeCliente());
		assertEquals("joao@example.com", clienteDTO.getEmailCliente());
		assertEquals("12345678901", clienteDTO.getCpfCliente());
		assertEquals("11987654321", clienteDTO.getTelefone());
		assertNotNull(clienteDTO.getEndereco());
		assertTrue(clienteDTO.getAtivo());
		assertEquals(agora, clienteDTO.getDataCadastro());
		assertEquals(agora, clienteDTO.getDataUltimaAtualizacao());
		assertEquals(1, clienteDTO.getVersao());
		assertNotNull(clienteDTO.getMetadata());
	}

	@Test
	@DisplayName("Deve aceitar versao zero")
	void deveAceitarVersaoZero() {
		clienteDTO.setVersao(0);
		assertEquals(0, clienteDTO.getVersao());
	}

	@Test
	@DisplayName("Deve aceitar versao negativa")
	void deveAceitarVersaoNegativa() {
		clienteDTO.setVersao(-1);
		assertEquals(-1, clienteDTO.getVersao());
	}

	@Test
	@DisplayName("Deve aceitar versao null")
	void deveAceitarVersaoNull() {
		clienteDTO.setVersao(null);
		assertNull(clienteDTO.getVersao());
	}

	@Test
	@DisplayName("Deve aceitar dataCadastro null")
	void deveAceitarDataCadastroNull() {
		clienteDTO.setDataCadastro(null);
		assertNull(clienteDTO.getDataCadastro());
	}

	@Test
	@DisplayName("Deve aceitar dataUltimaAtualizacao null")
	void deveAceitarDataUltimaAtualizacaoNull() {
		clienteDTO.setDataUltimaAtualizacao(null);
		assertNull(clienteDTO.getDataUltimaAtualizacao());
	}
}
