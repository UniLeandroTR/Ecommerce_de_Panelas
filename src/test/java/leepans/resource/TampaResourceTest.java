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
import leepans.model.Tampa;
import leepans.service.ecommerce.TampaService;

@QuarkusTest
class TampaResourceTest {

    private static final String BASE = "/tampas";

    @InjectMock
    TampaService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void listar_deveRetornar200() {
        when(service.findAll()).thenReturn(List.of(tampa(1L)));

        given().accept(ContentType.JSON).when().get(BASE).then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].isDePressao", equalTo(true));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorId_deveRetornar200() {
        when(service.findById(1L)).thenReturn(tampa(1L));

        given().accept(ContentType.JSON).when().get(BASE + "/admin/1").then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorMaterial_deveRetornar200() {
        when(service.findByMaterial(1L)).thenReturn(List.of(tampa(1L)));

        given().accept(ContentType.JSON).when().get(BASE + "/admin/material/1").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void criar_deveRetornar201() {
        when(service.create(any())).thenReturn(tampa(2L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"peso\":0.5,\"idsMateriais\":[1],\"isDePressao\":true}")
                .when()
                .post(BASE)
                .then()
                .statusCode(201)
                .body("id", equalTo(2));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void atualizar_deveRetornar204() {
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .body("{\"peso\":0.6,\"idsMateriais\":[1],\"isDePressao\":false,\"version\":1}")
                .when()
                .put(BASE + "/admin/1")
                .then()
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin", roles = "ADMIN")
    void remover_deveRetornar204() {
        doNothing().when(service).delete(1L);

        given().when().delete(BASE + "/admin/1").then().statusCode(204);
    }

    @Test
    void listar_deveRetornar401SemAutenticacao() {
        given().when().get(BASE).then().statusCode(401);
    }

    private static Tampa tampa(Long id) {
        Tampa t = new Tampa();
        t.setId(id);
        t.setPeso(0.5);
        t.setIsDePressao(true);
        return t;
    }
}
