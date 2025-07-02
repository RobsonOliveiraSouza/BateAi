# 🕒 BateAi – Sistema de Registro de Ponto com Recompensas

**BateAi** é um sistema em desenvolvimento com o objetivo de facilitar o controle de ponto de funcionários por parte de empresas, além de promover o engajamento dos colaboradores por meio de recompensas e personalização.

## ✅ Funcionalidades atuais

- Cadastro de usuários do tipo **Coordenador** e **Colaborador**
- Cadastro de empresas
- Armazenamento de usuários em banco de dados persistente (PostgreSQL)
- Registro de ponto para **ENTRADA** e **SAIDA**
- Criação automática da tabela `usuarios` com mapeamento por enum (`TipoUsuario`)
- Busca de colaboradores pendentes para aprovação
- Aprovação de vinculo entre coordenador e colaborador
- Visualização de dashboard com informações dos usuários e suas situações

## 📁 Estrutura do Projeto

```
src/
└── main/
    ├── java/
    │   └── com/bateai/
    │       ├── config/              # Configurações
    │       ├── controller/          # Controllers REST
    │       ├── dto/                 # DTOs para entrada de dados
    │       ├── entity/              # Entidades JPA
    │           └── enums/           # Enumerações de tipos auxiliares
    │       ├── repository/          # Repositórios JPA
    │       └── service/             # Lógica de negócio
    └── resources/
        └── application.properties   # Configurações do banco de dados
```

## 🧪 Endpoints disponíveis

| Método | Endpoint                             | Descrição                                   |
|--------|--------------------------------------|---------------------------------------------|
| POST   | `/empresas/cadastrar`                | Cadastra uma nova empresa                   |
| POST   | `/usuarios/cadastrar-coordenador`    | Cadastra um novo coordenador                |
| POST   | `/usuarios/cadastrar-colaborador`    | Cadastra um novo colaborador                |
| GET    | `/usuarios/{id}`                     | Busca um usuário pelo ID                    |
| GET    | `/usuarios/pendentes?empresaId={id}` | Busca usuários pendentes (ID da Empresa)    |
| GET    | `/usuarios/dashboard?empresaId={id}` | Lista todos usuários e suas situações       |
| PUT    | `/usuarios/aprovar-vinculo/{id}`     | Coordenador aprova o vinculo do colaborador |
| PUT    | `/usuarios/alterar-senha/{id}`       | Altera a senha de um usuário                |
| DELETE | `/usuarios/{id}`                     | Remove um usuário pelo ID                   |
| POST   | `/ponto/registrar`                   | Registra o ponto batido                     |


## 📄 Exemplos de Payloads

Exemplo de payload para empresa:

```json
{
  "cnpj": "12345678000199",
  "nomeFantasia": "BateAi Ltda",
  "razaoSocial": "BateAi Sistemas Inteligentes",
  "endereco": "Av. das Empresas, 1000",
  "emailResponsavel": "admin@bateai.com"
}
```

Exemplo de payload para cadastro de coordenador:

```json
{
  "nome": "João Coordenador",
  "email": "joao@bateai.com",
  "senha": "senha123",
  "cpf": "12345678901",
  "telefone": "11999998888",
  "empresaId": 1
}
```
Exemplo de payload para cadastro de colaborador:

```json
{
  "nome": "Maria Colaboradora",
  "email": "maria@bateai.com",
  "senha": "senha123",
  "cpf": "98765432100",
  "telefone": "44988889999",
  "setor": "Financeiro",
  "empresaId": 1
}
```
Exemplo de payload para cadastro de ponto:

```json
{
  "colaboradorId": 2,
  "tipoRegistro": "ENTRADA",
  "localizacao": "Av. Brasil, 123"
}
```

Response do payload de cadastro de ponto (ponto batido):
```json
{
  "id": 2,
  "dataHora": "2025-07-01T15:24:48.946016761",
  "tipoRegistro": "ENTRADA",
  "localizacao": "Av. Brasil, 123",
  "colaborador": {
    "id": 2,
    "nome": "Maria Colaboradora",
    "email": "maria@bateai.com"
  }
}
```

## 🛠 Tecnologias utilizadas

- **Java**: Linguagem principal do projeto (Versão: 21)
- **Spring Boot (Maven)**: Framework para desenvolvimento de aplicações Java
- **PostgreSQL**: Banco de dados relacional utilizado para persistência
- **JPA/Hibernate**: Framework de mapeamento objeto-relacional
- **Lombok**: Biblioteca para redução de boilerplate em código Java
- **JUnit**: Framework para testes unitários
- **Mockito**: Biblioteca para criação de mocks em testes unitários
- **Swagger**: Documentação da API REST
- **Git**: Controle de versionamento do código-fonte

## ⚙️ Como executar

1. Certifique-se de que o PostgreSQL está rodando e o banco `bateai` foi criado.
2. Configure o arquivo `application.properties` com os dados do banco:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bateai
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. No terminal, execute o projeto com:

```bash
./mvnw spring-boot:run
```

4. Acesse: [http://localhost:8080](http://localhost:8080)

## 🚧 Em desenvolvimento

- Registro de ponto com geolocalização
- Relatórios de jornada e banco de horas
- Sistema de recompensas e personalização
- Autenticação e segurança com Spring Security
- Interface frontend

## 🤝 Contribuições

Este é um projeto acadêmico desenvolvido para a disciplina:   
**Gerenciamento de Projetos de Software**  –  Universidade Estadual de Maringá.

## 👤 Autor

Gabriel Soares Sossai  
Robson Oliveira Souza  
Vitor Fernando Regis