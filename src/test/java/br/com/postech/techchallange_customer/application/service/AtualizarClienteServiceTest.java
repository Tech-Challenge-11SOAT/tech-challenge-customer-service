package br.com.postech.techchallange_customer.application.service;

import br.com.postech.techchallange_customer.domain.entity.Cliente;
import br.com.postech.techchallange_customer.domain.entity.Endereco;
import br.com.postech.techchallange_customer.domain.entity.Metadata;
import br.com.postech.techchallange_customer.domain.exception.ClienteNotFoundException;
import br.com.postech.techchallange_customer.domain.exception.InvalidClienteException;
import br.com.postech.techchallange_customer.domain.port.out.ClienteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AtualizarClienteService Tests")
class AtualizarClienteServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepository;

    private AtualizarClienteService service;

    @BeforeEach
    void setUp() {
        service = new AtualizarClienteService(clienteRepository);
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso com todos os campos")
    void deveAtualizarClienteComSucessoComTodosOsCampos() {
        String clienteId = "cliente-123";
        LocalDateTime dataAnterior = LocalDateTime.of(2024, 1, 1, 10, 0);
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId("id-123");
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Antigo");
        clienteExistente.setEmailCliente("antigo@example.com");
        clienteExistente.setCpfCliente("12345678901");
        clienteExistente.setTelefone("11999998888");
        clienteExistente.setAtivo(true);
        clienteExistente.setDataCadastro(dataAnterior);
        clienteExistente.setDataUltimaAtualizacao(dataAnterior);
        clienteExistente.setVersao(1);

        Endereco novoEndereco = new Endereco();
        novoEndereco.setRua("Rua Nova");
        novoEndereco.setNumero("456");
        novoEndereco.setCidade("São Paulo");
        novoEndereco.setEstado("SP");
        novoEndereco.setCep("12345678");

        Metadata novaMetadata = new Metadata();
        novaMetadata.setOrigem("Mobile");
        novaMetadata.setCanal("App");
        novaMetadata.setTags(Arrays.asList("premium"));

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");
        clienteAtualizado.setTelefone("11987654321");
        clienteAtualizado.setEndereco(novoEndereco);
        clienteAtualizado.setMetadata(novaMetadata);

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        assertNotNull(resultado);
        assertEquals("Nome Novo", resultado.getNomeCliente());
        assertEquals("novo@example.com", resultado.getEmailCliente());
        assertEquals("11987654321", resultado.getTelefone());
        assertEquals("12345678901", resultado.getCpfCliente()); // CPF não muda
        assertEquals(clienteId, resultado.getClienteId()); // ClienteId não muda
        assertNotNull(resultado.getEndereco());
        assertEquals("Rua Nova", resultado.getEndereco().getRua());
        assertNotNull(resultado.getMetadata());
        assertEquals("Mobile", resultado.getMetadata().getOrigem());

        verify(clienteRepository).findByClienteId(clienteId);
        verify(clienteRepository).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não for encontrado")
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        String clienteId = "cliente-inexistente";
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.empty());

        ClienteNotFoundException exception = assertThrows(
            ClienteNotFoundException.class,
            () -> service.execute(clienteId, clienteAtualizado)
        );

        assertTrue(exception.getMessage().contains(clienteId));
        verify(clienteRepository).findByClienteId(clienteId);
        verify(clienteRepository, never()).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente atualizado é null")
    void deveLancarExcecaoQuandoClienteAtualizadoEhNull() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));

        InvalidClienteException exception = assertThrows(
            InvalidClienteException.class,
            () -> service.execute(clienteId, null)
        );

        assertEquals("Cliente não pode ser nulo", exception.getMessage());
        verify(clienteRepository).findByClienteId(clienteId);
        verify(clienteRepository, never()).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome do cliente é null")
    void deveLancarExcecaoQuandoNomeDoClienteEhNull() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente(null);
        clienteAtualizado.setEmailCliente("novo@example.com");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));

        InvalidClienteException exception = assertThrows(
            InvalidClienteException.class,
            () -> service.execute(clienteId, clienteAtualizado)
        );

        assertTrue(exception.getMessage().contains("nome"));
        assertTrue(exception.getMessage().contains("não pode ser vazio"));
        verify(clienteRepository).findByClienteId(clienteId);
        verify(clienteRepository, never()).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome do cliente é vazio")
    void deveLancarExcecaoQuandoNomeDoClienteEhVazio() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("");
        clienteAtualizado.setEmailCliente("novo@example.com");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));

        InvalidClienteException exception = assertThrows(
            InvalidClienteException.class,
            () -> service.execute(clienteId, clienteAtualizado)
        );

        assertTrue(exception.getMessage().contains("nome"));
        assertTrue(exception.getMessage().contains("não pode ser vazio"));
        verify(clienteRepository, never()).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email do cliente é null")
    void deveLancarExcecaoQuandoEmailDoClienteEhNull() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente(null);

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));

        InvalidClienteException exception = assertThrows(
            InvalidClienteException.class,
            () -> service.execute(clienteId, clienteAtualizado)
        );

        assertTrue(exception.getMessage().contains("e-mail"));
        assertTrue(exception.getMessage().contains("não pode ser vazio"));
        verify(clienteRepository, never()).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email do cliente é vazio")
    void deveLancarExcecaoQuandoEmailDoClienteEhVazio() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));

        InvalidClienteException exception = assertThrows(
            InvalidClienteException.class,
            () -> service.execute(clienteId, clienteAtualizado)
        );

        assertTrue(exception.getMessage().contains("e-mail"));
        assertTrue(exception.getMessage().contains("não pode ser vazio"));
        verify(clienteRepository, never()).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email tem formato inválido")
    void deveLancarExcecaoQuandoEmailTemFormatoInvalido() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("email-invalido");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));

        InvalidClienteException exception = assertThrows(
            InvalidClienteException.class,
            () -> service.execute(clienteId, clienteAtualizado)
        );

        assertTrue(exception.getMessage().contains("e-mail"));
        assertTrue(exception.getMessage().contains("formato inválido"));
        verify(clienteRepository, never()).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve atualizar cliente sem endereco")
    void deveAtualizarClienteSemEndereco() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");
        clienteAtualizado.setEndereco(null);

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        assertNotNull(resultado);
        assertNull(resultado.getEndereco());
        verify(clienteRepository).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve atualizar cliente sem metadata")
    void deveAtualizarClienteSemMetadata() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");
        clienteAtualizado.setMetadata(null);

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        assertNotNull(resultado);
        assertNull(resultado.getMetadata());
        verify(clienteRepository).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve atualizar cliente sem telefone")
    void deveAtualizarClienteSemTelefone() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");
        clienteExistente.setTelefone("11999998888");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");
        clienteAtualizado.setTelefone(null);

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        assertNotNull(resultado);
        assertNull(resultado.getTelefone());
        verify(clienteRepository).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve chamar updateTimestamp ao atualizar cliente")
    void deveChamarUpdateTimestampAoAtualizarCliente() {
        String clienteId = "cliente-123";
        LocalDateTime dataAnterior = LocalDateTime.of(2024, 1, 1, 10, 0);
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");
        clienteExistente.setDataUltimaAtualizacao(dataAnterior);

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        assertNotNull(resultado.getDataUltimaAtualizacao());
        assertTrue(resultado.getDataUltimaAtualizacao().isAfter(dataAnterior));
        verify(clienteRepository).update(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve preservar dados não atualizáveis ao atualizar cliente")
    void devePreservarDadosNaoAtualizaveisAoAtualizarCliente() {
        String clienteId = "cliente-123";
        LocalDateTime dataCadastro = LocalDateTime.of(2024, 1, 1, 10, 0);
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId("id-original");
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");
        clienteExistente.setAtivo(true);
        clienteExistente.setDataCadastro(dataCadastro);
        clienteExistente.setVersao(5);

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        // Verificar que dados não atualizáveis foram preservados
        assertEquals("id-original", resultado.getId());
        assertEquals(clienteId, resultado.getClienteId());
        assertEquals("12345678901", resultado.getCpfCliente());
        assertTrue(resultado.getAtivo());
        assertEquals(dataCadastro, resultado.getDataCadastro());
        assertEquals(5, resultado.getVersao());
        
        // Verificar que dados atualizáveis foram alterados
        assertEquals("Nome Novo", resultado.getNomeCliente());
        assertEquals("novo@example.com", resultado.getEmailCliente());
    }

    @Test
    @DisplayName("Deve atualizar substituindo endereco existente")
    void deveAtualizarSubstituindoEnderecoExistente() {
        String clienteId = "cliente-123";
        
        Endereco enderecoAntigo = new Endereco();
        enderecoAntigo.setRua("Rua Antiga");
        enderecoAntigo.setNumero("100");
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");
        clienteExistente.setEndereco(enderecoAntigo);

        Endereco enderecoNovo = new Endereco();
        enderecoNovo.setRua("Rua Nova");
        enderecoNovo.setNumero("200");
        
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");
        clienteAtualizado.setEndereco(enderecoNovo);

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        assertNotNull(resultado.getEndereco());
        assertEquals("Rua Nova", resultado.getEndereco().getRua());
        assertEquals("200", resultado.getEndereco().getNumero());
    }

    @Test
    @DisplayName("Deve atualizar substituindo metadata existente")
    void deveAtualizarSubstituindoMetadataExistente() {
        String clienteId = "cliente-123";
        
        Metadata metadataAntiga = new Metadata();
        metadataAntiga.setOrigem("Web");
        metadataAntiga.setCanal("Online");
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");
        clienteExistente.setMetadata(metadataAntiga);

        Metadata metadataNova = new Metadata();
        metadataNova.setOrigem("Mobile");
        metadataNova.setCanal("App");
        
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");
        clienteAtualizado.setMetadata(metadataNova);

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        assertNotNull(resultado.getMetadata());
        assertEquals("Mobile", resultado.getMetadata().getOrigem());
        assertEquals("App", resultado.getMetadata().getCanal());
    }

    @Test
    @DisplayName("Deve passar cliente correto para repositório ao atualizar")
    void devePassarClienteCorretoParaRepositorioAoAtualizar() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("novo@example.com");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.execute(clienteId, clienteAtualizado);

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).update(clienteCaptor.capture());

        Cliente clientePassado = clienteCaptor.getValue();
        assertEquals("Nome Novo", clientePassado.getNomeCliente());
        assertEquals("novo@example.com", clientePassado.getEmailCliente());
        assertEquals(clienteId, clientePassado.getClienteId());
    }

    @Test
    @DisplayName("Deve aceitar email válido com caracteres especiais")
    void deveAceitarEmailValidoComCaracteresEspeciais() {
        String clienteId = "cliente-123";
        
        Cliente clienteExistente = new Cliente();
        clienteExistente.setClienteId(clienteId);
        clienteExistente.setNomeCliente("Nome Existente");
        clienteExistente.setEmailCliente("existente@example.com");
        clienteExistente.setCpfCliente("12345678901");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Nome Novo");
        clienteAtualizado.setEmailCliente("user.name+tag@example.com");

        when(clienteRepository.findByClienteId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.update(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.execute(clienteId, clienteAtualizado);

        assertNotNull(resultado);
        assertEquals("user.name+tag@example.com", resultado.getEmailCliente());
    }

    @Test
    @DisplayName("Deve criar instância do serviço com construtor")
    void deveCriarInstanciaDoServicoComConstrutor() {
        AtualizarClienteService novoService = new AtualizarClienteService(clienteRepository);
        assertNotNull(novoService);
    }
}
