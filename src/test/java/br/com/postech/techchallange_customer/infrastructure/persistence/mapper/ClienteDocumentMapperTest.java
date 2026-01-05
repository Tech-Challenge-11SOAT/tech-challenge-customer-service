package br.com.postech.techchallange_customer.infrastructure.persistence.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.infrastructure.persistence.document.ClienteDocument;

class ClienteDocumentMapperTest {

	private Cliente cliente;
	private ClienteDocument clienteDocument;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;

	@BeforeEach
	void setUp() {
		dataCadastro = LocalDateTime.of(2026, 1, 1, 10, 0);
		dataAtualizacao = LocalDateTime.of(2026, 1, 5, 15, 30);

		// Setup Cliente
		cliente = new Cliente();
		cliente.setId("id-123");
		cliente.setClienteId("cliente-uuid-123");
		cliente.setNomeCliente("João Silva");
		cliente.setEmailCliente("joao@email.com");
		cliente.setCpfCliente("12345678901");
		cliente.setTelefone("11987654321");
		cliente.setAtivo(true);
		cliente.setDataCadastro(dataCadastro);
		cliente.setDataUltimaAtualizacao(dataAtualizacao);
		cliente.setVersao(1);

		// Setup ClienteDocument
		clienteDocument = new ClienteDocument();
		clienteDocument.setId("id-123");
		clienteDocument.setClienteId("cliente-uuid-123");
		clienteDocument.setNomeCliente("João Silva");
		clienteDocument.setEmailCliente("joao@email.com");
		clienteDocument.setCpfCliente("12345678901");
		clienteDocument.setTelefone("11987654321");
		clienteDocument.setAtivo(true);
		clienteDocument.setDataCadastro(dataCadastro);
		clienteDocument.setDataUltimaAtualizacao(dataAtualizacao);
		clienteDocument.setVersao(1);
	}

	// Testes toDocument

	@Test
	@DisplayName("Deve converter Cliente para ClienteDocument")
	void deveConverterClienteParaClienteDocument() {
		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertEquals(cliente.getId(), resultado.getId());
		assertEquals(cliente.getClienteId(), resultado.getClienteId());
		assertEquals(cliente.getNomeCliente(), resultado.getNomeCliente());
		assertEquals(cliente.getEmailCliente(), resultado.getEmailCliente());
		assertEquals(cliente.getCpfCliente(), resultado.getCpfCliente());
		assertEquals(cliente.getTelefone(), resultado.getTelefone());
		assertEquals(cliente.getAtivo(), resultado.getAtivo());
		assertEquals(cliente.getDataCadastro(), resultado.getDataCadastro());
		assertEquals(cliente.getDataUltimaAtualizacao(), resultado.getDataUltimaAtualizacao());
		assertEquals(cliente.getVersao(), resultado.getVersao());
	}

	@Test
	@DisplayName("Deve retornar null quando Cliente é null em toDocument")
	void deveRetornarNullQuandoClienteNuloEmToDocument() {
		ClienteDocument resultado = ClienteDocumentMapper.toDocument(null);
		assertNull(resultado);
	}

	@Test
	@DisplayName("Deve converter Cliente com Endereco para ClienteDocument")
	void deveConverterClienteComEnderecoParaClienteDocument() {
		Endereco endereco = new Endereco();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setComplemento("Apto 45");
		endereco.setBairro("Centro");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");
		cliente.setEndereco(endereco);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNotNull(resultado.getEndereco());
		assertEquals("Rua das Flores", resultado.getEndereco().getRua());
		assertEquals("123", resultado.getEndereco().getNumero());
		assertEquals("Apto 45", resultado.getEndereco().getComplemento());
		assertEquals("Centro", resultado.getEndereco().getBairro());
		assertEquals("São Paulo", resultado.getEndereco().getCidade());
		assertEquals("SP", resultado.getEndereco().getEstado());
		assertEquals("01234567", resultado.getEndereco().getCep());
	}

