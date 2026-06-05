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
import leepans.model.Cor;
import leepans.service.ecommerce.CorService;

@QuarkusTest
class CorResourceTest {

    private static final String ADMIN = "/cores/admin";

    @InjectMock
    CorService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void listar_deveRetornar200() {
        when(service.findAll()).thenReturn(List.of(cor(1L, "Vermelho")));

        given().accept(ContentType.JSON).when().get(ADMIN).then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].nome", equalTo("Vermelho"));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorId_deveRetornar200() {
        when(service.findById(1L)).thenReturn(cor(1L, "Vermelho"));

        given().accept(ContentType.JSON).when().get(ADMIN + "/1").then()
                .statusCode(200)
                .body("nome", equalTo("Vermelho"));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorNome_deveRetornar200() {
        when(service.findByNome("Vermelho")).thenReturn(List.of(cor(1L, "Vermelho")));

        given().accept(ContentType.JSON).when().get(ADMIN + "/nome/Vermelho").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void criar_deveRetornar201() {
        when(service.create(any())).thenReturn(cor(2L, "Azul"));

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Azul\"}")
                .when()
                .post(ADMIN)
                .then()
                .statusCode(201)
                .body("id", equalTo(2));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void atualizar_deveRetornar204() {
        when(service.findById(1L)).thenReturn(cor(1L, "Vermelho"));
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Verde\",\"version\":1}")
                .when()
                .put(ADMIN + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin", roles = "ADMIN")
    void remover_deveRetornar204() {
        doNothing().when(service).delete(1L);

        given().when().delete(ADMIN + "/1").then().statusCode(204);
    }

    private static Cor cor(Long id, String nome) {
        Cor c = new Cor();
        c.setId(id);
        c.setNome(nome);
        return c;
    }
}
