package br.com.postech.techchallange_customer.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MetadataTest {

	private Metadata metadata;

	@BeforeEach
	void setUp() {
		metadata = new Metadata();
	}

	@Test
	@DisplayName("Deve criar metadata com construtor vazio")
	void deveCriarMetadataComConstrutorVazio() {
		assertNotNull(metadata);
		assertNull(metadata.getOrigem());
		assertNull(metadata.getCanal());
		assertNotNull(metadata.getTags());
		assertTrue(metadata.getTags().isEmpty());
		assertNull(metadata.getNotas());
		assertNull(metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve criar metadata com construtor parametrizado")
	void deveCriarMetadataComConstrutorParametrizado() {
		Metadata metadataCompleto = new Metadata("Web", "Site");

		assertNotNull(metadataCompleto);
		assertEquals("Web", metadataCompleto.getOrigem());
		assertEquals("Site", metadataCompleto.getCanal());
		assertNotNull(metadataCompleto.getTags());
		assertTrue(metadataCompleto.getTags().isEmpty());
		assertNull(metadataCompleto.getNotas());
		assertNull(metadataCompleto.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve adicionar tag com sucesso")
	void deveAdicionarTagComSucesso() {
		metadata.adicionarTag("VIP");

		assertTrue(metadata.getTags().contains("VIP"));
		assertEquals(1, metadata.getTags().size());
	}

	@Test
	@DisplayName("Deve adicionar múltiplas tags diferentes")
	void deveAdicionarMultiplasTagsDiferentes() {
		metadata.adicionarTag("VIP");
		metadata.adicionarTag("Premium");
		metadata.adicionarTag("Especial");

		assertEquals(3, metadata.getTags().size());
		assertTrue(metadata.getTags().contains("VIP"));
		assertTrue(metadata.getTags().contains("Premium"));
		assertTrue(metadata.getTags().contains("Especial"));
	}

	@Test
	@DisplayName("Não deve adicionar tag duplicada")
	void naoDeveAdicionarTagDuplicada() {
		metadata.adicionarTag("VIP");
		metadata.adicionarTag("VIP");

		assertEquals(1, metadata.getTags().size());
		assertTrue(metadata.getTags().contains("VIP"));
	}

	@Test
	@DisplayName("Deve adicionar tag quando lista é nula")
	void deveAdicionarTagQuandoListaNula() {
		metadata.setTags(null);
		metadata.adicionarTag("VIP");

		assertNotNull(metadata.getTags());
		assertTrue(metadata.getTags().contains("VIP"));
		assertEquals(1, metadata.getTags().size());
	}

	@Test
	@DisplayName("Deve remover tag existente")
	void deveRemoverTagExistente() {
		metadata.adicionarTag("VIP");
		metadata.adicionarTag("Premium");

		metadata.removerTag("VIP");

		assertFalse(metadata.getTags().contains("VIP"));
		assertTrue(metadata.getTags().contains("Premium"));
		assertEquals(1, metadata.getTags().size());
	}

	@Test
	@DisplayName("Deve ignorar remoção de tag inexistente")
	void deveIgnorarRemocaoDeTagInexistente() {
		metadata.adicionarTag("VIP");

		metadata.removerTag("Premium");

		assertTrue(metadata.getTags().contains("VIP"));
		assertEquals(1, metadata.getTags().size());
	}

	@Test
	@DisplayName("Não deve lançar exceção ao remover tag quando lista é nula")
	void naoDeveLancarExcecaoAoRemoverTagQuandoListaNula() {
		metadata.setTags(null);

		assertDoesNotThrow(() -> metadata.removerTag("VIP"));
	}

	@Test
	@DisplayName("Deve verificar se possui tag específica")
	void deveVerificarSePossuiTagEspecifica() {
		metadata.adicionarTag("VIP");
		metadata.adicionarTag("Premium");

		assertTrue(metadata.hasTag("VIP"));
		assertTrue(metadata.hasTag("Premium"));
		assertFalse(metadata.hasTag("Especial"));
	}

	@Test
	@DisplayName("Deve retornar falso ao verificar tag quando lista é nula")
	void deveRetornarFalsoAoVerificarTagQuandoListaNula() {
		metadata.setTags(null);

		assertFalse(metadata.hasTag("VIP"));
	}

	@Test
	@DisplayName("Deve retornar falso ao verificar tag inexistente")
	void deveRetornarFalsoAoVerificarTagInexistente() {
		metadata.adicionarTag("VIP");

		assertFalse(metadata.hasTag("Premium"));
	}

	@Test
	@DisplayName("Deve verificar se está desativado quando data desativação não é nula")
	void deveVerificarSeEstaDesativadoQuandoDataDesativacaoNaoNula() {
		LocalDateTime dataDesativacao = LocalDateTime.now();
		metadata.setDataDesativacao(dataDesativacao);

		assertTrue(metadata.isDesativado());
	}

	@Test
	@DisplayName("Deve verificar que não está desativado quando data desativação é nula")
	void deveVerificarQueNaoEstaDesativadoQuandoDataDesativacaoNula() {
		metadata.setDataDesativacao(null);

		assertFalse(metadata.isDesativado());
	}

	@Test
	@DisplayName("Deve limpar data de desativação")
	void deveLimparDataDeDesativacao() {
		LocalDateTime dataDesativacao = LocalDateTime.now();
		metadata.setDataDesativacao(dataDesativacao);

		assertTrue(metadata.isDesativado());

		metadata.limparDataDesativacao();

		assertNull(metadata.getDataDesativacao());
		assertFalse(metadata.isDesativado());
	}

	@Test
	@DisplayName("Deve setar e obter origem corretamente")
	void deveSetarEObterOrigemCorretamente() {
		metadata.setOrigem("Web");
		assertEquals("Web", metadata.getOrigem());
	}

	@Test
	@DisplayName("Deve setar e obter canal corretamente")
	void deveSetarEObterCanalCorretamente() {
		metadata.setCanal("Site");
		assertEquals("Site", metadata.getCanal());
	}

	@Test
	@DisplayName("Deve setar e obter tags corretamente")
	void deveSetarEObterTagsCorretamente() {
		List<String> tags = Arrays.asList("VIP", "Premium");
		metadata.setTags(tags);

		assertEquals(tags, metadata.getTags());
		assertEquals(2, metadata.getTags().size());
	}

	@Test
	@DisplayName("Deve setar e obter notas corretamente")
	void deveSetarEObterNotasCorretamente() {
		metadata.setNotas("Cliente importante");
		assertEquals("Cliente importante", metadata.getNotas());
	}

	@Test
	@DisplayName("Deve setar e obter data desativação corretamente")
	void deveSetarEObterDataDesativacaoCorretamente() {
		LocalDateTime dataDesativacao = LocalDateTime.of(2026, 1, 5, 10, 30);
		metadata.setDataDesativacao(dataDesativacao);

		assertEquals(dataDesativacao, metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve permitir limpar data de desativação mesmo quando já está nula")
	void devePermitirLimparDataDeDesativacaoMesmoQuandoJaEstaNula() {
		metadata.setDataDesativacao(null);

		assertDoesNotThrow(() -> metadata.limparDataDesativacao());
		assertNull(metadata.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve adicionar tag em lista vazia")
	void deveAdicionarTagEmListaVazia() {
		metadata.setTags(new ArrayList<>());
		metadata.adicionarTag("Nova");

		assertTrue(metadata.hasTag("Nova"));
		assertEquals(1, metadata.getTags().size());
	}

	@Test
	@DisplayName("Deve remover todas as tags")
	void deveRemoverTodasAsTags() {
		metadata.adicionarTag("Tag1");
		metadata.adicionarTag("Tag2");
		metadata.adicionarTag("Tag3");

		metadata.removerTag("Tag1");
		metadata.removerTag("Tag2");
		metadata.removerTag("Tag3");

		assertTrue(metadata.getTags().isEmpty());
	}

	@Test
	@DisplayName("Deve manter outras tags ao remover uma específica")
	void deveManterOutrasTagsAoRemoverUmaEspecifica() {
		metadata.adicionarTag("Tag1");
		metadata.adicionarTag("Tag2");
		metadata.adicionarTag("Tag3");

		metadata.removerTag("Tag2");

		assertTrue(metadata.hasTag("Tag1"));
		assertFalse(metadata.hasTag("Tag2"));
		assertTrue(metadata.hasTag("Tag3"));
		assertEquals(2, metadata.getTags().size());
	}
}
