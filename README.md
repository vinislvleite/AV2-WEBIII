# AV2 - AutoManager

Microserviço Spring Boot para gerenciamento de clientes, documentos, endereços e telefones, utilizando persistência em banco de dados MySQL e navegação por hiperlinks (HATEOAS).

---

## Como executar o projeto

### 1. Clone o repositório

---

### 2. Configuração do banco de dados

#### 2.1 Configure o arquivo `application.properties`
Certifique-se de que as credenciais do seu banco de dados local estejam corretas:

~~~properties
spring.datasource.url=jdbc:mysql://localhost:3306/automanager?createDatabaseIfNotExist=true
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
~~~

* O banco de dados `automanager` será criado automaticamente na primeira execução, caso não exista.
* Sempre que você rodar novamente o banco será criado do ZERO, então lembre-se disso.

---

### 3. Execução da aplicação

Execute o Application através da sua IDE de preferência:
`src/main/java/com/autobots/automanager/AutomanagerApplication.java`

A API estará disponível localmente na porta padrão (8080).

---

### 4. Teste das rotas (HATEOAS)

Ferramentas recomendadas para teste: Insomnia ou Postman.
<br>
**Atenção:** Como esta API segue o Nível 3 de Maturidade de Richardson (Rotas Hierárquicas), o fluxo correto é **sempre criar o Cliente primeiro**. Os IDs do cliente e dos recursos devem ser passados diretamente na **URL**, e o JSON de resposta sempre conterá o campo de links para navegação.

---

# Cliente
Base URL: `/cliente`

### GET (Listar e Buscar)
* Listar todos: `GET localhost:8080/cliente`
* Buscar por ID: `GET localhost:8080/cliente/{id}`

### POST (Cadastrar)
`POST localhost:8080/cliente`

~~~json
{
  "nome": "Vinícius Leite",
  "nomeSocial": "Vini",
  "dataNascimento": "2000-01-01",
  "dataCadastro": "2026-04-18"
}
~~~

### PUT (Atualizar)
`PUT localhost:8080/cliente/{id}`

~~~json
{
  "nome": "Vinícius Leite Silva",
  "nomeSocial": "Vini"
}
~~~

### DELETE (Excluir)
`DELETE localhost:8080/cliente/{id}`

---

# Documento
Base URL: `/cliente/{clienteId}/documento`

### GET (Listar e Buscar)
* Listar do Cliente: `GET localhost:8080/cliente/{clienteId}/documento`
* Buscar por ID: `GET localhost:8080/cliente/{clienteId}/documento/{docId}`

### POST (Cadastrar)
`POST localhost:8080/cliente/{clienteId}/documento`

~~~json
{
  "tipo": "CPF",
  "numero": "12345678900"
}
~~~

### PUT (Atualizar)
`PUT localhost:8080/cliente/{clienteId}/documento/{docId}`

~~~json
{
  "tipo": "CPF",
  "numero": "99988877766"
}
~~~

### DELETE (Excluir)
`DELETE localhost:8080/cliente/{clienteId}/documento/{docId}`

---

# Endereço
Base URL: `/cliente/{clienteId}/endereco`

### GET (Buscar)
* Buscar Endereço do Cliente: `GET localhost:8080/cliente/{clienteId}/endereco`

### POST (Cadastrar)
`POST localhost:8080/cliente/{clienteId}/endereco`

~~~json
{
  "estado": "SP",
  "cidade": "São José dos Campos",
  "bairro": "Centro",
  "rua": "Rua das Flores",
  "numero": "500",
  "codigoPostal": "12200-000",
  "informacoesAdicionais": "Apartamento 42"
}
~~~

### PUT (Atualizar)
`PUT localhost:8080/cliente/{clienteId}/endereco`

~~~json
{
  "informacoesAdicionais": "Cobertura"
}
~~~

### DELETE (Excluir)
`DELETE localhost:8080/cliente/{clienteId}/endereco`

---

# Telefone
Base URL: `/cliente/{clienteId}/telefone`

### GET (Listar e Buscar)
* Listar do Cliente: `GET localhost:8080/cliente/{clienteId}/telefone`
* Buscar por ID: `GET localhost:8080/cliente/{clienteId}/telefone/{telId}`

### POST (Cadastrar)
`POST localhost:8080/cliente/{clienteId}/telefone`

~~~json
{
  "ddd": "12",
  "numero": "988887777"
}
~~~

### PUT (Atualizar)
`PUT localhost:8080/cliente/{clienteId}/telefone/{telId}`

~~~json
{
  "ddd": "12",
  "numero": "911112222"
}
~~~

### DELETE (Excluir)
`DELETE localhost:8080/cliente/{clienteId}/telefone/{telId}`

---

## Observações Finais
* O cadastro e a exclusão dependem da entidade Cliente. Excluir um Cliente removerá em cascata os seus respectivos documentos, telefones e endereços.