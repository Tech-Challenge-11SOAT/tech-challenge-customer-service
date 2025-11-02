use clientedb;

db.createCollection("clientes", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "clienteId",
        "nomeCliente",
        "emailCliente",
        "cpfCliente",
        "ativo",
        "dataCadastro",
        "dataUltimaAtualizacao"
      ],
      properties: {
        clienteId: {
          bsonType: "string",
          description: "UUID único do cliente - obrigatório",
          pattern: "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"
        },
        nomeCliente: {
          bsonType: "string",
          minLength: 3,
          maxLength: 100,
          description: "Nome completo do cliente - obrigatório"
        },
        emailCliente: {
          bsonType: "string",
          pattern: "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
          description: "E-mail válido - obrigatório"
        },
        cpfCliente: {
          bsonType: "string",
          pattern: "^[0-9]{11}$",
          description: "CPF com 11 dígitos numéricos - obrigatório"
        },
        telefone: {
          bsonType: "string",
          pattern: "^[0-9]{10,11}$",
          description: "Telefone com 10 ou 11 dígitos - opcional"
        },
        endereco: {
          bsonType: "object",
          description: "Endereço completo do cliente - opcional",
          properties: {
            rua: { 
              bsonType: "string",
              maxLength: 200
            },
            numero: { 
              bsonType: "string",
              maxLength: 20
            },
            complemento: { 
              bsonType: "string",
              maxLength: 100
            },
            bairro: { 
              bsonType: "string",
              maxLength: 100
            },
            cidade: { 
              bsonType: "string",
              maxLength: 100
            },
            estado: { 
              bsonType: "string",
              minLength: 2,
              maxLength: 2,
              description: "Sigla do estado (ex: SP, RJ)"
            },
            cep: { 
              bsonType: "string",
              pattern: "^[0-9]{8}$",
              description: "CEP com 8 dígitos"
            }
          }
        },
        ativo: {
          bsonType: "bool",
          description: "Status ativo/inativo do cliente - obrigatório"
        },
        dataCadastro: {
          bsonType: "date",
          description: "Data de cadastro do cliente - obrigatório"
        },
        dataUltimaAtualizacao: {
          bsonType: "date",
          description: "Data da última atualização - obrigatório"
        },
        versao: {
          bsonType: "int",
          minimum: 0,
          description: "Versão do documento para controle de concorrência otimista"
        },
        metadata: {
          bsonType: "object",
          description: "Metadados adicionais - opcional",
          properties: {
            origem: { 
              bsonType: "string",
              description: "Canal de origem (web, mobile, api, migracao, etc)"
            },
            canal: { 
              bsonType: "string",
              description: "Canal específico de cadastro"
            },
            tags: {
              bsonType: "array",
              items: { bsonType: "string" },
              description: "Tags para categorização do cliente"
            },
            notas: {
              bsonType: "string",
              maxLength: 500,
              description: "Observações adicionais"
            }
          }
        }
      }
    }
  },
  validationLevel: "strict",
  validationAction: "error"
});

print("✅ Collection 'clientes' criada com sucesso!");
print("📝 Validação de schema configurada");
print("");
