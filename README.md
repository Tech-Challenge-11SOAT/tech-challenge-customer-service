# 🏗️ Tech Challenge - Customer Microservice

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green)](https://www.mongodb.com/)
[![Hexagonal Architecture](https://img.shields.io/badge/Architecture-Hexagonal-blue)](https://alistair.cockburn.us/hexagonal-architecture/)

Microserviço de gerenciamento de clientes desenvolvido com **Arquitetura Hexagonal** completa, utilizando Spring Boot, Java 17 e MongoDB.

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Arquitetura](#-arquitetura)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Execução](#-instalação-e-execução)
- [Documentação da API](#-documentação-da-api)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Testes](#-testes)
- [Documentação Adicional](#-documentação-adicional)

---

## 🎯 Visão Geral

Este microserviço implementa um sistema completo de gerenciamento de clientes com as seguintes funcionalidades:

✅ Criar, atualizar, buscar e listar clientes  
✅ Soft delete (desativação) e reativação de clientes  
✅ Busca por CPF, e-mail, cidade e tags  
✅ Validações completas (CPF, e-mail, telefone, CEP)  
✅ Suporte a metadata e tags (VIP, Premium, etc.)  
✅ Versionamento otimista com MongoDB  
✅ API REST documentada com OpenAPI/Swagger  
✅ Tratamento global de exceções  

---

## 🏛️ Arquitetura

Este projeto segue os princípios da **Arquitetura Hexagonal** (Ports and Adapters):

```
┌─────────────────────────────────────────┐
│        Infrastructure Layer             │
│  (REST API, MongoDB, Configs)           │
│                                         │
│  ┌───────────────────────────────────┐  │
│  │     Application Layer             │  │
│  │  (Use Cases, DTOs, Mappers)       │  │
│  │                                   │  │
│  │  ┌─────────────────────────────┐  │  │
│  │  │      Domain Layer           │  │  │
│  │  │   (Entities, Rules, Ports)  │  │  │
│  │  │   *** CORE - SEM DEPS ***   │  │  │
│  │  └─────────────────────────────┘  │  │
│  └───────────────────────────────────┘  │
└─────────────────────────────────────────┘
```

### Camadas

#### **Domain (Núcleo)**
- Entidades puras: `Cliente`, `Endereco`, `Metadata`
- Regras de negócio: validações, comportamentos
- Portas (interfaces): `CriarClienteUseCase`, `ClienteRepositoryPort`
- **Zero dependências externas**

#### **Application (Casos de Uso)**
- Implementação dos Use Cases
- Orquestração de operações
- DTOs e Mappers

#### **Infrastructure (Adaptadores)**
- REST API (Controllers)
- Persistência MongoDB
- Configurações Spring

📚 **[Documentação Completa da Arquitetura](ARCHITECTURE.md)**  
📊 **[Diagrama Visual](ARCHITECTURE_DIAGRAM.md)**

---

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data MongoDB**
- **MongoDB 7.0**
- **Spring Validation**
- **SpringDoc OpenAPI 3** (Swagger)
- **Lombok**
- **Maven**

---

## 📦 Pré-requisitos

- Java 17+
- Maven 3.8+
- MongoDB 7.0+ (ou Docker)

---

## 🚀 Instalação e Execução

### 1. Clonar o repositório

```bash
git clone https://github.com/Tech-Challenge-11SOAT/tech-challenge-customer-service.git
cd tech-challenge-customer-service
```

### 2. Iniciar MongoDB com Docker

```bash
docker run -d \
  --name customer-mongodb \
  -p 27017:27017 \
  -e MONGO_INITDB_DATABASE=customer_db \
  mongo:7.0
```

Ou use o Docker Compose incluído:

```bash
docker-compose up -d
```

### 3. Configurar aplicação

Edite `src/main/resources/application.yml`:

```yaml
spring:
  data:
    mongodb:
      host: localhost
      port: 27017
      database: customer_db
```

### 4. Compilar e executar

```bash
# Compilar
./mvnw clean package

# Executar
./mvnw spring-boot:run
```

### 5. Acessar a aplicação

- **API Base**: `http://localhost:8080/api/v1/clientes`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

---

## 📖 Documentação da API

### Endpoints Principais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/clientes` | Criar novo cliente |
| GET | `/api/v1/clientes/{id}` | Buscar por ID |
| GET | `/api/v1/clientes/cpf/{cpf}` | Buscar por CPF |
| GET | `/api/v1/clientes/email/{email}` | Buscar por e-mail |
| GET | `/api/v1/clientes` | Listar todos |
| GET | `/api/v1/clientes/ativos` | Listar ativos |
| GET | `/api/v1/clientes/cidade/{cidade}` | Buscar por cidade |
| GET | `/api/v1/clientes/vip` | Buscar VIPs |
| PUT | `/api/v1/clientes/{id}` | Atualizar cliente |
| PATCH | `/api/v1/clientes/{id}/desativar` | Desativar (soft delete) |
| PATCH | `/api/v1/clientes/{id}/reativar` | Reativar |
| DELETE | `/api/v1/clientes/{id}` | Deletar permanentemente |

### Exemplo de Requisição

```bash
curl -X POST http://localhost:8080/api/v1/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nomeCliente": "João Silva",
    "emailCliente": "joao@email.com",
    "cpfCliente": "12345678901",
    "telefone": "11987654321",
    "endereco": {
      "rua": "Rua das Flores",
      "numero": "123",
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "01234567"
    }
  }'
```

📚 **[Exemplos Completos da API](API_EXAMPLES.md)**

---

## 📁 Estrutura do Projeto

```
src/main/java/br/com/postech/techchallange_customer/
│
├── domain/                          # 🎯 DOMÍNIO (Core)
│   ├── entity/                      # Entidades puras
│   ├── exception/                   # Exceções de domínio
│   └── port/                        # Interfaces (Portas)
│       ├── in/                      # Use Cases (inbound)
│       └── out/                     # Repository (outbound)
│
├── application/                     # 📦 APLICAÇÃO
│   ├── service/                     # Implementação Use Cases
│   ├── dto/                         # Data Transfer Objects
│   └── mapper/                      # Conversores DTO ↔ Domain
│
└── infrastructure/                  # 🔌 INFRAESTRUTURA
    ├── persistence/                 # MongoDB
    │   ├── adapter/                 # Implementa ports
    │   ├── document/                # Documentos MongoDB
    │   ├── mapper/                  # Domain ↔ Document
    │   └── repository/              # Spring Data
    ├── rest/                        # API REST
    │   ├── adapter/                 # Controllers
    │   └── exception/               # Exception Handlers
    └── config/                      # Configurações Spring
```

---

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test

# Testes unitários apenas
./mvnw test -Dtest=**/*Test

# Testes de integração
./mvnw test -Dtest=**/*IT

# Com relatório de cobertura
./mvnw test jacoco:report
```

### Estrutura de Testes

- **Domain**: Testes unitários de entidades e regras de negócio
- **Application**: Testes de Use Cases com mocks
- **Infrastructure**: Testes de integração com MongoDB e API REST

📚 **[Estratégia Completa de Testes](TESTING_STRATEGY.md)**

---

## 📚 Documentação Adicional

- **[ARCHITECTURE.md](ARCHITECTURE.md)** - Documentação detalhada da arquitetura hexagonal
- **[ARCHITECTURE_DIAGRAM.md](ARCHITECTURE_DIAGRAM.md)** - Diagramas visuais da arquitetura
- **[API_EXAMPLES.md](API_EXAMPLES.md)** - Exemplos de uso da API com cURL
- **[TESTING_STRATEGY.md](TESTING_STRATEGY.md)** - Estratégia e exemplos de testes

---

## 🎨 Princípios Seguidos

✅ **SOLID**: Princípios de design orientado a objetos  
✅ **Clean Architecture**: Separação clara de responsabilidades  
✅ **Hexagonal Architecture**: Independência de frameworks  
✅ **Domain-Driven Design**: Lógica de negócio no domínio  
✅ **Dependency Inversion**: Dependências apontam para o núcleo  
✅ **Single Responsibility**: Uma responsabilidade por classe  
✅ **Open/Closed**: Aberto para extensão, fechado para modificação  

---

## 🔍 Validações Implementadas

- **CPF**: Exatamente 11 dígitos numéricos
- **E-mail**: Formato RFC 5322
- **Telefone**: 10 ou 11 dígitos
- **CEP**: Exatamente 8 dígitos
- **Estado**: 2 caracteres maiúsculos (UF)
- **Unicidade**: CPF e e-mail únicos no sistema

---

## 🛡️ Recursos de Segurança

- Soft delete (dados não são perdidos)
- Versionamento otimista (evita conflitos de concorrência)
- Validação em múltiplas camadas
- Tratamento global de exceções
- Logs estruturados

---

## 🌟 Diferenciais da Implementação

✨ **Arquitetura Hexagonal Completa**
- Domínio 100% independente de frameworks
- Portas e adaptadores bem definidos
- Facilita testes e manutenção

✨ **Código Limpo e Organizado**
- Nomenclatura clara e consistente
- Responsabilidades bem divididas
- Fácil de entender e evoluir

✨ **Documentação Abrangente**
- Swagger/OpenAPI integrado
- Documentação técnica detalhada
- Exemplos práticos de uso

✨ **Pronto para Produção**
- Exception handling robusto
- Logs apropriados
- Validações completas
- Versionamento de dados

---

## 👥 Autores

**Tech Challenge - Grupo 11SOAT**  
Pós-Graduação em Arquitetura de Software - FIAP

---

## 📄 Licença

Este projeto é parte do Tech Challenge da POSTECH FIAP.

---

## 🤝 Contribuindo

Este é um projeto acadêmico. Sugestões e melhorias são bem-vindas através de issues ou pull requests.

---

## 📞 Suporte

Para dúvidas ou problemas, abra uma issue no repositório.

---

**Desenvolvido com ❤️ para o Tech Challenge POSTECH FIAP**
