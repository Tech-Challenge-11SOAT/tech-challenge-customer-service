package br.com.postech.techchallange_customer.application.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnderecoDTO Tests")
class EnderecoDTOTest {

	private Validator validator;
	private EnderecoDTO enderecoDTO;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		enderecoDTO = new EnderecoDTO();
	}

	@Test
	@DisplayName("Deve criar EnderecoDTO com construtor padrão")
	void deveCriarEnderecoDTOComConstrutorPadrao() {
		EnderecoDTO dto = new EnderecoDTO();
		assertNotNull(dto);
	}

	@Test
	@DisplayName("Deve validar EnderecoDTO válido com todos os campos")
	void deveValidarEnderecoDTOValido() {
		enderecoDTO.setRua("Rua das Flores");
		enderecoDTO.setNumero("123");
		enderecoDTO.setComplemento("Apto 45");
		enderecoDTO.setBairro("Centro");
		enderecoDTO.setCidade("São Paulo");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve validar EnderecoDTO com campos opcionais vazios")
	void deveValidarEnderecoDTOComCamposOpcionaisVazios() {
		enderecoDTO.setEstado("RJ");
		enderecoDTO.setCep("87654321");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar estado válido com 2 caracteres maiúsculos")
	void deveAceitarEstadoValidoComDoisCaracteres() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar todos os estados brasileiros válidos")
	void deveAceitarTodosEstadosBrasileirosValidos() {
		String[] estados = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
				"MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
				"RS", "RO", "RR", "SC", "SP", "SE", "TO" };

		for (String estado : estados) {
			enderecoDTO.setEstado(estado);
			enderecoDTO.setCep("12345678");

			Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
			assertTrue(violations.isEmpty(), "Estado " + estado + " deveria ser válido");
		}
	}

	@Test
	@DisplayName("Deve falhar quando estado tem apenas 1 caractere")
	void deveFalharQuandoEstadoTemApenasUmCaractere() {
		enderecoDTO.setEstado("S");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Estado deve ter 2 caracteres")));
	}

	@Test
	@DisplayName("Deve falhar quando estado tem mais de 2 caracteres")
	void deveFalharQuandoEstadoTemMaisDeDoisCaracteres() {
		enderecoDTO.setEstado("SAO");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Estado deve ter 2 caracteres")));
	}

	@Test
	@DisplayName("Deve falhar quando estado tem letras minúsculas")
	void deveFalharQuandoEstadoTemLetrasMinusculas() {
		enderecoDTO.setEstado("sp");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Estado deve ter 2 caracteres")));
	}

	@Test
	@DisplayName("Deve falhar quando estado tem letras mistas")
	void deveFalharQuandoEstadoTemLetrasMistas() {
		enderecoDTO.setEstado("Sp");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Estado deve ter 2 caracteres")));
	}

	@Test
	@DisplayName("Deve falhar quando estado contém números")
	void deveFalharQuandoEstadoContemNumeros() {
		enderecoDTO.setEstado("S1");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Estado deve ter 2 caracteres")));
	}

	@Test
	@DisplayName("Deve falhar quando estado contém caracteres especiais")
	void deveFalharQuandoEstadoContemCaracteresEspeciais() {
		enderecoDTO.setEstado("S-");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Estado deve ter 2 caracteres")));
	}

	@Test
	@DisplayName("Deve aceitar estado null")
	void deveAceitarEstadoNull() {
		enderecoDTO.setEstado(null);
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar CEP válido com 8 dígitos")
	void deveAceitarCepValidoComOitoDigitos() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar CEP com todos zeros")
	void deveAceitarCepComTodosZeros() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("00000000");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve falhar quando CEP tem menos de 8 dígitos")
	void deveFalharQuandoCepTemMenosDeOitoDigitos() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("1234567");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("CEP deve conter 8 dígitos")));
	}

	@Test
	@DisplayName("Deve falhar quando CEP tem mais de 8 dígitos")
	void deveFalharQuandoCepTemMaisDeOitoDigitos() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("123456789");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("CEP deve conter 8 dígitos")));
	}

	@Test
	@DisplayName("Deve falhar quando CEP contém letras")
	void deveFalharQuandoCepContemLetras() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("1234567A");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("CEP deve conter 8 dígitos")));
	}

	@Test
	@DisplayName("Deve falhar quando CEP contém hífen")
	void deveFalharQuandoCepContemHifen() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345-678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("CEP deve conter 8 dígitos")));
	}

	@Test
	@DisplayName("Deve falhar quando CEP contém espaços")
	void deveFalharQuandoCepContemEspacos() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("123 56789");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("CEP deve conter 8 dígitos")));
	}

	@Test
	@DisplayName("Deve aceitar CEP null")
	void deveAceitarCepNull() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep(null);

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve testar getter e setter de rua")
	void deveTestarGetterSetterDeRua() {
		String rua = "Rua das Flores";
		enderecoDTO.setRua(rua);
		assertEquals(rua, enderecoDTO.getRua());
	}

	@Test
	@DisplayName("Deve testar getter e setter de numero")
	void deveTestarGetterSetterDeNumero() {
		String numero = "123";
		enderecoDTO.setNumero(numero);
		assertEquals(numero, enderecoDTO.getNumero());
	}

	@Test
	@DisplayName("Deve testar getter e setter de complemento")
	void deveTestarGetterSetterDeComplemento() {
		String complemento = "Apto 45";
		enderecoDTO.setComplemento(complemento);
		assertEquals(complemento, enderecoDTO.getComplemento());
	}

	@Test
	@DisplayName("Deve testar getter e setter de bairro")
	void deveTestarGetterSetterDeBairro() {
		String bairro = "Centro";
		enderecoDTO.setBairro(bairro);
		assertEquals(bairro, enderecoDTO.getBairro());
	}

	@Test
	@DisplayName("Deve testar getter e setter de cidade")
	void deveTestarGetterSetterDeCidade() {
		String cidade = "São Paulo";
		enderecoDTO.setCidade(cidade);
		assertEquals(cidade, enderecoDTO.getCidade());
	}

	@Test
	@DisplayName("Deve testar getter e setter de estado")
	void deveTestarGetterSetterDeEstado() {
		String estado = "SP";
		enderecoDTO.setEstado(estado);
		assertEquals(estado, enderecoDTO.getEstado());
	}

	@Test
	@DisplayName("Deve testar getter e setter de cep")
	void deveTestarGetterSetterDeCep() {
		String cep = "12345678";
		enderecoDTO.setCep(cep);
		assertEquals(cep, enderecoDTO.getCep());
	}

	@Test
	@DisplayName("Deve aceitar rua null")
	void deveAceitarRuaNull() {
		enderecoDTO.setRua(null);
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar numero null")
	void deveAceitarNumeroNull() {
		enderecoDTO.setNumero(null);
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar complemento null")
	void deveAceitarComplementoNull() {
		enderecoDTO.setComplemento(null);
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar bairro null")
	void deveAceitarBairroNull() {
		enderecoDTO.setBairro(null);
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar cidade null")
	void deveAceitarCidadeNull() {
		enderecoDTO.setCidade(null);
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar rua vazia")
	void deveAceitarRuaVazia() {
		enderecoDTO.setRua("");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar numero vazio")
	void deveAceitarNumeroVazio() {
		enderecoDTO.setNumero("");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar complemento vazio")
	void deveAceitarComplementoVazio() {
		enderecoDTO.setComplemento("");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar bairro vazio")
	void deveAceitarBairroVazio() {
		enderecoDTO.setBairro("");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar cidade vazia")
	void deveAceitarCidadeVazia() {
		enderecoDTO.setCidade("");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar numero com valor S/N")
	void deveAceitarNumeroComValorSN() {
		enderecoDTO.setNumero("S/N");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar complemento com caracteres especiais")
	void deveAceitarComplementoComCaracteresEspeciais() {
		enderecoDTO.setComplemento("Bloco A - Apto 101/102");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve validar múltiplos erros simultaneamente")
	void deveValidarMultiplosErrosSimultaneamente() {
		enderecoDTO.setEstado("sp");
		enderecoDTO.setCep("12345");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertEquals(2, violations.size());
	}

	@Test
	@DisplayName("Deve criar EnderecoDTO completo com todos os campos")
	void deveCriarEnderecoDTOCompletoComTodosOsCampos() {
		enderecoDTO.setRua("Avenida Paulista");
		enderecoDTO.setNumero("1000");
		enderecoDTO.setComplemento("Torre A - 10º andar");
		enderecoDTO.setBairro("Bela Vista");
		enderecoDTO.setCidade("São Paulo");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("01310100");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());

		assertEquals("Avenida Paulista", enderecoDTO.getRua());
		assertEquals("1000", enderecoDTO.getNumero());
		assertEquals("Torre A - 10º andar", enderecoDTO.getComplemento());
		assertEquals("Bela Vista", enderecoDTO.getBairro());
		assertEquals("São Paulo", enderecoDTO.getCidade());
		assertEquals("SP", enderecoDTO.getEstado());
		assertEquals("01310100", enderecoDTO.getCep());
	}

	@Test
	@DisplayName("Deve aceitar EnderecoDTO sem nenhum campo preenchido")
	void deveAceitarEnderecoDTOSemNenhumCampoPreenchido() {
		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve falhar quando estado vazio string vazia")
	void deveFalharQuandoEstadoStringVazia() {
		enderecoDTO.setEstado("");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Estado deve ter 2 caracteres")));
	}

	@Test
	@DisplayName("Deve falhar quando CEP é string vazia")
	void deveFalharQuandoCepEhStringVazia() {
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("CEP deve conter 8 dígitos")));
	}

	@Test
	@DisplayName("Deve aceitar rua com caracteres especiais e acentuação")
	void deveAceitarRuaComCaracteresEspeciaisEAcentuacao() {
		enderecoDTO.setRua("Rua José de Alencar - 1ª edição");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar cidade com caracteres especiais e acentuação")
	void deveAceitarCidadeComCaracteresEspeciaisEAcentuacao() {
		enderecoDTO.setCidade("São José dos Campos");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("Deve aceitar bairro com caracteres especiais e acentuação")
	void deveAceitarBairroComCaracteresEspeciaisEAcentuacao() {
		enderecoDTO.setBairro("Jardim São Luís");
		enderecoDTO.setEstado("SP");
		enderecoDTO.setCep("12345678");

		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		assertTrue(violations.isEmpty());
	}
}
