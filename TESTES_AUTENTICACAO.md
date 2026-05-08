# Correção de Testes com Autenticação e Autorização

## Resumo das Mudanças

Após a implementação de autenticação JWT e autorização com `@RolesAllowed`, os testes dos resources foram atualizados para funcionar corretamente com o novo sistema de segurança.

## Alterações Principais

### 1. **Dependência Adicionada** (`pom.xml`)
```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-test-security-jwt</artifactId>
    <scope>test</scope>
</dependency>
```

Esta dependência fornece a anotação `@TestSecurity` que permite simular usuários autenticados com roles específicas em testes.

### 2. **Importação Adicionada** (CategoriaResourceHttpContractTest.java)
```java
import io.quarkus.test.security.TestSecurity;
```

### 3. **Testes Atualizados**

#### Testes com Autenticação Obrigatória
Endpoints que requerem `@RolesAllowed({ "FUNCIONARIO", "ADMIN" })` agora usam `@TestSecurity`:

```java
@Test
@TestSecurity(user = "funcionario", roles = { "FUNCIONARIO" })
void deveCriarCategoriaComStatus201() {
    // Teste com usuário autenticado como FUNCIONARIO
}

@Test
@TestSecurity(user = "funcionario", roles = { "FUNCIONARIO" })
void deveAtualizarCategoriaComStatus204() {
    // Teste com usuário autenticado como FUNCIONARIO
}
```

#### Testes com Role Específica
Endpoints que requerem `@RolesAllowed({ "ADMIN" })` usam role ADMIN:

```java
@Test
@TestSecurity(user = "admin", roles = { "ADMIN" })
void deveRemoverCategoriaComStatus204() {
    // Teste com usuário autenticado como ADMIN
}
```

#### Testes de Segurança (Sem Autenticação)
```java
@Test
void deveRetornar401QuandoTentarCriarSemAutenticacao() {
    // Espera 401 (Unauthorized) quando não há token JWT
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("{\"tipo\":\"Panelas\"}")
    .when()
        .post(BASE_URL)
    .then()
        .statusCode(401);
}
```

#### Testes de Autorização (Role Insuficiente)
```java
@Test
@TestSecurity(user = "cliente", roles = { "CLIENTE" })
void deveRetornar403QuandoTentarDeletarComRoleInsuficiente() {
    // Espera 403 (Forbidden) quando role não é suficiente
    given()
        .accept(ContentType.JSON)
    .when()
        .delete(BASE_URL + "/1")
    .then()
        .statusCode(403);
}
```

## Códigos de Status HTTP

| Status | Significado | Cenário |
|--------|-------------|---------|
| **200** | OK | GET sem restrição, sucesso geral |
| **201** | Created | POST bem-sucedido |
| **204** | No Content | PUT/DELETE bem-sucedido |
| **400** | Bad Request | JSON malformado |
| **401** | Unauthorized | Sem token JWT ou token inválido |
| **403** | Forbidden | Token válido mas role insuficiente |
| **404** | Not Found | Recurso não encontrado |
| **406** | Not Acceptable | Accept header não suportado |
| **415** | Unsupported Media Type | Content-Type não suportado |
| **422** | Unprocessable Entity | Validação falhou |

## Padrão para Testes com Segurança

### Template para Novos Testes Protegidos

```java
@Test
@TestSecurity(user = "username", roles = { "ROLE_NAME" })
void deveTestarOperacaoProtegida() {
    // Mock do serviço
    when(service.findById(1L)).thenReturn(entity);

    // Requisição com autenticação
    given()
        .accept(ContentType.JSON)
    .when()
        .get(BASE_URL + "/1")
    .then()
        .statusCode(200)
        .body("id", equalTo(1));
}
```

### Testes Sem Restrição
Endpoints que não têm `@RolesAllowed` funcionam normalmente sem anotação:

```java
@Test
void deveListarCategoriasComStatus200() {
    when(service.findAll()).thenReturn(List.of(/*...*/));

    given()
        .accept(ContentType.JSON)
    .when()
        .get(BASE_URL)
    .then()
        .statusCode(200);
}
```

## Aplicando em Outros Testes

Para corrigir todos os testes de resources, aplique o mesmo padrão:

1. **Adicione a anotação `@TestSecurity`** aos testes de POST, PUT, DELETE
2. **Especifique o user e roles** conforme exigido pelo endpoint
3. **Altere testes de validação** para usar 401 (sem autenticação) ou 403 (role insuficiente)
4. **Mantenha GET sem restrição** conforme não exigir autenticação

### Exemplo Prático
Se um endpoint tem:
```java
@RolesAllowed({ "ADMIN", "FUNCIONARIO" })
public Response create(@Valid DTO dto) { ... }
```

O teste deve ser:
```java
@Test
@TestSecurity(user = "admin", roles = { "ADMIN" })  // ou "FUNCIONARIO"
void deveCriarComStatus201() { ... }
```

## Validação

Para validar que todos os testes passam:

```bash
mvn test -Dtest=CategoriaResourceHttpContractTest
```

Esperado: `BUILD SUCCESS` com todos os testes passando.

## Referências

- [Quarkus Testing with JWT](https://quarkus.io/guides/security-testing)
- [TestSecurity Annotation](https://quarkus.io/guides/security-testing#testing-security-with-testsecurity)
- [RFC 7807 - Problem Details](https://tools.ietf.org/html/rfc7807)
