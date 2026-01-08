package br.com.postech.techchallange_customer.application.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("MetadataDTO Tests")
class MetadataDTOTest {

	private Validator validator;
	private MetadataDTO metadataDTO;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		metadataDTO = new MetadataDTO();
	}

	@Test
	@DisplayName("Deve criar MetadataDTO com construtor padrão")
	void deveCriarMetadataDTOComConstrutorPadrao() {
		MetadataDTO dto = new MetadataDTO();
		assertNotNull(dto);
	}

	@Test
	@DisplayName("Deve validar MetadataDTO válido com todos os campos")
	void deveValidarMetadataDTOValidoComTodosOsCampos() {
		metadataDTO.setOrigem("Web");
		metadataDTO.setCanal("Online");
		metadataDTO.setTags(Arrays.asList("premium", "vip"));
		metadataDTO.setNotas("Cliente especial");
		metadataDTO.setDataDesativacao(LocalDateTime.of(2025, 12, 31, 23, 59, 59));

		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve validar MetadataDTO sem nenhum campo preenchido")
	void deveValidarMetadataDTOSemNenhumCampoPreenchido() {
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve testar getter e setter de origem")
	void deveTestarGetterSetterDeOrigem() {
		String origem = "Mobile App";
		metadataDTO.setOrigem(origem);
		assertEquals(origem, metadataDTO.getOrigem());
	}

	@Test
	@DisplayName("Deve testar getter e setter de canal")
	void deveTestarGetterSetterDeCanal() {
		String canal = "Loja Física";
		metadataDTO.setCanal(canal);
		assertEquals(canal, metadataDTO.getCanal());
	}

	@Test
	@DisplayName("Deve testar getter e setter de tags")
	void deveTestarGetterSetterDeTags() {
		List<String> tags = Arrays.asList("premium", "vip", "fidelidade");
		metadataDTO.setTags(tags);
		assertEquals(tags, metadataDTO.getTags());
		assertEquals(3, metadataDTO.getTags().size());
	}

	@Test
	@DisplayName("Deve testar getter e setter de notas")
	void deveTestarGetterSetterDeNotas() {
		String notas = "Cliente importante com histórico positivo";
		metadataDTO.setNotas(notas);
		assertEquals(notas, metadataDTO.getNotas());
	}

	@Test
	@DisplayName("Deve testar getter e setter de dataDesativacao")
	void deveTestarGetterSetterDeDataDesativacao() {
		LocalDateTime data = LocalDateTime.of(2025, 6, 15, 10, 30, 45);
		metadataDTO.setDataDesativacao(data);
		assertEquals(data, metadataDTO.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve aceitar origem null")
	void deveAceitarOrigemNull() {
		metadataDTO.setOrigem(null);
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar origem vazia")
	void deveAceitarOrigemVazia() {
		metadataDTO.setOrigem("");
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar canal null")
	void deveAceitarCanalNull() {
		metadataDTO.setCanal(null);
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar canal vazio")
	void deveAceitarCanalVazio() {
		metadataDTO.setCanal("");
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar tags null")
	void deveAceitarTagsNull() {
		metadataDTO.setTags(null);
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar lista de tags vazia")
	void deveAceitarListaDeTagsVazia() {
		metadataDTO.setTags(new ArrayList<>());
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
		assertNotNull(metadataDTO.getTags());
		assertEquals(0, metadataDTO.getTags().size());
	}

	@Test
	@DisplayName("Deve aceitar notas null")
	void deveAceitarNotasNull() {
		metadataDTO.setNotas(null);
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar notas vazia")
	void deveAceitarNotasVazia() {
		metadataDTO.setNotas("");
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar dataDesativacao null")
	void deveAceitarDataDesativacaoNull() {
		metadataDTO.setDataDesativacao(null);
		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar lista com uma única tag")
	void deveAceitarListaComUmaUnicaTag() {
		metadataDTO.setTags(Collections.singletonList("premium"));
		assertEquals(1, metadataDTO.getTags().size());
		assertEquals("premium", metadataDTO.getTags().get(0));
	}

	@Test
	@DisplayName("Deve aceitar lista com múltiplas tags")
	void deveAceitarListaComMultiplasTags() {
		List<String> tags = Arrays.asList("premium", "vip", "fidelidade", "corporativo", "especial");
		metadataDTO.setTags(tags);
		assertEquals(5, metadataDTO.getTags().size());
		assertTrue(metadataDTO.getTags().contains("premium"));
		assertTrue(metadataDTO.getTags().contains("vip"));
		assertTrue(metadataDTO.getTags().contains("fidelidade"));
	}

	@Test
	@DisplayName("Deve aceitar tags com valores duplicados")
	void deveAceitarTagsComValoresDuplicados() {
		List<String> tags = Arrays.asList("premium", "premium", "vip");
		metadataDTO.setTags(tags);
		assertEquals(3, metadataDTO.getTags().size());
	}

	@Test
	@DisplayName("Deve aceitar origem com diferentes valores")
	void deveAceitarOrigemComDiferentesValores() {
		String[] origens = { "Web", "Mobile", "Loja Física", "Telemarketing", "Parceiro", "Indicação" };

		for (String origem : origens) {
			metadataDTO.setOrigem(origem);
			assertEquals(origem, metadataDTO.getOrigem());
			Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
			assertTrue(violations.isEmpty());
		}
	}

	@Test
	@DisplayName("Deve aceitar canal com diferentes valores")
	void deveAceitarCanalComDiferentesValores() {
		String[] canais = { "Online", "Offline", "Telefone", "Email", "WhatsApp", "Chat" };

		for (String canal : canais) {
			metadataDTO.setCanal(canal);
			assertEquals(canal, metadataDTO.getCanal());
			Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
			assertTrue(violations.isEmpty());
		}
	}

	@Test
	@DisplayName("Deve aceitar notas com texto longo")
	void deveAceitarNotasComTextoLongo() {
		String notasLongas = "Este é um cliente muito importante para a empresa. " +
				"Possui histórico de compras elevado e sempre paga em dia. " +
				"Requer atendimento diferenciado e possui gerente de conta dedicado.";
		metadataDTO.setNotas(notasLongas);
		assertEquals(notasLongas, metadataDTO.getNotas());
	}

	@Test
	@DisplayName("Deve aceitar notas com caracteres especiais")
	void deveAceitarNotasComCaracteresEspeciais() {
		String notas = "Cliente VIP! Atenção: não aplicar taxa de entrega. (Desconto: 15%)";
		metadataDTO.setNotas(notas);
		assertEquals(notas, metadataDTO.getNotas());
	}

	@Test
	@DisplayName("Deve aceitar dataDesativacao no passado")
	void deveAceitarDataDesativacaoNoPassado() {
		LocalDateTime dataPassado = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
		metadataDTO.setDataDesativacao(dataPassado);
		assertEquals(dataPassado, metadataDTO.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve aceitar dataDesativacao no futuro")
	void deveAceitarDataDesativacaoNoFuturo() {
		LocalDateTime dataFuturo = LocalDateTime.of(2030, 12, 31, 23, 59, 59);
		metadataDTO.setDataDesativacao(dataFuturo);
		assertEquals(dataFuturo, metadataDTO.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve aceitar dataDesativacao com data e hora específicas")
	void deveAceitarDataDesativacaoComDataEHoraEspecificas() {
		LocalDateTime data = LocalDateTime.of(2025, 6, 15, 14, 30, 45);
		metadataDTO.setDataDesativacao(data);
		assertEquals(2025, metadataDTO.getDataDesativacao().getYear());
		assertEquals(6, metadataDTO.getDataDesativacao().getMonthValue());
		assertEquals(15, metadataDTO.getDataDesativacao().getDayOfMonth());
		assertEquals(14, metadataDTO.getDataDesativacao().getHour());
		assertEquals(30, metadataDTO.getDataDesativacao().getMinute());
		assertEquals(45, metadataDTO.getDataDesativacao().getSecond());
	}

	@Test
	@DisplayName("Deve criar MetadataDTO completo e verificar todos os valores")
	void deveCriarMetadataDTOCompletoEVerificarTodosOsValores() {
		LocalDateTime dataDesativacao = LocalDateTime.of(2025, 12, 31, 23, 59, 59);
		List<String> tags = Arrays.asList("premium", "vip", "fidelidade");

		metadataDTO.setOrigem("Web");
		metadataDTO.setCanal("Online");
		metadataDTO.setTags(tags);
		metadataDTO.setNotas("Cliente especial com desconto permanente");
		metadataDTO.setDataDesativacao(dataDesativacao);

		Set<ConstraintViolation<MetadataDTO>> violations = validator.validate(metadataDTO);
		assertTrue(violations.isEmpty());

		assertEquals("Web", metadataDTO.getOrigem());
		assertEquals("Online", metadataDTO.getCanal());
		assertEquals(3, metadataDTO.getTags().size());
		assertEquals("Cliente especial com desconto permanente", metadataDTO.getNotas());
		assertEquals(dataDesativacao, metadataDTO.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve aceitar alteração de valores após inicialização")
	void deveAceitarAlteracaoDeValoresAposInicializacao() {
		metadataDTO.setOrigem("Web");
		assertEquals("Web", metadataDTO.getOrigem());

		metadataDTO.setOrigem("Mobile");
		assertEquals("Mobile", metadataDTO.getOrigem());
	}

	@Test
	@DisplayName("Deve aceitar tags contendo espaços")
	void deveAceitarTagsContendoEspacos() {
		List<String> tags = Arrays.asList("cliente premium", "programa fidelidade", "desconto especial");
		metadataDTO.setTags(tags);
		assertEquals(3, metadataDTO.getTags().size());
		assertTrue(metadataDTO.getTags().contains("cliente premium"));
	}

	@Test
	@DisplayName("Deve aceitar tags com caracteres especiais")
	void deveAceitarTagsComCaracteresEspeciais() {
		List<String> tags = Arrays.asList("VIP+", "desconto-20%", "cliente#1");
		metadataDTO.setTags(tags);
		assertEquals(3, metadataDTO.getTags().size());
	}

	@Test
	@DisplayName("Deve aceitar origem com espaços e caracteres especiais")
	void deveAceitarOrigemComEspacosECaracteresEspeciais() {
		metadataDTO.setOrigem("Loja Física - Shopping Center");
		assertEquals("Loja Física - Shopping Center", metadataDTO.getOrigem());
	}

	@Test
	@DisplayName("Deve aceitar canal com espaços e caracteres especiais")
	void deveAceitarCanalComEspacosECaracteresEspeciais() {
		metadataDTO.setCanal("WhatsApp Business");
		assertEquals("WhatsApp Business", metadataDTO.getCanal());
	}

	@Test
	@DisplayName("Deve manter referência da lista de tags")
	void deveManterReferenciaDaListaDeTags() {
		List<String> tags = new ArrayList<>(Arrays.asList("premium", "vip"));
		metadataDTO.setTags(tags);

		assertSame(tags, metadataDTO.getTags());
	}

	@Test
	@DisplayName("Deve aceitar dataDesativacao com hora mínima do dia")
	void deveAceitarDataDesativacaoComHoraMinimaEspecifica() {
		LocalDateTime data = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
		metadataDTO.setDataDesativacao(data);
		assertEquals(0, metadataDTO.getDataDesativacao().getHour());
		assertEquals(0, metadataDTO.getDataDesativacao().getMinute());
		assertEquals(0, metadataDTO.getDataDesativacao().getSecond());
	}

	@Test
	@DisplayName("Deve aceitar dataDesativacao com hora máxima do dia")
	void deveAceitarDataDesativacaoComHoraMaximaEspecifica() {
		LocalDateTime data = LocalDateTime.of(2025, 12, 31, 23, 59, 59);
		metadataDTO.setDataDesativacao(data);
		assertEquals(23, metadataDTO.getDataDesativacao().getHour());
		assertEquals(59, metadataDTO.getDataDesativacao().getMinute());
		assertEquals(59, metadataDTO.getDataDesativacao().getSecond());
	}

	@Test
	@DisplayName("Deve permitir resetar todos os campos para null")
	void devePermitirResetarTodosOsCamposParaNull() {
		metadataDTO.setOrigem("Web");
		metadataDTO.setCanal("Online");
		metadataDTO.setTags(Arrays.asList("premium"));
		metadataDTO.setNotas("Teste");
		metadataDTO.setDataDesativacao(LocalDateTime.now());

		metadataDTO.setOrigem(null);
		metadataDTO.setCanal(null);
		metadataDTO.setTags(null);
		metadataDTO.setNotas(null);
		metadataDTO.setDataDesativacao(null);

		assertNull(metadataDTO.getOrigem());
		assertNull(metadataDTO.getCanal());
		assertNull(metadataDTO.getTags());
		assertNull(metadataDTO.getNotas());
		assertNull(metadataDTO.getDataDesativacao());
	}

	@Test
	@DisplayName("Deve aceitar notas com quebras de linha")
	void deveAceitarNotasComQuebrasDeLinha() {
		String notas = "Linha 1\nLinha 2\nLinha 3";
		metadataDTO.setNotas(notas);
		assertEquals(notas, metadataDTO.getNotas());
		assertTrue(metadataDTO.getNotas().contains("\n"));
	}

	@Test
	@DisplayName("Deve aceitar tags com strings vazias na lista")
	void deveAceitarTagsComStringsVaziasNaLista() {
		List<String> tags = Arrays.asList("premium", "", "vip");
		metadataDTO.setTags(tags);
		assertEquals(3, metadataDTO.getTags().size());
		assertEquals("", metadataDTO.getTags().get(1));
	}
}
