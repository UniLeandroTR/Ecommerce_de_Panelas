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
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;
import leepans.model.Cor;
import leepans.service.ecommerce.CorService;

@QuarkusTest
public class CorResourceHttpContractTest {

    private static final String BASE_URL = "/cores";

    @InjectMock
    CorService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void deveListarCoresComStatus200() {
        when(service.findAll()).thenReturn(List.of(
                cor(1L, "Vermelho"),
                cor(2L, "Azul")));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Vermelho"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(service.findById(1L)).thenReturn(cor(1L, "Vermelho"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Vermelho"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(service.findById(999L)).thenThrow(new NotFoundException("Cor nao encontrada"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarCorComStatus201() {
        when(service.create(any(Cor.class)))
                .thenReturn(cor(10L, "Verde"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Verde\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(10))
                .body("nome", equalTo("Verde"));
    }

    @Test
    void deveAtualizarCorComStatus204() {
        when(service.findById(1L)).thenReturn(cor(1L, "Vermelho"));
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Roxo\"}")
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    void deveRemoverCorComStatus204() {
        doNothing().when(service).delete(1L);

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

    private Cor cor(Long id, String nome) {
        Cor c = new Cor();
        c.setId(id);
        c.setNome(nome);
        return c;
    }
}
