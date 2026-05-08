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
import leepans.model.Colecao;
import leepans.service.ecommerce.ColecaoService;

@QuarkusTest
public class ColecaoResourceHttpContractTest {

    private static final String BASE_URL = "/colecoes";

    @InjectMock
    ColecaoService service;

    @BeforeEach

    void setUp() {
        reset(service);
    }

    @Test
    void deveListarColecoesComStatus200() {
        when(service.findAll()).thenReturn(List.of(
                colecao(1L, "Primavera"),
                colecao(2L, "Outono")));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Primavera"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(service.findById(1L)).thenReturn(colecao(1L, "Primavera"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Primavera"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(service.findById(999L)).thenThrow(new NotFoundException("Colecao nao encontrada"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "funcionario", roles = { "FUNCIONARIO" })
    void deveCriarColecaoComStatus201() {
        when(service.create(any(Colecao.class)))
                .thenReturn(colecao(10L, "Verão"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Verão\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(10))
                .body("nome", equalTo("Verão"));
    }

    @Test
    void deveAtualizarColecaoComStatus204() {
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Nova Colecao\"}")
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    void deveRemoverColecaoComStatus204() {
        doNothing().when(service).delete(1L);

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

    private Colecao colecao(Long id, String nome) {
        Colecao c = new Colecao();
        c.setId(id);
        c.setNome(nome);
        return c;
    }
}
