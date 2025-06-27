# 🕒 BateAi – Sistema de Registro de Ponto com Recompensas

**BateAi** é um sistema em desenvolvimento com o objetivo de facilitar o controle de ponto de funcionários por parte de empresas, além de promover o engajamento dos colaboradores por meio de recompensas e personalização.

## ✅ Funcionalidades atuais

- Cadastro de usuários do tipo **Coordenador** e **Funcionário**
- Armazenamento de usuários em banco de dados persistente (PostgreSQL)
- Criação automática da tabela `usuarios` com mapeamento por enum (`TipoUsuario`)

## 📁 Estrutura do Projeto

```
src/
└── main/
    ├── java/
    │   └── com/bateai/
    │       ├── controller/          # Controllers REST
    │       ├── dto/                 # DTOs para entrada de dados
    │       ├── entity/              # Entidades JPA
    │       ├── enums/               # Enumeração de tipos de usuário
    │       ├── repository/          # Repositórios JPA
    │       └── service/             # Lógica de negócio
    └── resources/
        └── application.properties   # Configurações do banco de dados
```

## 🧪 Endpoints disponíveis

| Método | Endpoint                       | Descrição                            |
|--------|--------------------------------|----------------------------------------|
| POST   | `/usuarios/cadastrar-coordenador` | Cadastra um novo coordenador |
| POST   | `/usuarios/cadastrar-funcionario` | Cadastra um novo funcionário |

Exemplo de payload para coordenador:

```json
{
  "nome": "João Coordenador",
  "email": "joao@empresa.com",
  "senha": "senha123"
}
```
Exemplo de payload para funcionario:

```json
{
  "nome": "Maria Funcionária",
  "email": "maria@empresa.com",
  "senha": "senha123",
  "coordenadorId": 1
}
```

## 🛠 Tecnologias utilizadas

- **Java**: Linguagem principal do projeto
- **Spring Boot (Maven)**: Framework para desenvolvimento de aplicações Java
- **PostgreSQL**: Banco de dados relacional utilizado para persistência
- **JPA/Hibernate**: Framework de mapeamento objeto-relacional
- **Lombok**: Biblioteca para redução de boilerplate em código Java
- **JUnit**: Framework para testes unitários
- **Mockito**: Biblioteca para criação de mocks em testes unitários
- **Swagger**: Documentação da API REST
- **Git**: Controle de versão do código-fonte

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