package leepans.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;
import leepans.model.Fornecedor;
import leepans.service.ecommerce.FornecedorService;

@QuarkusTest
class FornecedorResourceTest {

    private static final String BASE = "/fornecedores";

    @InjectMock
    FornecedorService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void listar_deveRetornar200() {
        when(service.findAll()).thenReturn(List.of(fornecedor(1L)));

        given().accept(ContentType.JSON).when().get(BASE).then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].nome", equalTo("Fornecedor A"));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorId_deveRetornar200() {
        when(service.findById(1L)).thenReturn(fornecedor(1L));

        given().accept(ContentType.JSON).when().get(BASE + "/1").then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorId_deveRetornar404() {
        when(service.findById(99L)).thenThrow(new NotFoundException());

        given().accept(ContentType.JSON).when().get(BASE + "/99").then().statusCode(404);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorNome_deveRetornar200() {
        when(service.findByNome("FornecedorA")).thenReturn(List.of(fornecedor(1L)));

        given().accept(ContentType.JSON).when().get(BASE + "/nome/FornecedorA").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void criar_deveRetornar201() {
        when(service.create(any())).thenReturn(fornecedor(10L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Novo Fornecedor\",\"telefone\":\"63999999999\",\"cnpj\":\"12345678912345\"}")
                .when()
                .post(BASE)
                .then()
                .statusCode(201)
                .body("id", equalTo(10));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void atualizar_deveRetornar204() {
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Atualizado\",\"telefone\":\"63999999999\",\"cnpj\":\"12345678912345\",\"version\":1}")
                .when()
                .put(BASE + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin", roles = "ADMIN")
    void remover_deveRetornar204() {
        doNothing().when(service).delete(1L);

        given().when().delete(BASE + "/1").then().statusCode(204);
    }

    @Test
    void listar_deveRetornar401SemAutenticacao() {
        given().when().get(BASE).then().statusCode(401);
    }

    private static Fornecedor fornecedor(Long id) {
        Fornecedor f = new Fornecedor();
        f.setId(id);
        f.setNome("Fornecedor A");
        f.setTelefone("63999999999");
        f.setCnpj("12345678912345");
        return f;
    }
}
