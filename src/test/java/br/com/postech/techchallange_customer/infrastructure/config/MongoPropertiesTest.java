package br.com.postech.techchallange_customer.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MongoPropertiesTest {

	private MongoProperties mongoProperties;

	@BeforeEach
	void setUp() {
		mongoProperties = new MongoProperties();
	}

	@Test
	@DisplayName("Deve criar MongoProperties com valores padrão")
	void deveCriarMongoPropertiesComValoresPadrao() {
		assertNotNull(mongoProperties);
		assertEquals("mongodb://localhost:27017/tc-customer", mongoProperties.getUri());
		assertEquals("tc-customer", mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve setar e obter URI corretamente")
	void deveSetarEObterUriCorretamente() {
		String novaUri = "mongodb://production-server:27017/customers";
		mongoProperties.setUri(novaUri);

		assertEquals(novaUri, mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve setar e obter database corretamente")
	void deveSetarEObterDatabaseCorretamente() {
		String novoDatabase = "production-customers";
		mongoProperties.setDatabase(novoDatabase);

		assertEquals(novoDatabase, mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve permitir URI com autenticação")
	void devePermitirUriComAutenticacao() {
		String uriComAuth = "mongodb://usuario:senha@servidor:27017/database";
		mongoProperties.setUri(uriComAuth);

		assertEquals(uriComAuth, mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir URI com múltiplos hosts")
	void devePermitirUriComMultiplosHosts() {
		String uriMultiplosHosts = "mongodb://host1:27017,host2:27017,host3:27017/database";
		mongoProperties.setUri(uriMultiplosHosts);

		assertEquals(uriMultiplosHosts, mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir URI com parâmetros de conexão")
	void devePermitirUriComParametrosDeConexao() {
		String uriComParametros = "mongodb://localhost:27017/database?retryWrites=true&w=majority";
		mongoProperties.setUri(uriComParametros);

		assertEquals(uriComParametros, mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir URI com MongoDB Atlas")
	void devePermitirUriComMongoDbAtlas() {
		String uriAtlas = "mongodb+srv://usuario:senha@cluster.mongodb.net/database";
		mongoProperties.setUri(uriAtlas);

		assertEquals(uriAtlas, mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir database com diferentes nomes")
	void devePermitirDatabaseComDiferentesNomes() {
		mongoProperties.setDatabase("test-db");
		assertEquals("test-db", mongoProperties.getDatabase());

		mongoProperties.setDatabase("production_db");
		assertEquals("production_db", mongoProperties.getDatabase());

		mongoProperties.setDatabase("dev");
		assertEquals("dev", mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve permitir URI nula")
	void devePermitirUriNula() {
		mongoProperties.setUri(null);
		assertNull(mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir database nulo")
	void devePermitirDatabaseNulo() {
		mongoProperties.setDatabase(null);
		assertNull(mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve permitir URI vazia")
	void devePermitirUriVazia() {
		mongoProperties.setUri("");
		assertEquals("", mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir database vazio")
	void devePermitirDatabaseVazio() {
		mongoProperties.setDatabase("");
		assertEquals("", mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve manter valores padrão após criação")
	void deveManterValoresPadraoAposCriacao() {
		MongoProperties properties1 = new MongoProperties();
		MongoProperties properties2 = new MongoProperties();

		assertEquals(properties1.getUri(), properties2.getUri());
		assertEquals(properties1.getDatabase(), properties2.getDatabase());
	}

	@Test
	@DisplayName("Deve permitir URI localhost na porta padrão")
	void devePermitirUriLocalhostNaPortaPadrao() {
		mongoProperties.setUri("mongodb://localhost:27017/mydb");
		assertEquals("mongodb://localhost:27017/mydb", mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir URI localhost em porta customizada")
	void devePermitirUriLocalhostEmPortaCustomizada() {
		mongoProperties.setUri("mongodb://localhost:27018/mydb");
		assertEquals("mongodb://localhost:27018/mydb", mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir URI com IP ao invés de hostname")
	void devePermitirUriComIpAoInvesDeHostname() {
		mongoProperties.setUri("mongodb://192.168.1.100:27017/database");
		assertEquals("mongodb://192.168.1.100:27017/database", mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir alterar URI múltiplas vezes")
	void devePermitirAlterarUriMultiplasVezes() {
		mongoProperties.setUri("mongodb://host1:27017/db1");
		assertEquals("mongodb://host1:27017/db1", mongoProperties.getUri());

		mongoProperties.setUri("mongodb://host2:27017/db2");
		assertEquals("mongodb://host2:27017/db2", mongoProperties.getUri());

		mongoProperties.setUri("mongodb://host3:27017/db3");
		assertEquals("mongodb://host3:27017/db3", mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve permitir alterar database múltiplas vezes")
	void devePermitirAlterarDatabaseMultiplasVezes() {
		mongoProperties.setDatabase("db1");
		assertEquals("db1", mongoProperties.getDatabase());

		mongoProperties.setDatabase("db2");
		assertEquals("db2", mongoProperties.getDatabase());

		mongoProperties.setDatabase("db3");
		assertEquals("db3", mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve manter URI padrão quando não configurado")
	void deveManterUriPadraoQuandoNaoConfigurado() {
		assertEquals("mongodb://localhost:27017/tc-customer", mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve manter database padrão quando não configurado")
	void deveManterDatabasePadraoQuandoNaoConfigurado() {
		assertEquals("tc-customer", mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve permitir configurar URI e database independentemente")
	void devePermitirConfigurarUriEDatabaseIndependentemente() {
		mongoProperties.setUri("mongodb://newhost:27017/db");
		assertEquals("mongodb://newhost:27017/db", mongoProperties.getUri());
		assertEquals("tc-customer", mongoProperties.getDatabase());

		mongoProperties.setDatabase("newdb");
		assertEquals("mongodb://newhost:27017/db", mongoProperties.getUri());
		assertEquals("newdb", mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve permitir database com caracteres especiais")
	void devePermitirDatabaseComCaracteresEspeciais() {
		mongoProperties.setDatabase("db-test_2024");
		assertEquals("db-test_2024", mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve permitir URI com caracteres especiais codificados")
	void devePermitirUriComCaracteresEspeciaisCodificados() {
		String uriComCaracteresEspeciais = "mongodb://user%40name:p%40ssw0rd@host:27017/database";
		mongoProperties.setUri(uriComCaracteresEspeciais);

		assertEquals(uriComCaracteresEspeciais, mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve retornar valores setados corretamente")
	void deveRetornarValoresSetadosCorretamente() {
		String uri = "mongodb://testhost:27017/testdb";
		String database = "testdb";

		mongoProperties.setUri(uri);
		mongoProperties.setDatabase(database);

		assertEquals(uri, mongoProperties.getUri());
		assertEquals(database, mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve ser possível criar múltiplas instâncias independentes")
	void deveSerPossivelCriarMultiplasInstanciasIndependentes() {
		MongoProperties properties1 = new MongoProperties();
		MongoProperties properties2 = new MongoProperties();

		properties1.setUri("mongodb://host1:27017/db1");
		properties1.setDatabase("db1");

		properties2.setUri("mongodb://host2:27017/db2");
		properties2.setDatabase("db2");

		assertEquals("mongodb://host1:27017/db1", properties1.getUri());
		assertEquals("db1", properties1.getDatabase());
		assertEquals("mongodb://host2:27017/db2", properties2.getUri());
		assertEquals("db2", properties2.getDatabase());
	}

	@Test
	@DisplayName("Deve aceitar URI com protocolo mongodb+srv")
	void deveAceitarUriComProtocoloMongodbSrv() {
		String uriSrv = "mongodb+srv://cluster.example.com/database";
		mongoProperties.setUri(uriSrv);

		assertEquals(uriSrv, mongoProperties.getUri());
	}

	@Test
	@DisplayName("Deve aceitar database com números")
	void deveAceitarDatabaseComNumeros() {
		mongoProperties.setDatabase("database123");
		assertEquals("database123", mongoProperties.getDatabase());
	}

	@Test
	@DisplayName("Deve aceitar URI com replica set")
	void deveAceitarUriComReplicaSet() {
		String uriReplicaSet = "mongodb://host1:27017,host2:27017/database?replicaSet=rs0";
		mongoProperties.setUri(uriReplicaSet);

		assertEquals(uriReplicaSet, mongoProperties.getUri());
	}
}
