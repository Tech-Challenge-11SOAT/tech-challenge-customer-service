package br.com.postech.techchallange_customer.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnderecoTest {

	private Endereco endereco;

	@BeforeEach
	void setUp() {
		endereco = new Endereco();
	}

	@Test
	@DisplayName("Deve criar endereço com construtor vazio")
	void deveCriarEnderecoComConstrutorVazio() {
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
	@DisplayName("Deve criar endereço com construtor parametrizado")
	void deveCriarEnderecoComConstrutorParametrizado() {
		Endereco enderecoCompleto = new Endereco("Rua das Flores", "123", "São Paulo", "SP", "01234567");

		assertNotNull(enderecoCompleto);
		assertEquals("Rua das Flores", enderecoCompleto.getRua());
		assertEquals("123", enderecoCompleto.getNumero());
		assertEquals("São Paulo", enderecoCompleto.getCidade());
		assertEquals("SP", enderecoCompleto.getEstado());
		assertEquals("01234567", enderecoCompleto.getCep());
		assertNull(enderecoCompleto.getComplemento());
		assertNull(enderecoCompleto.getBairro());
	}

	@Test
	@DisplayName("Deve validar endereço completo como verdadeiro")
	void deveValidarEnderecoCompletoComoVerdadeiro() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		assertTrue(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando rua está vazia")
	void deveValidarEnderecoIncompletoQuandoRuaVazia() {
		endereco.setRua("");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando rua é nula")
	void deveValidarEnderecoIncompletoQuandoRuaNula() {
		endereco.setRua(null);
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando numero está vazio")
	void deveValidarEnderecoIncompletoQuandoNumeroVazio() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero("");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando numero é nulo")
	void deveValidarEnderecoIncompletoQuandoNumeroNulo() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero(null);
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando cidade está vazia")
	void deveValidarEnderecoIncompletoQuandoCidadeVazia() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("");
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando cidade é nula")
	void deveValidarEnderecoIncompletoQuandoCidadeNula() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade(null);
		endereco.setEstado("SP");
		endereco.setCep("01234567");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando estado está vazio")
	void deveValidarEnderecoIncompletoQuandoEstadoVazio() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("");
		endereco.setCep("01234567");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando estado é nulo")
	void deveValidarEnderecoIncompletoQuandoEstadoNulo() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado(null);
		endereco.setCep("01234567");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando CEP está vazio")
	void deveValidarEnderecoIncompletoQuandoCepVazio() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("");

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar endereço incompleto quando CEP é nulo")
	void deveValidarEnderecoIncompletoQuandoCepNulo() {
		endereco.setRua("Rua das Flores");
		endereco.setNumero("123");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep(null);

		assertFalse(endereco.isCompleto());
	}

	@Test
	@DisplayName("Deve validar CEP válido com 8 dígitos")
	void deveValidarCepValidoCom8Digitos() {
		endereco.setCep("01234567");
		assertTrue(endereco.isCepValido());
	}

	@Test
	@DisplayName("Deve validar CEP inválido com menos de 8 dígitos")
	void deveValidarCepInvalidoComMenos8Digitos() {
		endereco.setCep("0123456");
		assertFalse(endereco.isCepValido());
	}

	@Test
	@DisplayName("Deve validar CEP inválido com mais de 8 dígitos")
	void deveValidarCepInvalidoComMais8Digitos() {
		endereco.setCep("012345678");
		assertFalse(endereco.isCepValido());
	}

	@Test
	@DisplayName("Deve validar CEP inválido com caracteres não numéricos")
	void deveValidarCepInvalidoComCaracteresNaoNumericos() {
		endereco.setCep("0123456a");
		assertFalse(endereco.isCepValido());
	}

	@Test
	@DisplayName("Deve validar CEP inválido quando nulo")
	void deveValidarCepInvalidoQuandoNulo() {
		endereco.setCep(null);
		assertFalse(endereco.isCepValido());
	}

	@Test
	@DisplayName("Deve validar CEP inválido com formato formatado")
	void deveValidarCepInvalidoComFormatoFormatado() {
		endereco.setCep("01234-567");
		assertFalse(endereco.isCepValido());
	}

	@Test
	@DisplayName("Deve validar estado válido com 2 caracteres maiúsculos")
	void deveValidarEstadoValidoCom2CaracteresMaiusculos() {
		endereco.setEstado("SP");
		assertTrue(endereco.isEstadoValido());
	}

	@Test
	@DisplayName("Deve validar estado inválido com 1 caractere")
	void deveValidarEstadoInvalidoCom1Caractere() {
		endereco.setEstado("S");
		assertFalse(endereco.isEstadoValido());
	}

	@Test
	@DisplayName("Deve validar estado inválido com 3 caracteres")
	void deveValidarEstadoInvalidoCom3Caracteres() {
		endereco.setEstado("SPA");
		assertFalse(endereco.isEstadoValido());
	}

	@Test
	@DisplayName("Deve validar estado inválido com caracteres minúsculos")
	void deveValidarEstadoInvalidoComCaracteresMinusculos() {
		endereco.setEstado("sp");
		assertFalse(endereco.isEstadoValido());
	}

	@Test
	@DisplayName("Deve validar estado inválido com números")
	void deveValidarEstadoInvalidoComNumeros() {
		endereco.setEstado("S1");
		assertFalse(endereco.isEstadoValido());
	}

	@Test
	@DisplayName("Deve validar estado inválido quando nulo")
	void deveValidarEstadoInvalidoQuandoNulo() {
		endereco.setEstado(null);
		assertFalse(endereco.isEstadoValido());
	}

	@Test
	@DisplayName("Deve formatar CEP válido no padrão XXXXX-XXX")
	void deveFormatarCepValidoNoPadrao() {
		endereco.setCep("01234567");
		assertEquals("01234-567", endereco.formatarCep());
	}

	@Test
	@DisplayName("Deve retornar CEP sem formatação quando inválido")
	void deveRetornarCepSemFormatacaoQuandoInvalido() {
		endereco.setCep("0123456");
		assertEquals("0123456", endereco.formatarCep());
	}

	@Test
	@DisplayName("Deve retornar CEP sem formatação quando nulo")
	void deveRetornarCepSemFormatacaoQuandoNulo() {
		endereco.setCep(null);
		assertNull(endereco.formatarCep());
	}

	@Test
	@DisplayName("Deve retornar CEP sem formatação quando tem caracteres não numéricos")
	void deveRetornarCepSemFormatacaoQuandoTemCaracteresNaoNumericos() {
		endereco.setCep("0123456a");
		assertEquals("0123456a", endereco.formatarCep());
	}

	@Test
	@DisplayName("Deve setar e obter rua corretamente")
	void deveSetarEObterRuaCorretamente() {
		endereco.setRua("Rua das Flores");
		assertEquals("Rua das Flores", endereco.getRua());
	}

	@Test
	@DisplayName("Deve setar e obter numero corretamente")
	void deveSetarEObterNumeroCorretamente() {
		endereco.setNumero("123");
		assertEquals("123", endereco.getNumero());
	}

	@Test
	@DisplayName("Deve setar e obter complemento corretamente")
	void deveSetarEObterComplementoCorretamente() {
		endereco.setComplemento("Apto 45");
		assertEquals("Apto 45", endereco.getComplemento());
	}

	@Test
	@DisplayName("Deve setar e obter bairro corretamente")
	void deveSetarEObterBairroCorretamente() {
		endereco.setBairro("Centro");
		assertEquals("Centro", endereco.getBairro());
	}

	@Test
	@DisplayName("Deve setar e obter cidade corretamente")
	void deveSetarEObterCidadeCorretamente() {
		endereco.setCidade("São Paulo");
		assertEquals("São Paulo", endereco.getCidade());
	}

	@Test
	@DisplayName("Deve setar e obter estado corretamente")
	void deveSetarEObterEstadoCorretamente() {
		endereco.setEstado("SP");
		assertEquals("SP", endereco.getEstado());
	}

	@Test
	@DisplayName("Deve setar e obter CEP corretamente")
	void deveSetarEObterCepCorretamente() {
		endereco.setCep("01234567");
		assertEquals("01234567", endereco.getCep());
	}
}
