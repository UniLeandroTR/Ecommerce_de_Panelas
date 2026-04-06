# 🛒 Ecommerce de Panelas para Restaurantes

API RESTful desenvolvida em Java com foco na gestão de um ecommerce especializado na venda de panelas para restaurantes.

> ⚙️ Projeto backend em desenvolvimento, com arquitetura preparada para escalabilidade, organização e boas práticas de engenharia de software.

---

## 🚀 Tecnologias Utilizadas

- **Java**
- **Quarkus** – Framework moderno, otimizado para alta performance
- **PostgreSQL** – Banco de dados relacional robusto
- **Hibernate ORM / JPA**
- **REST API (HTTP + JSON)**

---

## 📌 Objetivo do Projeto

Construir um sistema completo de ecommerce voltado para o setor gastronômico, permitindo:

- Gestão de produtos
- Controle de estoque
- Cadastro de clientes
- Processamento de pedidos
- Base sólida para futuras integrações (pagamentos, frontend, etc.)

---

## 🧱 Arquitetura

O projeto segue uma arquitetura em camadas:
- `┣ 📂 src`
  - `┣ 📂 main`
    - `┣ 📂 java`
      - `┣ 📂 leepans`
        - `┣ 📂 converter` - converter enum para a base de dados
        - `┣ 📂 dto` - padronizar os dados enviados em requests e responses da api
        - `┣ 📂 mapper` - transformar RequestDTO em Entidade e Entidade em ResponseDTO
        - `┣ 📂 model` - entidades e regras de negócio do projeto
        - `┣ 📂 repository` - classes de comunicação com a base de dados
        - `┣ 📂 resources` - recursos da api
        - `┣ 📂 service` - implementação das operações entre o repository e o resource

Separação clara de responsabilidades,
Baixo acoplamento e
Facilidade de manutenção e evolução

---

## 🔗 Endpoints (Em desenvolvimento)

- `GET:` cores | materiais | fundos | sustentacoes | tampas
- `GET:` cores/{id} | materiais/{id} | fundos/{id} | sustentacoes/{id} | tampas/{id}
- `POST:` cores | materiais | fundos | sustentacoes | tampas
- `PUT:` cores/{id} | materiais/{id} | fundos/{id} | sustentacoes/{id} | tampas/{id}
- `DELETE:` cores/{id} | materiais/{id} | fundos/{id} | sustentacoes/{id} | tampas/{id}

> Novos endpoints serão adicionados conforme evolução do projeto.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos

- Java 25+
- Maven
- PostgreSQL

### Passos:

```bash
# Clone o repositório
git clone https://github.com/UniLeandroTR/Ecommerce_de_Panelas.git

# Acesse o diretório
cd Ecommerce_de_Panelas
````
#### Configure do Banco de Dados
Configure o arquivo : `src/main/resources/application.properties` e altere as propriedades:
````
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=seu_usuario
quarkus.datasource.password=sua_senha
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/banco_de_dados
````
````bash
# Execute o projeto
./mvnw quarkus:dev
````
A api estará disponível em localhost:8080

---

## 📈 Roadmap
- [x] Estrutura inicial do projeto _`em andamento`_
- [ ] CRUD de produtos
- [ ] CRUD de clientes
- [ ] Sistema de pedidos
- [ ] Autenticação e autorização (JWT)
- [ ] Integração com frontend
- [ ] Deploy em ambiente cloud

---

## 📚 Aprendizados

Este projeto está sendo desenvolvido com foco em:

- Arquitetura de APIs REST
- Boas práticas com Java e Quarkus
- Modelagem de dados
- Organização de código profissional
- Preparação para projetos reais de mercado

---
## 🤝 Contribuição

Sinta-se à vontade para contribuir:

- Fork do projeto
- Crie uma branch (feature/sua-feature)
- Commit suas alterações
- Abra um Pull Request

---
## 👨‍💻 Autor
**[Leandro Tavares Rosendo](https://github.com/Rosendoxx/Rosendoxx)**