	@Test
	@DisplayName("Deve converter Cliente sem Endereco para ClienteDocument")
	void deveConverterClienteSemEnderecoParaClienteDocument() {
		cliente.setEndereco(null);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve converter Cliente com Metadata para ClienteDocument")
	void deveConverterClienteComMetadataParaClienteDocument() {
		Metadata metadata = new Metadata();
		metadata.setOrigem("Web");
		metadata.setCanal("Site");
		metadata.setTags(Arrays.asList("VIP", "Premium"));
		metadata.setNotas("Cliente importante");
		metadata.setDataDesativacao(LocalDateTime.of(2026, 1, 5, 10, 30));
		cliente.setMetadata(metadata);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNotNull(resultado.getMetadata());
		assertEquals("Web", resultado.getMetadata().getOrigem());
		assertEquals("Site", resultado.getMetadata().getCanal());
		assertEquals(2, resultado.getMetadata().getTags().size());
		assertTrue(resultado.getMetadata().getTags().contains("VIP"));
		assertTrue(resultado.getMetadata().getTags().contains("Premium"));
		assertEquals("Cliente importante", resultado.getMetadata().getNotas());
		assertNotNull(resultado.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve converter Cliente sem Metadata para ClienteDocument")
	void deveConverterClienteSemMetadataParaClienteDocument() {
		cliente.setMetadata(null);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve converter Cliente completo com Endereco e Metadata para ClienteDocument")
	void deveConverterClienteCompletoParaClienteDocument() {
		Endereco endereco = new Endereco();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");
		cliente.setEndereco(endereco);

		Metadata metadata = new Metadata();
		metadata.setOrigem("Web");
		metadata.setCanal("Site");
		metadata.setTags(Arrays.asList("VIP"));
		cliente.setMetadata(metadata);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNotNull(resultado.getEndereco());
		assertNotNull(resultado.getMetadata());
		assertEquals("Rua das Flores", resultado.getEndereco().getRua());
		assertEquals("Web", resultado.getMetadata().getOrigem());
	}

	// Testes toDomain

	@Test
	@DisplayName("Deve converter ClienteDocument para Cliente")
	void deveConverterClienteDocumentParaCliente() {
		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertEquals(clienteDocument.getId(), resultado.getId());
		assertEquals(clienteDocument.getClienteId(), resultado.getClienteId());
		assertEquals(clienteDocument.getNomeCliente(), resultado.getNomeCliente());
		assertEquals(clienteDocument.getEmailCliente(), resultado.getEmailCliente());
		assertEquals(clienteDocument.getCpfCliente(), resultado.getCpfCliente());
		assertEquals(clienteDocument.getTelefone(), resultado.getTelefone());
		assertEquals(clienteDocument.getAtivo(), resultado.getAtivo());
		assertEquals(clienteDocument.getDataCadastro(), resultado.getDataCadastro());
		assertEquals(clienteDocument.getDataUltimaAtualizacao(), resultado.getDataUltimaAtualizacao());
		assertEquals(clienteDocument.getVersao(), resultado.getVersao());
	}

	@Test
	@DisplayName("Deve retornar null quando ClienteDocument é null em toDomain")
	void deveRetornarNullQuandoClienteDocumentNuloEmToDomain() {
		Cliente resultado = ClienteDocumentMapper.toDomain(null);
		assertNull(resultado);
	}

	@Test
	@DisplayName("Deve converter ClienteDocument com EnderecoDocument para Cliente")
	void deveConverterClienteDocumentComEnderecoParaCliente() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setComplemento("Apto 45");
		endereco.setBairro("Centro");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");
		clienteDocument.setEndereco(endereco);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNotNull(resultado.getEndereco());
		assertEquals("Rua das Flores", resultado.getEndereco().getRua());
		assertEquals("123", resultado.getEndereco().getNumero());
		assertEquals("Apto 45", resultado.getEndereco().getComplemento());
		assertEquals("Centro", resultado.getEndereco().getBairro());
		assertEquals("São Paulo", resultado.getEndereco().getCidade());
		assertEquals("SP", resultado.getEndereco().getEstado());
		assertEquals("01234567", resultado.getEndereco().getCep());
	}

	@Test
	@DisplayName("Deve converter ClienteDocument sem EnderecoDocument para Cliente")
	void deveConverterClienteDocumentSemEnderecoParaCliente() {
		clienteDocument.setEndereco(null);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve converter ClienteDocument com MetadataDocument para Cliente")
	void deveConverterClienteDocumentComMetadataParaCliente() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("Web");
		metadata.setCanal("Site");
		metadata.setTags(Arrays.asList("VIP", "Premium"));
		metadata.setNotas("Cliente importante");
		metadata.setDataDesativacao(LocalDateTime.of(2026, 1, 5, 10, 30));
		clienteDocument.setMetadata(metadata);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNotNull(resultado.getMetadata());
		assertEquals("Web", resultado.getMetadata().getOrigem());
		assertEquals("Site", resultado.getMetadata().getCanal());
		assertEquals(2, resultado.getMetadata().getTags().size());
		assertTrue(resultado.getMetadata().getTags().contains("VIP"));
		assertTrue(resultado.getMetadata().getTags().contains("Premium"));
		assertEquals("Cliente importante", resultado.getMetadata().getNotas());
		assertNotNull(resultado.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve converter ClienteDocument sem MetadataDocument para Cliente")
	void deveConverterClienteDocumentSemMetadataParaCliente() {
		clienteDocument.setMetadata(null);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve converter ClienteDocument completo com EnderecoDocument e MetadataDocument para Cliente")
	void deveConverterClienteDocumentCompletoParaCliente() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");
		clienteDocument.setEndereco(endereco);

		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("Web");
		metadata.setCanal("Site");
		metadata.setTags(Arrays.asList("VIP"));
		clienteDocument.setMetadata(metadata);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNotNull(resultado.getEndereco());
		assertNotNull(resultado.getMetadata());
		assertEquals("Rua das Flores", resultado.getEndereco().getRua());
		assertEquals("Web", resultado.getMetadata().getOrigem());
	}

	// Testes de conversão bidirecional

	@Test
	@DisplayName("Deve manter dados ao converter Cliente para Document e de volta para Domain")
	void deveManterDadosAoConverterBidirecional() {
		Endereco endereco = new Endereco();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");
		cliente.setEndereco(endereco);

		Metadata metadata = new Metadata();
		metadata.setOrigem("Web");
		metadata.setCanal("Site");
		metadata.setTags(Arrays.asList("VIP", "Premium"));
		cliente.setMetadata(metadata);

		ClienteDocument document = ClienteDocumentMapper.toDocument(cliente);
		Cliente clienteConvertido = ClienteDocumentMapper.toDomain(document);

		assertEquals(cliente.getId(), clienteConvertido.getId());
		assertEquals(cliente.getClienteId(), clienteConvertido.getClienteId());
		assertEquals(cliente.getNomeCliente(), clienteConvertido.getNomeCliente());
		assertEquals(cliente.getEmailCliente(), clienteConvertido.getEmailCliente());
		assertEquals(cliente.getCpfCliente(), clienteConvertido.getCpfCliente());
		assertEquals(cliente.getTelefone(), clienteConvertido.getTelefone());
		assertEquals(cliente.getAtivo(), clienteConvertido.getAtivo());
		assertNotNull(clienteConvertido.getEndereco());
		assertNotNull(clienteConvertido.getMetadata());
		assertEquals("Rua das Flores", clienteConvertido.getEndereco().getRua());
		assertEquals("Web", clienteConvertido.getMetadata().getOrigem());
		assertEquals(2, clienteConvertido.getMetadata().getTags().size());
	}

	@Test
	@DisplayName("Deve manter dados ao converter Document para Domain e de volta para Document")
	void deveManterDadosAoConverterBidirecionalInverso() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");
		clienteDocument.setEndereco(endereco);

		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("Web");
		metadata.setCanal("Site");
		metadata.setTags(Arrays.asList("VIP", "Premium"));
		clienteDocument.setMetadata(metadata);

		Cliente clienteDomain = ClienteDocumentMapper.toDomain(clienteDocument);
		ClienteDocument documentConvertido = ClienteDocumentMapper.toDocument(clienteDomain);

		assertEquals(clienteDocument.getId(), documentConvertido.getId());
		assertEquals(clienteDocument.getClienteId(), documentConvertido.getClienteId());
		assertEquals(clienteDocument.getNomeCliente(), documentConvertido.getNomeCliente());
		assertEquals(clienteDocument.getEmailCliente(), documentConvertido.getEmailCliente());
		assertEquals(clienteDocument.getCpfCliente(), documentConvertido.getCpfCliente());
		assertEquals(clienteDocument.getTelefone(), documentConvertido.getTelefone());
		assertEquals(clienteDocument.getAtivo(), documentConvertido.getAtivo());
		assertNotNull(documentConvertido.getEndereco());
		assertNotNull(documentConvertido.getMetadata());
		assertEquals("Rua das Flores", documentConvertido.getEndereco().getRua());
		assertEquals("Web", documentConvertido.getMetadata().getOrigem());
		assertEquals(2, documentConvertido.getMetadata().getTags().size());
	}

	// Testes com valores nulos e vazios

	@Test
	@DisplayName("Deve converter Endereco nulo corretamente")
	void deveConverterEnderecoNuloCorretamente() {
		Endereco enderecoNulo = null;
		cliente.setEndereco(enderecoNulo);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve converter Metadata nula corretamente")
	void deveConverterMetadataNulaCorretamente() {
		Metadata metadataNula = null;
		cliente.setMetadata(metadataNula);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve converter EnderecoDocument nulo corretamente")
	void deveConverterEnderecoDocumentNuloCorretamente() {
		ClienteDocument.EnderecoDocument enderecoNulo = null;
		clienteDocument.setEndereco(enderecoNulo);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve converter MetadataDocument nula corretamente")
	void deveConverterMetadataDocumentNulaCorretamente() {
		ClienteDocument.MetadataDocument metadataNula = null;
		clienteDocument.setMetadata(metadataNula);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve converter Metadata com lista vazia de tags")
	void deveConverterMetadataComListaVaziaDeTags() {
		Metadata metadata = new Metadata();
		metadata.setOrigem("Web");
		metadata.setTags(Arrays.asList());
		cliente.setMetadata(metadata);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNotNull(resultado.getMetadata());
		assertNotNull(resultado.getMetadata().getTags());
		assertTrue(resultado.getMetadata().getTags().isEmpty());
	}

	@Test
	@DisplayName("Deve converter todos os campos de Endereco")
	void deveConverterTodosCamposDeEndereco() {
		Endereco endereco = new Endereco();
		endereco.setRua("Rua Test");
		endereco.setNumero("456");
		endereco.setComplemento("Bloco B");
		endereco.setBairro("Jardim");
		endereco.setCidade("Rio de Janeiro");
		endereco.setEstado("RJ");
		endereco.setCep("98765432");
		cliente.setEndereco(endereco);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado.getEndereco());
		assertEquals("Rua Test", resultado.getEndereco().getRua());
		assertEquals("456", resultado.getEndereco().getNumero());
		assertEquals("Bloco B", resultado.getEndereco().getComplemento());
		assertEquals("Jardim", resultado.getEndereco().getBairro());
		assertEquals("Rio de Janeiro", resultado.getEndereco().getCidade());
		assertEquals("RJ", resultado.getEndereco().getEstado());
		assertEquals("98765432", resultado.getEndereco().getCep());
	}

	@Test
	@DisplayName("Deve converter todos os campos de Metadata")
	void deveConverterTodosCamposDeMetadata() {
		Metadata metadata = new Metadata();
		metadata.setOrigem("App");
		metadata.setCanal("Mobile");
		metadata.setTags(Arrays.asList("Gold", "Especial", "Teste"));
		metadata.setNotas("Notas importantes");
		metadata.setDataDesativacao(LocalDateTime.of(2026, 12, 31, 23, 59));
		cliente.setMetadata(metadata);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado.getMetadata());
		assertEquals("App", resultado.getMetadata().getOrigem());
		assertEquals("Mobile", resultado.getMetadata().getCanal());
		assertEquals(3, resultado.getMetadata().getTags().size());
		assertTrue(resultado.getMetadata().getTags().contains("Gold"));
		assertTrue(resultado.getMetadata().getTags().contains("Especial"));
		assertTrue(resultado.getMetadata().getTags().contains("Teste"));
		assertEquals("Notas importantes", resultado.getMetadata().getNotas());
		assertEquals(LocalDateTime.of(2026, 12, 31, 23, 59), resultado.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve converter todos os campos de EnderecoDocument para Domain")
	void deveConverterTodosCamposDeEnderecoDocumentParaDomain() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Avenida Principal");
		endereco.setNumero("789");
		endereco.setComplemento("Casa");
		endereco.setBairro("Vila");
		endereco.setCidade("Curitiba");
		endereco.setEstado("PR");
		endereco.setCep("11223344");
		clienteDocument.setEndereco(endereco);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado.getEndereco());
		assertEquals("Avenida Principal", resultado.getEndereco().getRua());
		assertEquals("789", resultado.getEndereco().getNumero());
		assertEquals("Casa", resultado.getEndereco().getComplemento());
		assertEquals("Vila", resultado.getEndereco().getBairro());
		assertEquals("Curitiba", resultado.getEndereco().getCidade());
		assertEquals("PR", resultado.getEndereco().getEstado());
		assertEquals("11223344", resultado.getEndereco().getCep());
	}

	@Test
	@DisplayName("Deve converter todos os campos de MetadataDocument para Domain")
	void deveConverterTodosCamposDeMetadataDocumentParaDomain() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("API");
		metadata.setCanal("Integration");
		metadata.setTags(Arrays.asList("Partner", "B2B"));
		metadata.setNotas("Cliente parceiro");
		metadata.setDataDesativacao(LocalDateTime.of(2026, 6, 15, 12, 0));
		clienteDocument.setMetadata(metadata);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado.getMetadata());
		assertEquals("API", resultado.getMetadata().getOrigem());
		assertEquals("Integration", resultado.getMetadata().getCanal());
		assertEquals(2, resultado.getMetadata().getTags().size());
		assertTrue(resultado.getMetadata().getTags().contains("Partner"));
		assertTrue(resultado.getMetadata().getTags().contains("B2B"));
		assertEquals("Cliente parceiro", resultado.getMetadata().getNotas());
		assertEquals(LocalDateTime.of(2026, 6, 15, 12, 0), resultado.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve converter Cliente com todos os campos nulos")
	void deveConverterClienteComTodosCamposNulos() {
		Cliente clienteNulo = new Cliente();
		clienteNulo.setId(null);
		clienteNulo.setClienteId(null);
		clienteNulo.setNomeCliente(null);
		clienteNulo.setEmailCliente(null);
		clienteNulo.setCpfCliente(null);
		clienteNulo.setTelefone(null);
		clienteNulo.setAtivo(null);
		clienteNulo.setEndereco(null);
		clienteNulo.setMetadata(null);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(clienteNulo);

		assertNotNull(resultado);
		assertNull(resultado.getId());
		assertNull(resultado.getClienteId());
		assertNull(resultado.getNomeCliente());
		assertNull(resultado.getEmailCliente());
		assertNull(resultado.getCpfCliente());
		assertNull(resultado.getTelefone());
		assertNull(resultado.getAtivo());
		assertNull(resultado.getEndereco());
		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve converter ClienteDocument com todos os campos nulos")
	void deveConverterClienteDocumentComTodosCamposNulos() {
		ClienteDocument documentNulo = new ClienteDocument();
		documentNulo.setId(null);
		documentNulo.setClienteId(null);
		documentNulo.setNomeCliente(null);
		documentNulo.setEmailCliente(null);
		documentNulo.setCpfCliente(null);
		documentNulo.setTelefone(null);
		documentNulo.setAtivo(null);
		documentNulo.setEndereco(null);
		documentNulo.setMetadata(null);

		Cliente resultado = ClienteDocumentMapper.toDomain(documentNulo);

		assertNotNull(resultado);
		assertNull(resultado.getId());
		assertNull(resultado.getClienteId());
		assertNull(resultado.getNomeCliente());
		assertNull(resultado.getEmailCliente());
		assertNull(resultado.getCpfCliente());
		assertNull(resultado.getTelefone());
		assertNull(resultado.getAtivo());
		assertNull(resultado.getEndereco());
		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve converter Endereco com todos os campos nulos")
	void deveConverterEnderecoComTodosCamposNulos() {
		Endereco endereco = new Endereco();
		endereco.setRua(null);
		endereco.setNumero(null);
		endereco.setComplemento(null);
		endereco.setBairro(null);
		endereco.setCidade(null);
		endereco.setEstado(null);
		endereco.setCep(null);
		cliente.setEndereco(endereco);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNotNull(resultado.getEndereco());
		assertNull(resultado.getEndereco().getRua());
		assertNull(resultado.getEndereco().getNumero());
		assertNull(resultado.getEndereco().getComplemento());
		assertNull(resultado.getEndereco().getBairro());
		assertNull(resultado.getEndereco().getCidade());
		assertNull(resultado.getEndereco().getEstado());
		assertNull(resultado.getEndereco().getCep());
	}

	@Test
	@DisplayName("Deve converter EnderecoDocument com todos os campos nulos")
	void deveConverterEnderecoDocumentComTodosCamposNulos() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua(null);
		endereco.setNumero(null);
		endereco.setComplemento(null);
		endereco.setBairro(null);
		endereco.setCidade(null);
		endereco.setEstado(null);
		endereco.setCep(null);
		clienteDocument.setEndereco(endereco);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNotNull(resultado.getEndereco());
		assertNull(resultado.getEndereco().getRua());
		assertNull(resultado.getEndereco().getNumero());
		assertNull(resultado.getEndereco().getComplemento());
		assertNull(resultado.getEndereco().getBairro());
		assertNull(resultado.getEndereco().getCidade());
		assertNull(resultado.getEndereco().getEstado());
		assertNull(resultado.getEndereco().getCep());
	}

	@Test
	@DisplayName("Deve converter Metadata com todos os campos nulos")
	void deveConverterMetadataComTodosCamposNulos() {
		Metadata metadata = new Metadata();
		metadata.setOrigem(null);
		metadata.setCanal(null);
		metadata.setTags(null);
		metadata.setNotas(null);
		metadata.setDataDesativacao(null);
		cliente.setMetadata(metadata);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNotNull(resultado.getMetadata());
		assertNull(resultado.getMetadata().getOrigem());
		assertNull(resultado.getMetadata().getCanal());
		assertNull(resultado.getMetadata().getTags());
		assertNull(resultado.getMetadata().getNotas());
		assertNull(resultado.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve converter MetadataDocument com todos os campos nulos")
	void deveConverterMetadataDocumentComTodosCamposNulos() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem(null);
		metadata.setCanal(null);
		metadata.setTags(null);
		metadata.setNotas(null);
		metadata.setDataDesativacao(null);
		clienteDocument.setMetadata(metadata);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNotNull(resultado.getMetadata());
		assertNull(resultado.getMetadata().getOrigem());
		assertNull(resultado.getMetadata().getCanal());
		assertNull(resultado.getMetadata().getTags());
		assertNull(resultado.getMetadata().getNotas());
		assertNull(resultado.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve permitir instanciar ClienteDocumentMapper")
	void devePermitirInstanciarClienteDocumentMapper() throws Exception {
		Constructor<ClienteDocumentMapper> constructor = ClienteDocumentMapper.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		ClienteDocumentMapper instance = constructor.newInstance();
		assertNotNull(instance);
	}

	@Test
	@DisplayName("Deve converter Cliente com Endereco vazio mas não nulo")
	void deveConverterClienteComEnderecoVazioMasNaoNulo() {
		Endereco endereco = new Endereco();
		cliente.setEndereco(endereco);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNotNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve converter Cliente com Metadata vazia mas não nula")
	void deveConverterClienteComMetadataVaziaMasNaoNula() {
		Metadata metadata = new Metadata();
		cliente.setMetadata(metadata);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNotNull(resultado);
		assertNotNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve converter ClienteDocument com EnderecoDocument vazio mas não nulo")
	void deveConverterClienteDocumentComEnderecoDocumentVazioMasNaoNulo() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		clienteDocument.setEndereco(endereco);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNotNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve converter ClienteDocument com MetadataDocument vazia mas não nula")
	void deveConverterClienteDocumentComMetadataDocumentVaziaMasNaoNula() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		clienteDocument.setMetadata(metadata);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNotNull(resultado);
		assertNotNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve manter referência null de Endereco em conversão toDocument")
	void deveManterReferenciaNullDeEnderecoEmConversaoToDocument() {
		cliente.setEndereco(null);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve manter referência null de Metadata em conversão toDocument")
	void deveManterReferenciaNullDeMetadataEmConversaoToDocument() {
		cliente.setMetadata(null);

		ClienteDocument resultado = ClienteDocumentMapper.toDocument(cliente);

		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve manter referência null de EnderecoDocument em conversão toDomain")
	void deveManterReferenciaNullDeEnderecoDocumentEmConversaoToDomain() {
		clienteDocument.setEndereco(null);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNull(resultado.getEndereco());
	}

	@Test
	@DisplayName("Deve manter referência null de MetadataDocument em conversão toDomain")
	void deveManterReferenciaNullDeMetadataDocumentEmConversaoToDomain() {
		clienteDocument.setMetadata(null);

		Cliente resultado = ClienteDocumentMapper.toDomain(clienteDocument);

		assertNull(resultado.getMetadata());
	}

	@Test
	@DisplayName("Deve converter múltiplos clientes mantendo independência")
	void deveConverterMultiplosClientesMantenndoIndependencia() {
		Cliente cliente1 = new Cliente();
		cliente1.setNomeCliente("Cliente 1");
		Endereco endereco1 = new Endereco();
		endereco1.setRua("Rua 1");
		cliente1.setEndereco(endereco1);

		Cliente cliente2 = new Cliente();
		cliente2.setNomeCliente("Cliente 2");
		cliente2.setEndereco(null);

		ClienteDocument doc1 = ClienteDocumentMapper.toDocument(cliente1);
		ClienteDocument doc2 = ClienteDocumentMapper.toDocument(cliente2);

		assertNotNull(doc1.getEndereco());
		assertNull(doc2.getEndereco());
		assertEquals("Cliente 1", doc1.getNomeCliente());
		assertEquals("Cliente 2", doc2.getNomeCliente());
	}

	@Test
	@DisplayName("Deve converter múltiplos documents mantendo independência")
	void deveConverterMultiplosDocumentsMantenndoIndependencia() {
		ClienteDocument doc1 = new ClienteDocument();
		doc1.setNomeCliente("Doc 1");
		ClienteDocument.MetadataDocument meta1 = new ClienteDocument.MetadataDocument();
		meta1.setOrigem("Web");
		doc1.setMetadata(meta1);

		ClienteDocument doc2 = new ClienteDocument();
		doc2.setNomeCliente("Doc 2");
		doc2.setMetadata(null);

		Cliente cliente1 = ClienteDocumentMapper.toDomain(doc1);
		Cliente cliente2 = ClienteDocumentMapper.toDomain(doc2);

		assertNotNull(cliente1.getMetadata());
		assertNull(cliente2.getMetadata());
		assertEquals("Doc 1", cliente1.getNomeCliente());
		assertEquals("Doc 2", cliente2.getNomeCliente());
	}

	@Test
	@DisplayName("Deve cobrir branches não executados dos métodos privados - cenário completo 1")
	void deveCobrirBranchesMetodosPrivadosCenario1() {
		// Testa todos os branches de toEnderecoDocument com objeto completo
		Endereco endereco = new Endereco();
		endereco.setRua("Av Principal");
		endereco.setNumero("999");
		endereco.setComplemento("Sala 10");
		endereco.setBairro("Bairro Teste");
		endereco.setCidade("Cidade Teste");
		endereco.setEstado("MG");
		endereco.setCep("30000000");

		Cliente clienteCompleto = new Cliente();
		clienteCompleto.setId("test-1");
		clienteCompleto.setCpfCliente("11111111111");
		clienteCompleto.setNomeCliente("Teste Completo");
		clienteCompleto.setEmailCliente("teste@teste.com");
		clienteCompleto.setEndereco(endereco);

		ClienteDocument doc = ClienteDocumentMapper.toDocument(clienteCompleto);

		assertNotNull(doc);
		assertNotNull(doc.getEndereco());
		assertEquals("Av Principal", doc.getEndereco().getRua());
		assertEquals("30000000", doc.getEndereco().getCep()); // CEP não é formatado no mapper
	}

	@Test
	@DisplayName("Deve cobrir branches não executados dos métodos privados - cenário completo 2")
	void deveCobrirBranchesMetodosPrivadosCenario2() {
		// Testa todos os branches de toMetadataDocument com objeto completo
		Metadata metadata = new Metadata();
		metadata.setOrigem("API");
		metadata.setCanal("Mobile");
		metadata.setTags(Arrays.asList("importante", "vip"));
		metadata.setNotas("Anotações importantes");
		metadata.setDataDesativacao(LocalDateTime.now());

		Cliente clienteCompleto = new Cliente();
		clienteCompleto.setId("test-2");
		clienteCompleto.setCpfCliente("22222222222");
		clienteCompleto.setNomeCliente("Cliente Metadata");
		clienteCompleto.setEmailCliente("metadata@teste.com");
		clienteCompleto.setMetadata(metadata);

		ClienteDocument doc = ClienteDocumentMapper.toDocument(clienteCompleto);

		assertNotNull(doc);
		assertNotNull(doc.getMetadata());
		assertEquals("API", doc.getMetadata().getOrigem());
		assertEquals(2, doc.getMetadata().getTags().size());
		assertNotNull(doc.getMetadata().getDataDesativacao());
	}

	@Test
	@DisplayName("Deve cobrir branches não executados dos métodos privados - conversão document para domain")
	void deveCobrirBranchesConversaoDocumentParaDomain() {
		// Testa toEnderecoDomain e toMetadataDomain com objetos completos
		ClienteDocument.EnderecoDocument enderecoDoc = new ClienteDocument.EnderecoDocument();
		enderecoDoc.setRua("Rua Document");
		enderecoDoc.setNumero("888");
		enderecoDoc.setComplemento("Bloco B");
		enderecoDoc.setBairro("Vila Document");
		enderecoDoc.setCidade("Cidade Doc");
		enderecoDoc.setEstado("RS");
		enderecoDoc.setCep("90000-000");

		ClienteDocument.MetadataDocument metadataDoc = new ClienteDocument.MetadataDocument();
		metadataDoc.setOrigem("Sistema");
		metadataDoc.setCanal("Web");
		metadataDoc.setTags(Arrays.asList("teste", "doc"));
		metadataDoc.setNotas("Notas do documento");
		metadataDoc.setDataDesativacao(LocalDateTime.now());

		ClienteDocument doc = new ClienteDocument();
		doc.setId("doc-1");
		doc.setClienteId("cliente-doc-1");
		doc.setCpfCliente("33333333333");
		doc.setNomeCliente("Nome Document");
		doc.setEmailCliente("doc@teste.com");
		doc.setEndereco(enderecoDoc);
		doc.setMetadata(metadataDoc);

		Cliente cliente = ClienteDocumentMapper.toDomain(doc);

		assertNotNull(cliente);
		assertNotNull(cliente.getEndereco());
		assertNotNull(cliente.getMetadata());
		assertEquals("Rua Document", cliente.getEndereco().getRua());
		assertEquals("90000-000", cliente.getEndereco().getCep()); // CEP vem formatado do document
		assertEquals("Sistema", cliente.getMetadata().getOrigem());
		assertEquals(2, cliente.getMetadata().getTags().size());
	}

	@Test
	@DisplayName("Deve cobrir branch null do toEnderecoDocument usando reflection")
	void deveCobrirBranchNullToEnderecoDocumentReflection() throws Exception {
		// Usa reflection para chamar o método privado com null
		Method method = ClienteDocumentMapper.class.getDeclaredMethod("toEnderecoDocument", Endereco.class);
		method.setAccessible(true);

		ClienteDocument.EnderecoDocument result = (ClienteDocument.EnderecoDocument) method.invoke(null,
				(Endereco) null);

		assertNull(result);
	}

	@Test
	@DisplayName("Deve cobrir branch null do toEnderecoDomain usando reflection")
	void deveCobrirBranchNullToEnderecoDomainReflection() throws Exception {
		// Usa reflection para chamar o método privado com null
		Method method = ClienteDocumentMapper.class.getDeclaredMethod("toEnderecoDomain",
				ClienteDocument.EnderecoDocument.class);
		method.setAccessible(true);

		Endereco result = (Endereco) method.invoke(null, (ClienteDocument.EnderecoDocument) null);

		assertNull(result);
	}

	@Test
	@DisplayName("Deve cobrir branch null do toMetadataDocument usando reflection")
	void deveCobrirBranchNullToMetadataDocumentReflection() throws Exception {
		// Usa reflection para chamar o método privado com null
		Method method = ClienteDocumentMapper.class.getDeclaredMethod("toMetadataDocument", Metadata.class);
		method.setAccessible(true);

		ClienteDocument.MetadataDocument result = (ClienteDocument.MetadataDocument) method.invoke(null,
				(Metadata) null);

		assertNull(result);
	}

	@Test
	@DisplayName("Deve cobrir branch null do toMetadataDomain usando reflection")
	void deveCobrirBranchNullToMetadataDomainReflection() throws Exception {
		// Usa reflection para chamar o método privado com null
		Method method = ClienteDocumentMapper.class.getDeclaredMethod("toMetadataDomain",
				ClienteDocument.MetadataDocument.class);
		method.setAccessible(true);

		Metadata result = (Metadata) method.invoke(null, (ClienteDocument.MetadataDocument) null);

		assertNull(result);
	}
}