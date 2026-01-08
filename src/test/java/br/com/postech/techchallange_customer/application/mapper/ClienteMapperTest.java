package br.com.postech.techchallange_customer.application.mapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.postech.techchallange_customer.application.dto.ClienteDTO;
import br.com.postech.techchallange_customer.application.dto.EnderecoDTO;
import br.com.postech.techchallange_customer.application.dto.MetadataDTO;
import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;

@DisplayName("ClienteMapper Tests")
class ClienteMapperTest {

	@Test
	@DisplayName("Deve converter Cliente para ClienteDTO com todos os campos")
	void deveConverterClienteParaClienteDTOComTodosOsCampos() {
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
		metadata.setTags(Arrays.asList("premium", "vip"));
		metadata.setNotas("Cliente especial");
		metadata.setDataDesativacao(agora);

		Cliente cliente = new Cliente();
		cliente.setId("id-123");
		cliente.setClienteId("cliente-456");
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setTelefone("11987654321");
		cliente.setEndereco(endereco);
		cliente.setAtivo(true);
		cliente.setDataCadastro(agora);
		cliente.setDataUltimaAtualizacao(agora);
		cliente.setVersao(1);
		cliente.setMetadata(metadata);

		ClienteDTO dto = ClienteMapper.toDTO(cliente);

		assertNotNull(dto);
		assertEquals("id-123", dto.getId());
		assertEquals("cliente-456", dto.getClienteId());
		assertEquals("João Silva", dto.getNomeCliente());
		assertEquals("joao@example.com", dto.getEmailCliente());
		assertEquals("12345678901", dto.getCpfCliente());
		assertEquals("11987654321", dto.getTelefone());
		assertTrue(dto.getAtivo());
		assertEquals(agora, dto.getDataCadastro());
		assertEquals(agora, dto.getDataUltimaAtualizacao());
		assertEquals(1, dto.getVersao());

		assertNotNull(dto.getEndereco());
		assertEquals("Rua das Flores", dto.getEndereco().getRua());
		assertEquals("123", dto.getEndereco().getNumero());
		assertEquals("Apto 45", dto.getEndereco().getComplemento());
		assertEquals("Centro", dto.getEndereco().getBairro());
		assertEquals("São Paulo", dto.getEndereco().getCidade());
		assertEquals("SP", dto.getEndereco().getEstado());
		assertEquals("12345678", dto.getEndereco().getCep());

		assertNotNull(dto.getMetadata());
		assertEquals("Web", dto.getMetadata().getOrigem());
		assertEquals("Online", dto.getMetadata().getCanal());
		assertEquals(2, dto.getMetadata().getTags().size());
		assertEquals("Cliente especial", dto.getMetadata().getNotas());
		assertEquals(agora, dto.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve retornar null quando Cliente é null ao converter para DTO")
	void deveRetornarNullQuandoClienteEhNullAoConverterParaDTO() {
		ClienteDTO dto = ClienteMapper.toDTO(null);
		assertNull(dto);
	}

	@Test
	@DisplayName("Deve converter Cliente para ClienteDTO sem endereco")
	void deveConverterClienteParaClienteDTOSemEndereco() {
		Cliente cliente = new Cliente();
		cliente.setId("id-123");
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setEndereco(null);

		ClienteDTO dto = ClienteMapper.toDTO(cliente);

		assertNotNull(dto);
		assertEquals("João Silva", dto.getNomeCliente());
		assertNull(dto.getEndereco());
	}

	@Test
	@DisplayName("Deve converter Cliente para ClienteDTO sem metadata")
	void deveConverterClienteParaClienteDTOSemMetadata() {
		Cliente cliente = new Cliente();
		cliente.setId("id-123");
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@example.com");
		cliente.setCpfCliente("12345678901");
		cliente.setMetadata(null);

		ClienteDTO dto = ClienteMapper.toDTO(cliente);

		assertNotNull(dto);
		assertEquals("João Silva", dto.getNomeCliente());
		assertNull(dto.getMetadata());
	}

	@Test
	@DisplayName("Deve converter ClienteDTO para Cliente com todos os campos")
	void deveConverterClienteDTOParaClienteComTodosOsCampos() {
		LocalDateTime agora = LocalDateTime.now();

		EnderecoDTO enderecoDTO = new EnderecoDTO();
		enderecoDTO.setRua("Avenida Paulista");
		enderecoDTO.setNumero("1000");
		enderecoDTO.setComplemento("Torre A");
		enderecoDTO.setBairro("Bela Vista");
		enderecoDTO.setCidade("São Paulo");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("01310100");

		MetadataDTO metadataDTO = new MetadataDTO();
		metadataDTO.setOrigem("Mobile");
		metadataDTO.setCanal("App");
		metadataDTO.setTags(Arrays.asList("corporativo", "premium"));
		metadataDTO.setNotas("Cliente corporativo");
		metadataDTO.setDataDesativacao(agora);

		ClienteDTO dto = new ClienteDTO();
		dto.setId("id-789");
		dto.setClienteId("cliente-101");
		dto.setNomeCliente("Maria Santos");
		dto.setEmailCliente("maria@example.com");
		dto.setCpfCliente("98765432109");
		dto.setTelefone("11999887766");
		dto.setEndereco(enderecoDTO);
		dto.setAtivo(false);
		dto.setDataCadastro(agora);
		dto.setDataUltimaAtualizacao(agora);
		dto.setVersao(5);
		dto.setMetadata(metadataDTO);

		Cliente cliente = ClienteMapper.toDomain(dto);

		assertNotNull(cliente);
		assertEquals("id-789", cliente.getId());
		assertEquals("cliente-101", cliente.getClienteId());
		assertEquals("Maria Santos", cliente.getNomeCliente());
		assertEquals("maria@example.com", cliente.getEmailCliente());
		assertEquals("98765432109", cliente.getCpfCliente());
		assertEquals("11999887766", cliente.getTelefone());
		assertFalse(cliente.getAtivo());
		assertEquals(agora, cliente.getDataCadastro());
		assertEquals(agora, cliente.getDataUltimaAtualizacao());
		assertEquals(5, cliente.getVersao());

		assertNotNull(cliente.getEndereco());
		assertEquals("Avenida Paulista", cliente.getEndereco().getRua());
		assertEquals("1000", cliente.getEndereco().getNumero());
		assertEquals("Torre A", cliente.getEndereco().getComplemento());
		assertEquals("Bela Vista", cliente.getEndereco().getBairro());
		assertEquals("São Paulo", cliente.getEndereco().getCidade());
		assertEquals("SP", cliente.getEndereco().getEstado());
		assertEquals("01310100", cliente.getEndereco().getCep());

		assertNotNull(cliente.getMetadata());
		assertEquals("Mobile", cliente.getMetadata().getOrigem());
		assertEquals("App", cliente.getMetadata().getCanal());
		assertEquals(2, cliente.getMetadata().getTags().size());
		assertEquals("Cliente corporativo", cliente.getMetadata().getNotas());
		assertEquals(agora, cliente.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve retornar null quando ClienteDTO é null ao converter para Domain")
	void deveRetornarNullQuandoClienteDTOEhNullAoConverterParaDomain() {
		Cliente cliente = ClienteMapper.toDomain(null);
		assertNull(cliente);
	}

	@Test
	@DisplayName("Deve converter ClienteDTO para Cliente sem endereco")
	void deveConverterClienteDTOParaClienteSemEndereco() {
		ClienteDTO dto = new ClienteDTO();
		dto.setId("id-123");
		dto.setNomeCliente("João Silva");
		dto.setEmailCliente("joao@example.com");
		dto.setCpfCliente("12345678901");
		dto.setEndereco(null);

		Cliente cliente = ClienteMapper.toDomain(dto);

		assertNotNull(cliente);
		assertEquals("João Silva", cliente.getNomeCliente());
		assertNull(cliente.getEndereco());
	}

	@Test
	@DisplayName("Deve converter ClienteDTO para Cliente sem metadata")
	void deveConverterClienteDTOParaClienteSemMetadata() {
		ClienteDTO dto = new ClienteDTO();
		dto.setId("id-123");
		dto.setNomeCliente("João Silva");
		dto.setEmailCliente("joao@example.com");
		dto.setCpfCliente("12345678901");
		dto.setMetadata(null);

		Cliente cliente = ClienteMapper.toDomain(dto);

		assertNotNull(cliente);
		assertEquals("João Silva", cliente.getNomeCliente());
		assertNull(cliente.getMetadata());
	}

	@Test
	@DisplayName("Deve converter Endereco para EnderecoDTO com todos os campos")
	void deveConverterEnderecoParaEnderecoDTOComTodosOsCampos() {
		Endereco endereco = new Endereco();
		endereco.setRua("Rua A");
		endereco.setNumero("100");
		endereco.setComplemento("Bloco B");
		endereco.setBairro("Jardim");
		endereco.setCidade("Rio de Janeiro");
		endereco.setEstado("RJ");
		endereco.setCep("20000000");

		EnderecoDTO dto = ClienteMapper.toEnderecoDTO(endereco);

		assertNotNull(dto);
		assertEquals("Rua A", dto.getRua());
		assertEquals("100", dto.getNumero());
		assertEquals("Bloco B", dto.getComplemento());
		assertEquals("Jardim", dto.getBairro());
		assertEquals("Rio de Janeiro", dto.getCidade());
		assertEquals("RJ", dto.getEstado());
		assertEquals("20000000", dto.getCep());
	}

	@Test
	@DisplayName("Deve retornar null quando Endereco é null ao converter para DTO")
	void deveRetornarNullQuandoEnderecoEhNullAoConverterParaDTO() {
		EnderecoDTO dto = ClienteMapper.toEnderecoDTO(null);
		assertNull(dto);
	}

	@Test
	@DisplayName("Deve converter Endereco vazio para EnderecoDTO")
	void deveConverterEnderecoVazioParaEnderecoDTO() {
		Endereco endereco = new Endereco();

		EnderecoDTO dto = ClienteMapper.toEnderecoDTO(endereco);

		assertNotNull(dto);
		assertNull(dto.getRua());
		assertNull(dto.getNumero());
		assertNull(dto.getComplemento());
		assertNull(dto.getBairro());
		assertNull(dto.getCidade());
		assertNull(dto.getEstado());
		assertNull(dto.getCep());
	}

	@Test
	@DisplayName("Deve converter EnderecoDTO para Endereco com todos os campos")
	void deveConverterEnderecoDTOParaEnderecoComTodosOsCampos() {
		EnderecoDTO dto = new EnderecoDTO();
		dto.setRua("Rua B");
		dto.setNumero("200");
		dto.setComplemento("Casa");
		dto.setBairro("Centro");
		dto.setCidade("Belo Horizonte");
		dto.setEstado("MG");
		dto.setCep("30000000");

		Endereco endereco = ClienteMapper.toEnderecoDomain(dto);

		assertNotNull(endereco);
		assertEquals("Rua B", endereco.getRua());
		assertEquals("200", endereco.getNumero());
		assertEquals("Casa", endereco.getComplemento());
		assertEquals("Centro", endereco.getBairro());
		assertEquals("Belo Horizonte", endereco.getCidade());
		assertEquals("MG", endereco.getEstado());
		assertEquals("30000000", endereco.getCep());
	}

	@Test
	@DisplayName("Deve retornar null quando EnderecoDTO é null ao converter para Domain")
	void deveRetornarNullQuandoEnderecoDTOEhNullAoConverterParaDomain() {
		Endereco endereco = ClienteMapper.toEnderecoDomain(null);
		assertNull(endereco);
	}

	@Test
	@DisplayName("Deve converter EnderecoDTO vazio para Endereco")
	void deveConverterEnderecoDTOVazioParaEndereco() {
		EnderecoDTO dto = new EnderecoDTO();

		Endereco endereco = ClienteMapper.toEnderecoDomain(dto);

		assertNotNull(endereco);
		assertNull(endereco.getRua());
		assertNull(endereco.getNumero());
		assertNull(endereco.getComplemento());
		assertNull(endereco.getBairro());
		assertNull(endereco.getCidade());
		assertNull(endereco.getEstado());
		assertNull(endereco.getCep());
	}

	@Test
	@DisplayName("Deve converter Metadata para MetadataDTO com todos os campos")
	void deveConverterMetadataParaMetadataDTOComTodosOsCampos() {
		LocalDateTime agora = LocalDateTime.now();
		List<String> tags = Arrays.asList("tag1", "tag2", "tag3");

		Metadata metadata = new Metadata();
		metadata.setOrigem("Loja");
		metadata.setCanal("Presencial");
		metadata.setTags(tags);
		metadata.setNotas("Observações importantes");
		metadata.setDataDesativacao(agora);

		MetadataDTO dto = ClienteMapper.toMetadataDTO(metadata);

		assertNotNull(dto);
		assertEquals("Loja", dto.getOrigem());
		assertEquals("Presencial", dto.getCanal());
		assertEquals(3, dto.getTags().size());
		assertEquals("tag1", dto.getTags().get(0));
		assertEquals("tag2", dto.getTags().get(1));
		assertEquals("tag3", dto.getTags().get(2));
		assertEquals("Observações importantes", dto.getNotas());
		assertEquals(agora, dto.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve retornar null quando Metadata é null ao converter para DTO")
	void deveRetornarNullQuandoMetadataEhNullAoConverterParaDTO() {
		MetadataDTO dto = ClienteMapper.toMetadataDTO(null);
		assertNull(dto);
	}

	@Test
	@DisplayName("Deve converter Metadata vazia para MetadataDTO")
	void deveConverterMetadataVaziaParaMetadataDTO() {
		Metadata metadata = new Metadata();

		MetadataDTO dto = ClienteMapper.toMetadataDTO(metadata);

		assertNotNull(dto);
		assertNull(dto.getOrigem());
		assertNull(dto.getCanal());
		assertNotNull(dto.getTags()); // Metadata inicializa tags como ArrayList vazio
		assertEquals(0, dto.getTags().size());
		assertNull(dto.getNotas());
		assertNull(dto.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve converter MetadataDTO para Metadata com todos os campos")
	void deveConverterMetadataDTOParaMetadataComTodosOsCampos() {
		LocalDateTime agora = LocalDateTime.now();
		List<String> tags = Arrays.asList("premium", "vip");

		MetadataDTO dto = new MetadataDTO();
		dto.setOrigem("Telefone");
		dto.setCanal("Call Center");
		dto.setTags(tags);
		dto.setNotas("Cliente preferencial");
		dto.setDataDesativacao(agora);

		Metadata metadata = ClienteMapper.toMetadataDomain(dto);

		assertNotNull(metadata);
		assertEquals("Telefone", metadata.getOrigem());
		assertEquals("Call Center", metadata.getCanal());
		assertEquals(2, metadata.getTags().size());
		assertTrue(metadata.getTags().contains("premium"));
		assertTrue(metadata.getTags().contains("vip"));
		assertEquals("Cliente preferencial", metadata.getNotas());
		assertEquals(agora, metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve retornar null quando MetadataDTO é null ao converter para Domain")
	void deveRetornarNullQuandoMetadataDTOEhNullAoConverterParaDomain() {
		Metadata metadata = ClienteMapper.toMetadataDomain(null);
		assertNull(metadata);
	}

	@Test
	@DisplayName("Deve converter MetadataDTO vazia para Metadata")
	void deveConverterMetadataDTOVaziaParaMetadata() {
		MetadataDTO dto = new MetadataDTO();

		Metadata metadata = ClienteMapper.toMetadataDomain(dto);

		assertNotNull(metadata);
		assertNull(metadata.getOrigem());
		assertNull(metadata.getCanal());
		assertNull(metadata.getTags());
		assertNull(metadata.getNotas());
		assertNull(metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve manter valores null ao converter Cliente para DTO")
	void deveManterValoresNullAoConverterClienteParaDTO() {
		Cliente cliente = new Cliente();
		cliente.setId(null);
		cliente.setClienteId(null);
		cliente.setNomeCliente(null);
		cliente.setEmailCliente(null);
		cliente.setCpfCliente(null);
		cliente.setTelefone(null);
		cliente.setAtivo(null);
		cliente.setDataCadastro(null);
		cliente.setDataUltimaAtualizacao(null);
		cliente.setVersao(null);

		ClienteDTO dto = ClienteMapper.toDTO(cliente);

		assertNotNull(dto);
		assertNull(dto.getId());
		assertNull(dto.getClienteId());
		assertNull(dto.getNomeCliente());
		assertNull(dto.getEmailCliente());
		assertNull(dto.getCpfCliente());
		assertNull(dto.getTelefone());
		assertNull(dto.getAtivo());
		assertNull(dto.getDataCadastro());
		assertNull(dto.getDataUltimaAtualizacao());
		assertNull(dto.getVersao());
	}

	@Test
	@DisplayName("Deve manter valores null ao converter ClienteDTO para Domain")
	void deveManterValoresNullAoConverterClienteDTOParaDomain() {
		ClienteDTO dto = new ClienteDTO();
		dto.setId(null);
		dto.setClienteId(null);
		dto.setNomeCliente(null);
		dto.setEmailCliente(null);
		dto.setCpfCliente(null);
		dto.setTelefone(null);
		dto.setAtivo(null);
		dto.setDataCadastro(null);
		dto.setDataUltimaAtualizacao(null);
		dto.setVersao(null);

		Cliente cliente = ClienteMapper.toDomain(dto);

		assertNotNull(cliente);
		assertNull(cliente.getId());
		assertNull(cliente.getClienteId());
		assertNull(cliente.getNomeCliente());
		assertNull(cliente.getEmailCliente());
		assertNull(cliente.getCpfCliente());
		assertNull(cliente.getTelefone());
		assertNull(cliente.getAtivo());
		assertNull(cliente.getDataCadastro());
		assertNull(cliente.getDataUltimaAtualizacao());
		assertNull(cliente.getVersao());
	}

	@Test
	@DisplayName("Deve realizar conversão bidirecional mantendo integridade dos dados")
	void deveRealizarConversaoBidirecionalMantendoIntegridadeDados() {
		LocalDateTime agora = LocalDateTime.now();

		Cliente clienteOriginal = new Cliente();
		clienteOriginal.setId("id-teste");
		clienteOriginal.setClienteId("cliente-teste");
		clienteOriginal.setNomeCliente("Teste Silva");
		clienteOriginal.setEmailCliente("teste@example.com");
		clienteOriginal.setCpfCliente("12345678901");
		clienteOriginal.setTelefone("11987654321");
		clienteOriginal.setAtivo(true);
		clienteOriginal.setDataCadastro(agora);
		clienteOriginal.setDataUltimaAtualizacao(agora);
		clienteOriginal.setVersao(1);

		// Cliente -> DTO -> Cliente
		ClienteDTO dto = ClienteMapper.toDTO(clienteOriginal);
		Cliente clienteConvertido = ClienteMapper.toDomain(dto);

		assertEquals(clienteOriginal.getId(), clienteConvertido.getId());
		assertEquals(clienteOriginal.getClienteId(), clienteConvertido.getClienteId());
		assertEquals(clienteOriginal.getNomeCliente(), clienteConvertido.getNomeCliente());
		assertEquals(clienteOriginal.getEmailCliente(), clienteConvertido.getEmailCliente());
		assertEquals(clienteOriginal.getCpfCliente(), clienteConvertido.getCpfCliente());
		assertEquals(clienteOriginal.getTelefone(), clienteConvertido.getTelefone());
		assertEquals(clienteOriginal.getAtivo(), clienteConvertido.getAtivo());
		assertEquals(clienteOriginal.getDataCadastro(), clienteConvertido.getDataCadastro());
		assertEquals(clienteOriginal.getDataUltimaAtualizacao(), clienteConvertido.getDataUltimaAtualizacao());
		assertEquals(clienteOriginal.getVersao(), clienteConvertido.getVersao());
	}

	@Test
	@DisplayName("Deve realizar conversão bidirecional de Endereco mantendo integridade")
	void deveRealizarConversaoBidirecionalDeEnderecoMantendoIntegridade() {
		Endereco enderecoOriginal = new Endereco();
		enderecoOriginal.setRua("Rua Teste");
		enderecoOriginal.setNumero("999");
		enderecoOriginal.setComplemento("Fundos");
		enderecoOriginal.setBairro("Teste");
		enderecoOriginal.setCidade("Testópolis");
		enderecoOriginal.setEstado("TS");
		enderecoOriginal.setCep("99999999");

		// Endereco -> DTO -> Endereco
		EnderecoDTO dto = ClienteMapper.toEnderecoDTO(enderecoOriginal);
		Endereco enderecoConvertido = ClienteMapper.toEnderecoDomain(dto);

		assertEquals(enderecoOriginal.getRua(), enderecoConvertido.getRua());
		assertEquals(enderecoOriginal.getNumero(), enderecoConvertido.getNumero());
		assertEquals(enderecoOriginal.getComplemento(), enderecoConvertido.getComplemento());
		assertEquals(enderecoOriginal.getBairro(), enderecoConvertido.getBairro());
		assertEquals(enderecoOriginal.getCidade(), enderecoConvertido.getCidade());
		assertEquals(enderecoOriginal.getEstado(), enderecoConvertido.getEstado());
		assertEquals(enderecoOriginal.getCep(), enderecoConvertido.getCep());
	}

	@Test
	@DisplayName("Deve realizar conversão bidirecional de Metadata mantendo integridade")
	void deveRealizarConversaoBidirecionalDeMetadataMantendoIntegridade() {
		LocalDateTime agora = LocalDateTime.now();

		Metadata metadataOriginal = new Metadata();
		metadataOriginal.setOrigem("Teste");
		metadataOriginal.setCanal("Canal Teste");
		metadataOriginal.setTags(Arrays.asList("teste1", "teste2"));
		metadataOriginal.setNotas("Notas de teste");
		metadataOriginal.setDataDesativacao(agora);

		// Metadata -> DTO -> Metadata
		MetadataDTO dto = ClienteMapper.toMetadataDTO(metadataOriginal);
		Metadata metadataConvertida = ClienteMapper.toMetadataDomain(dto);

		assertEquals(metadataOriginal.getOrigem(), metadataConvertida.getOrigem());
		assertEquals(metadataOriginal.getCanal(), metadataConvertida.getCanal());
		assertEquals(metadataOriginal.getTags().size(), metadataConvertida.getTags().size());
		assertEquals(metadataOriginal.getNotas(), metadataConvertida.getNotas());
		assertEquals(metadataOriginal.getDataDesativacao(), metadataConvertida.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve converter Cliente com Boolean ativo como false")
	void deveConverterClienteComBooleanAtivoComoFalse() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("Teste");
		cliente.setAtivo(false);

		ClienteDTO dto = ClienteMapper.toDTO(cliente);

		assertNotNull(dto);
		assertFalse(dto.getAtivo());
	}

	@Test
	@DisplayName("Deve converter ClienteDTO com Boolean ativo como false")
	void deveConverterClienteDTOComBooleanAtivoComoFalse() {
		ClienteDTO dto = new ClienteDTO();
		dto.setNomeCliente("Teste");
		dto.setAtivo(false);

		Cliente cliente = ClienteMapper.toDomain(dto);

		assertNotNull(cliente);
		assertFalse(cliente.getAtivo());
	}

	@Test
	@DisplayName("Deve converter Metadata com lista de tags vazia")
	void deveConverterMetadataComListaDeTagsVazia() {
		Metadata metadata = new Metadata();
		metadata.setTags(Arrays.asList());

		MetadataDTO dto = ClienteMapper.toMetadataDTO(metadata);

		assertNotNull(dto);
		assertNotNull(dto.getTags());
		assertEquals(0, dto.getTags().size());
	}

	@Test
	@DisplayName("Deve converter MetadataDTO com lista de tags null")
	void deveConverterMetadataDTOComListaDeTagsNull() {
		MetadataDTO dto = new MetadataDTO();
		dto.setTags(null);

		Metadata metadata = ClienteMapper.toMetadataDomain(dto);

		assertNotNull(metadata);
		assertNull(metadata.getTags());
	}

	@Test
	@DisplayName("Deve permitir instanciar ClienteMapper")
	void devePermitirInstanciarClienteMapper() {
		ClienteMapper mapper = new ClienteMapper();
		assertNotNull(mapper);
	}
}
