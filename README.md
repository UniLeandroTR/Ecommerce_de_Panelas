# Ecommerce de Panelas para Restaurantes

API REST em **Java** com **Quarkus**, voltada ao ecommerce B2B de panelas e utensílios para o setor gastronômico. O backend cobre catálogo, cadastro de clientes, pedidos, pagamentos, cupons, listas de desejo e operações administrativas, com autenticação JWT e controle de acesso por perfil.

---

## Tecnologias

| Tecnologia | Uso no projeto |
|------------|----------------|
| Java 25 | Linguagem e runtime |
| Quarkus 3.34 | Framework REST, injeção de dependência e build |
| PostgreSQL | Banco de dados em desenvolvimento/produção |
| Hibernate ORM / Panache | Persistência JPA |
| Redis | Cache (sessões e dados auxiliares) |
| SmallRye JWT | Autenticação e autorização |
| Argon2id | Hash de senhas (parâmetros configuráveis) |
| Quarkus Mailer | E-mails de recuperação e confirmação |
| Hibernate Validator | Validação de DTOs |
| SmallRye OpenAPI | Documentação interativa da API |
| Docker / Docker Compose | Redis local e imagens de deploy |
| JUnit 5 + REST Assured | Testes de integração dos resources |

---

## Funcionalidades

### Catálogo e produto

- **Panelas** (`/panelas`): produto principal, com visão ecommerce (público) e CRUD administrativo.
- **Componentes** do produto: cores, materiais, fundos, sustentações e tampas (gestão restrita a `ADMIN` e `FUNCIONARIO`).
- **Categorias** e **coleções** para organização do catálogo.
- **Fornecedores** para rastreio de origem dos itens.

### Clientes e conta

- Cadastro **simples** e **completo** de usuários.
- Perfis: `ADMIN`, `FUNCIONARIO` e `CLIENTE`.
- Edição de dados, endereços e senha.
- **Endereços** com rotas administrativas e consulta por usuário autenticado.

### Compras

- **Pedidos** (`/pedidos`): criação de compras, histórico do cliente e gestão administrativa (status, filtros por usuário, cidade etc.).
- **Pagamentos** (`/pagamentos`): processamento por cartão, boleto e PIX; consulta ecommerce e administração.
- **Cupons de desconto** com regras de ativação e aplicação por valor mínimo.
- **Listas de desejo** com inclusão e remoção de panelas.

### Autenticação (`/auth`)

- Login com retorno de token JWT.
- Informações do usuário autenticado (`/auth/info`).
- Fluxos de alteração e recuperação de senha com envio de e-mail.

### Qualidade e erros

- Respostas de erro padronizadas (`ProblemDetail`) com mappers para validação, autorização, conflito de versão (optimistic lock) etc.
- Dados iniciais via `import.sql` ao subir com `drop-and-create`.
- Suíte de **testes de integração** por resource (H2 em memória no perfil de teste).

---

## Arquitetura

Camadas em `src/main/java/leepans/`:

```
leepans/
├── converter/     # Enums ↔ colunas do banco
├── dto/           # Request, response e DTOs de ecommerce
├── exception/     # Exceções de domínio e mappers JAX-RS
├── mapper/        # Entidade ↔ DTO
├── model/         # Entidades JPA
├── repository/    # Acesso a dados (Panache)
├── resource/      # Endpoints REST
└── service/
    ├── auth/      # Login, cache, e-mail
    └── ecommerce/ # Regras de negócio do domínio
```

Separação de responsabilidades entre resource, service e repository, com DTOs na borda da API e regras concentradas nos services.

---

## API REST

Base URL em desenvolvimento: `http://localhost:8080`

| Prefixo | Domínio |
|---------|---------|
| `/auth` | Autenticação |
| `/usuarios` | Cadastro e gestão de usuários |
| `/panelas` | Produtos |
| `/categorias`, `/colecoes` | Organização do catálogo |
| `/cores`, `/materiais`, `/fundos`, `/sustentacoes`, `/tampas` | Componentes |
| `/fornecedores` | Fornecedores |
| `/enderecos` | Endereços |
| `/pedidos` | Pedidos |
| `/pagamentos` | Pagamentos |
| `/cupons-desconto` | Cupons |
| `/listas-desejo` | Listas de desejo |

Rotas administrativas costumam usar o sufixo `/admin` e exigem perfil `ADMIN` ou `FUNCIONARIO`, conforme o endpoint. Rotas de ecommerce e de cliente autenticado usam DTOs reduzidos e perfil `CLIENTE` quando aplicável.

Documentação interativa (com a aplicação em execução):

- Swagger UI: `http://localhost:8080/q/swagger-ui`
- OpenAPI: `http://localhost:8080/q/openapi`

Envie o token JWT no header `Authorization: Bearer <token>` nas rotas protegidas.

---

## Como executar

### Pré-requisitos

- JDK 25+
- Maven (ou use o wrapper `mvnw` / `mvnw.cmd`)
- PostgreSQL
- Redis (recomendado via Docker Compose)

### 1. Clonar o repositório

```bash
git clone https://github.com/UniLeandroTR/Ecommerce_de_Panelas.git
cd Ecommerce_de_Panelas
```

### 2. Variáveis de ambiente

Copie o arquivo de exemplo e ajuste os valores:

```bash
cp .env.example .env
```

Principais variáveis (ver `.env.example` para a lista completa):

- `DB_KIND`, `DB_USERNAME`, `DB_PASSWORD`, `DB_URL` — PostgreSQL
- `JWT_PUBLIC_KEY_LOCATION`, `JWT_SIGN_KEY_LOCATION`, `JWT_ISSUER` — JWT (chaves em `src/main/resources/META-INF/resources/`)
- `REDIS_HOSTS` — Redis
- `ARGON2_*` — parâmetros do hash de senha
- `MAILER_*` — SMTP para envio de e-mails

O `application.properties` referencia essas variáveis; não commite credenciais reais.

### 3. Subir o Redis (opcional, recomendado)

```bash
docker compose up -d
```

### 4. Executar em modo desenvolvimento

```bash
./mvnw quarkus:dev
```

No Windows:

```cmd
mvnw.cmd quarkus:dev
```

A API ficará disponível em `http://localhost:8080`.

> Em desenvolvimento, o Hibernate está configurado com `drop-and-create` e carrega dados de exemplo via `src/main/resources/import.sql`. Ajuste `quarkus.hibernate-orm.database.generation` em `application.properties` quando for usar um banco persistente.

---

## Testes

```bash
./mvnw test
```

Os testes usam H2 em memória e configuração em `src/test/resources/application.properties`, cobrindo os resources REST com JWT de teste.

---

## Docker

- `docker-compose.yml` — serviço Redis para desenvolvimento local.
- `src/main/docker/` — Dockerfiles JVM, legacy JAR e native para empacotamento da aplicação.

---

## Roadmap

- [x] Estrutura em camadas e domínio do ecommerce
- [x] CRUD de panelas, componentes, categorias, coleções e fornecedores
- [x] Usuários, endereços e autenticação JWT
- [x] Pedidos, pagamentos e cupons de desconto
- [x] Listas de desejo
- [x] Testes de integração dos resources
- [x] Documentação OpenAPI
- [ ] Integração com frontend
- [ ] Deploy em ambiente cloud
- [ ] Pipeline CI/CD

---

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch (`feature/sua-feature`)
3. Commit suas alterações
4. Abra um Pull Request

---

## Autor

**[Leandro Tavares Rosendo](https://github.com/Rosendoxx/Rosendoxx)**
