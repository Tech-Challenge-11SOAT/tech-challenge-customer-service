package br.com.postech.techchallange_customer.infrastructure.persistence.document;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClienteDocumentTest {

	private ClienteDocument clienteDocument;

	@BeforeEach
	void setUp() {
		clienteDocument = new ClienteDocument();
	}

	@Test
	@DisplayName("Deve criar ClienteDocument com construtor vazio")
	void deveCriarClienteDocumentComConstrutorVazio() {
		assertNotNull(clienteDocument);
		assertNull(clienteDocument.getId());
		assertNull(clienteDocument.getClienteId());
		assertNull(clienteDocument.getNomeCliente());
		assertNull(clienteDocument.getEmailCliente());
		assertNull(clienteDocument.getCpfCliente());
		assertNull(clienteDocument.getTelefone());
		assertNull(clienteDocument.getEndereco());
		assertNull(clienteDocument.getAtivo());
		assertNull(clienteDocument.getDataCadastro());
		assertNull(clienteDocument.getDataUltimaAtualizacao());
		assertNull(clienteDocument.getVersao());
		assertNull(clienteDocument.getMetadata());
	}

	@Test
	@DisplayName("Deve setar e obter id corretamente")
	void deveSetarEObterId() {
		clienteDocument.setId("id-123");
		assertEquals("id-123", clienteDocument.getId());
	}

	@Test
	@DisplayName("Deve setar e obter clienteId corretamente")
	void deveSetarEObterClienteId() {
		clienteDocument.setClienteId("cliente-uuid-456");
		assertEquals("cliente-uuid-456", clienteDocument.getClienteId());
	}

	@Test
	@DisplayName("Deve setar e obter nomeCliente corretamente")
	void deveSetarEObterNomeCliente() {
		clienteDocument.setNomeCliente("João Silva");
		assertEquals("João Silva", clienteDocument.getNomeCliente());
	}

	@Test
	@DisplayName("Deve setar e obter emailCliente corretamente")
	void deveSetarEObterEmailCliente() {
		clienteDocument.setEmailCliente("joao@email.com");
		assertEquals("joao@email.com", clienteDocument.getEmailCliente());
	}

	@Test
	@DisplayName("Deve setar e obter cpfCliente corretamente")
	void deveSetarEObterCpfCliente() {
		clienteDocument.setCpfCliente("12345678901");
		assertEquals("12345678901", clienteDocument.getCpfCliente());
	}

	@Test
	@DisplayName("Deve setar e obter telefone corretamente")
	void deveSetarEObterTelefone() {
		clienteDocument.setTelefone("11987654321");
		assertEquals("11987654321", clienteDocument.getTelefone());
	}

	@Test
	@DisplayName("Deve setar e obter ativo corretamente")
	void deveSetarEObterAtivo() {
		clienteDocument.setAtivo(true);
		assertTrue(clienteDocument.getAtivo());

		clienteDocument.setAtivo(false);
		assertFalse(clienteDocument.getAtivo());
	}

	@Test
	@DisplayName("Deve setar e obter dataCadastro corretamente")
	void deveSetarEObterDataCadastro() {
		LocalDateTime data = LocalDateTime.of(2026, 1, 1, 10, 0);
		clienteDocument.setDataCadastro(data);
		assertEquals(data, clienteDocument.getDataCadastro());
	}

	@Test
	@DisplayName("Deve setar e obter dataUltimaAtualizacao corretamente")
	void deveSetarEObterDataUltimaAtualizacao() {
		LocalDateTime data = LocalDateTime.of(2026, 1, 5, 15, 30);
		clienteDocument.setDataUltimaAtualizacao(data);
		assertEquals(data, clienteDocument.getDataUltimaAtualizacao());
	}

	@Test
	@DisplayName("Deve setar e obter versao corretamente")
	void deveSetarEObterVersao() {
		clienteDocument.setVersao(1);
		assertEquals(1, clienteDocument.getVersao());

		clienteDocument.setVersao(5);
		assertEquals(5, clienteDocument.getVersao());
	}

	@Test
	@DisplayName("Deve setar e obter endereco corretamente")
	void deveSetarEObterEndereco() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Rua das Flores");
		clienteDocument.setEndereco(endereco);

		assertNotNull(clienteDocument.getEndereco());
		assertEquals("Rua das Flores", clienteDocument.getEndereco().getRua());
	}

	@Test
	@DisplayName("Deve setar e obter metadata corretamente")
	void deveSetarEObterMetadata() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("Web");
		clienteDocument.setMetadata(metadata);

		assertNotNull(clienteDocument.getMetadata());
		assertEquals("Web", clienteDocument.getMetadata().getOrigem());
	}

	@Test
	@DisplayName("Deve permitir valores nulos")
	void devePermitirValoresNulos() {
		clienteDocument.setId(null);
		clienteDocument.setClienteId(null);
		clienteDocument.setNomeCliente(null);
		clienteDocument.setEmailCliente(null);
		clienteDocument.setCpfCliente(null);
		clienteDocument.setTelefone(null);
		clienteDocument.setEndereco(null);
		clienteDocument.setAtivo(null);
		clienteDocument.setDataCadastro(null);
		clienteDocument.setDataUltimaAtualizacao(null);
		clienteDocument.setVersao(null);
		clienteDocument.setMetadata(null);

		assertNull(clienteDocument.getId());
		assertNull(clienteDocument.getClienteId());
		assertNull(clienteDocument.getNomeCliente());
		assertNull(clienteDocument.getEmailCliente());
		assertNull(clienteDocument.getCpfCliente());
		assertNull(clienteDocument.getTelefone());
		assertNull(clienteDocument.getEndereco());
		assertNull(clienteDocument.getAtivo());
		assertNull(clienteDocument.getDataCadastro());
		assertNull(clienteDocument.getDataUltimaAtualizacao());
		assertNull(clienteDocument.getVersao());
		assertNull(clienteDocument.getMetadata());
	}

	@Test
	@DisplayName("Deve criar documento completo")
	void deveCriarDocumentoCompleto() {
		clienteDocument.setId("id-123");
		clienteDocument.setClienteId("cliente-uuid-123");
		clienteDocument.setNomeCliente("João Silva");
		clienteDocument.setEmailCliente("joao@email.com");
		clienteDocument.setCpfCliente("12345678901");
		clienteDocument.setTelefone("11987654321");
		clienteDocument.setAtivo(true);
		clienteDocument.setDataCadastro(LocalDateTime.now());
		clienteDocument.setDataUltimaAtualizacao(LocalDateTime.now());
		clienteDocument.setVersao(1);

		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		clienteDocument.setEndereco(endereco);

		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("Web");
		clienteDocument.setMetadata(metadata);

		assertNotNull(clienteDocument.getId());
		assertNotNull(clienteDocument.getClienteId());
		assertNotNull(clienteDocument.getNomeCliente());
		assertNotNull(clienteDocument.getEmailCliente());
		assertNotNull(clienteDocument.getCpfCliente());
		assertNotNull(clienteDocument.getTelefone());
		assertNotNull(clienteDocument.getEndereco());
		assertTrue(clienteDocument.getAtivo());
		assertNotNull(clienteDocument.getDataCadastro());
		assertNotNull(clienteDocument.getDataUltimaAtualizacao());
		assertNotNull(clienteDocument.getVersao());
		assertNotNull(clienteDocument.getMetadata());
	}

	// Testes para EnderecoDocument

	@Test
	@DisplayName("Deve criar EnderecoDocument com construtor vazio")
	void deveCriarEnderecoDocumentComConstrutorVazio() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();

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
	@DisplayName("Deve setar e obter rua em EnderecoDocument")
	void deveSetarEObterRuaEmEnderecoDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Rua das Flores");
		assertEquals("Rua das Flores", endereco.getRua());
	}

	@Test
	@DisplayName("Deve setar e obter numero em EnderecoDocument")
	void deveSetarEObterNumeroEmEnderecoDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setNumero("123");
		assertEquals("123", endereco.getNumero());
	}

	@Test
	@DisplayName("Deve setar e obter complemento em EnderecoDocument")
	void deveSetarEObterComplementoEmEnderecoDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setComplemento("Apto 45");
		assertEquals("Apto 45", endereco.getComplemento());
	}

	@Test
	@DisplayName("Deve setar e obter bairro em EnderecoDocument")
	void deveSetarEObterBairroEmEnderecoDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setBairro("Centro");
		assertEquals("Centro", endereco.getBairro());
	}

	@Test
	@DisplayName("Deve setar e obter cidade em EnderecoDocument")
	void deveSetarEObterCidadeEmEnderecoDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setCidade("São Paulo");
		assertEquals("São Paulo", endereco.getCidade());
	}

	@Test
	@DisplayName("Deve setar e obter estado em EnderecoDocument")
	void deveSetarEObterEstadoEmEnderecoDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setEstado("SP");
		assertEquals("SP", endereco.getEstado());
	}

	@Test
	@DisplayName("Deve setar e obter cep em EnderecoDocument")
	void deveSetarEObterCepEmEnderecoDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setCep("01234567");
		assertEquals("01234567", endereco.getCep());
	}

	@Test
	@DisplayName("Deve criar EnderecoDocument completo")
	void deveCriarEnderecoDocumentCompleto() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setComplemento("Apto 45");
		endereco.setBairro("Centro");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		assertEquals("Rua das Flores", endereco.getRua());
		assertEquals("123", endereco.getNumero());
		assertEquals("Apto 45", endereco.getComplemento());
		assertEquals("Centro", endereco.getBairro());
		assertEquals("São Paulo", endereco.getCidade());
		assertEquals("SP", endereco.getEstado());
		assertEquals("01234567", endereco.getCep());
	}

	@Test
	@DisplayName("Deve permitir valores nulos em EnderecoDocument")
	void devePermitirValoresNulosEmEnderecoDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua(null);
		endereco.setNumero(null);
		endereco.setComplemento(null);
		endereco.setBairro(null);
		endereco.setCidade(null);
		endereco.setEstado(null);
		endereco.setCep(null);

		assertNull(endereco.getRua());
		assertNull(endereco.getNumero());
		assertNull(endereco.getComplemento());
		assertNull(endereco.getBairro());
		assertNull(endereco.getCidade());
		assertNull(endereco.getEstado());
		assertNull(endereco.getCep());
	}

	// Testes para MetadataDocument

	@Test
	@DisplayName("Deve criar MetadataDocument com construtor vazio")
	void deveCriarMetadataDocumentComConstrutorVazio() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();

		assertNotNull(metadata);
		assertNull(metadata.getOrigem());
		assertNull(metadata.getCanal());
		assertNull(metadata.getTags());
		assertNull(metadata.getNotas());
		assertNull(metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve setar e obter origem em MetadataDocument")
	void deveSetarEObterOrigemEmMetadataDocument() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("Web");
		assertEquals("Web", metadata.getOrigem());
	}

	@Test
	@DisplayName("Deve setar e obter canal em MetadataDocument")
	void deveSetarEObterCanalEmMetadataDocument() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setCanal("Site");
		assertEquals("Site", metadata.getCanal());
	}

	@Test
	@DisplayName("Deve setar e obter tags em MetadataDocument")
	void deveSetarEObterTagsEmMetadataDocument() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		List<String> tags = Arrays.asList("VIP", "Premium");
		metadata.setTags(tags);

		assertEquals(tags, metadata.getTags());
		assertEquals(2, metadata.getTags().size());
		assertTrue(metadata.getTags().contains("VIP"));
		assertTrue(metadata.getTags().contains("Premium"));
	}

	@Test
	@DisplayName("Deve setar e obter notas em MetadataDocument")
	void deveSetarEObterNotasEmMetadataDocument() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setNotas("Cliente importante");
		assertEquals("Cliente importante", metadata.getNotas());
	}

	@Test
	@DisplayName("Deve setar e obter dataDesativacao em MetadataDocument")
	void deveSetarEObterDataDesativacaoEmMetadataDocument() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		LocalDateTime data = LocalDateTime.of(2026, 1, 5, 10, 30);
		metadata.setDataDesativacao(data);
		assertEquals(data, metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve criar MetadataDocument completo")
	void deveCriarMetadataDocumentCompleto() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("Web");
		metadata.setCanal("Site");
		metadata.setTags(Arrays.asList("VIP", "Premium", "Especial"));
		metadata.setNotas("Cliente VIP com benefícios especiais");
		metadata.setDataDesativacao(LocalDateTime.of(2026, 1, 5, 10, 30));

		assertEquals("Web", metadata.getOrigem());
		assertEquals("Site", metadata.getCanal());
		assertEquals(3, metadata.getTags().size());
		assertEquals("Cliente VIP com benefícios especiais", metadata.getNotas());
		assertNotNull(metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve permitir valores nulos em MetadataDocument")
	void devePermitirValoresNulosEmMetadataDocument() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem(null);
		metadata.setCanal(null);
		metadata.setTags(null);
		metadata.setNotas(null);
		metadata.setDataDesativacao(null);

		assertNull(metadata.getOrigem());
		assertNull(metadata.getCanal());
		assertNull(metadata.getTags());
		assertNull(metadata.getNotas());
		assertNull(metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve permitir lista vazia de tags em MetadataDocument")
	void devePermitirListaVaziaDeTagsEmMetadataDocument() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setTags(Arrays.asList());

		assertNotNull(metadata.getTags());
		assertTrue(metadata.getTags().isEmpty());
	}

	@Test
	@DisplayName("Deve alterar valores de EnderecoDocument múltiplas vezes")
	void deveAlterarValoresDeEnderecoDocumentMultiplasVezes() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();

		endereco.setRua("Rua A");
		assertEquals("Rua A", endereco.getRua());

		endereco.setRua("Rua B");
		assertEquals("Rua B", endereco.getRua());

		endereco.setCidade("São Paulo");
		assertEquals("São Paulo", endereco.getCidade());

		endereco.setCidade("Rio de Janeiro");
		assertEquals("Rio de Janeiro", endereco.getCidade());
	}

	@Test
	@DisplayName("Deve alterar valores de MetadataDocument múltiplas vezes")
	void deveAlterarValoresDeMetadataDocumentMultiplasVezes() {
		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();

		metadata.setOrigem("Web");
		assertEquals("Web", metadata.getOrigem());

		metadata.setOrigem("App");
		assertEquals("App", metadata.getOrigem());

		metadata.setTags(Arrays.asList("VIP"));
		assertEquals(1, metadata.getTags().size());

		metadata.setTags(Arrays.asList("VIP", "Premium"));
		assertEquals(2, metadata.getTags().size());
	}

	@Test
	@DisplayName("Deve associar EnderecoDocument e MetadataDocument a ClienteDocument")
	void deveAssociarEnderecoDocumentEMetadataDocumentAClienteDocument() {
		ClienteDocument.EnderecoDocument endereco = new ClienteDocument.EnderecoDocument();
		endereco.setRua("Rua das Flores");
		endereco.setCidade("São Paulo");

		ClienteDocument.MetadataDocument metadata = new ClienteDocument.MetadataDocument();
		metadata.setOrigem("Web");
		metadata.setTags(Arrays.asList("VIP"));

		clienteDocument.setEndereco(endereco);
		clienteDocument.setMetadata(metadata);

		assertNotNull(clienteDocument.getEndereco());
		assertNotNull(clienteDocument.getMetadata());
		assertEquals("Rua das Flores", clienteDocument.getEndereco().getRua());
		assertEquals("Web", clienteDocument.getMetadata().getOrigem());
	}
}
