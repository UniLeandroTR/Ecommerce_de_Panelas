package leepans.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;
import leepans.exception.ValidationException;
import leepans.model.Cor;
import leepans.model.Material;
import leepans.model.Tampa;
import leepans.service.TampaService;

@QuarkusTest
class TampaResourceHttpContractTest {

    private static final String BASE_URL = "/tampas";

    @InjectMock
    TampaService tampaService;

    @BeforeEach
    void setUp() {
        reset(tampaService);
    }

    @Test
    void deveListarTampasComStatus200() {
        when(tampaService.findAll()).thenReturn(List.of(
            tampa(1L, 1.5, cor(1L, "Preta"), List.of(material(1L, "Vidro")), true),
            tampa(2L, 2.0, cor(2L, "Prata"), List.of(material(2L, "Alumínio")), false)
        ));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(2))
            .body("[0].id", equalTo(1))
            .body("[0].peso", equalTo(1.5f))
            .body("[0].isDePressao", is(true));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(tampaService.findById(1L)).thenReturn(tampa(1L, 1.5, cor(1L, "Preta"), List.of(material(1L, "Vidro")), true));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("peso", equalTo(1.5f))
            .body("isDePressao", is(true));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(tampaService.findById(999L)).thenThrow(new NotFoundException("Tampa nao encontrada"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/999")
        .then()
            .statusCode(404);
    }

    @Test
    void deveCriarTampaComStatus201() {
        when(tampaService.create(any()))
            .thenReturn(tampa(10L, 3.3, cor(1L, "Preta"), List.of(material(1L, "Vidro")), false));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":3.3,\"idsMateriais\":[1],\"idCor\":1,\"isDePressao\":false}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", equalTo(10))
            .body("peso", equalTo(3.3f))
            .body("isDePressao", is(false));
    }

    @Test
    void deveAtualizarTampaComStatus204() {
        doNothing().when(tampaService).update(any(Long.class), any());

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":4.4,\"idsMateriais\":[1],\"idCor\":1,\"isDePressao\":true}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(204);
    }

    @Test
    void deveRemoverTampaComStatus204() {
        doNothing().when(tampaService).delete(1L);

        given()
            .accept(ContentType.JSON)
        .when()
            .delete(BASE_URL + "/1")
        .then()
            .statusCode(204);
    }

    @Test
    void deveRetornar422QuandoPayloadForInvalido() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":null,\"idsMateriais\":[],\"idCor\":null,\"isDePressao\":null}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(422)
            .contentType(ContentType.JSON)
            .body("type", equalTo("http://localhost:8080/errors/validation-error"))
            .body("title", equalTo("Erro de validação"))
            .body("status", equalTo(422))
            .body("detail", equalTo("Um ou mais campos não passaram na validação."))
            .body("errors", hasSize(greaterThanOrEqualTo(1)));

        verify(tampaService, never()).create(any());
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":3.3,\"idsMateriais\":[1],\"idCor\":1,\"isDePressao\":false")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(tampaService.findAll()).thenReturn(List.of());

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);

        given()
            .accept(ContentType.XML)
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(406);

        given()
            .contentType(ContentType.TEXT)
            .accept(ContentType.JSON)
            .body("peso=3.3")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(415);
    }

    @Test
    void deveTratarPathParamOuQueryParamInvalidos() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/abc")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(404)));

        when(tampaService.findAll()).thenReturn(List.of());

        given()
            .accept(ContentType.JSON)
            .queryParam("pagina", "abc")
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    void deveMapearValidationExceptionCorretamente() {
        doThrow(new ValidationException("Já existe uma tampa cadastrada", "peso"))
            .when(tampaService)
            .update(any(Long.class), any());

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":3.3,\"idsMateriais\":[1],\"idCor\":1,\"isDePressao\":false}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(422)
            .contentType(ContentType.JSON)
            .body("type", equalTo("http://localhost:8080/errors/validation-error"))
            .body("title", equalTo("Erro de validação"))
            .body("status", equalTo(422))
            .body("detail", containsString("Já existe uma tampa cadastrada"))
            .body("instance", equalTo("/tampas/1"))
            .body("errors[0].field", equalTo("peso"))
            .body("errors[0].message", containsString("Já existe uma tampa cadastrada"))
            .body("timestamp", notNullValue());
    }

    private Tampa tampa(Long id, Double peso, Cor cor, List<Material> materiais, Boolean isDePressao) {
        Tampa t = new Tampa();
        t.setId(id);
        t.setPeso(peso);
        t.setCor(cor);
        t.setMateriais(materiais);
        t.setIsDePressao(isDePressao);
        return t;
    }

    private Cor cor(Long id, String nome) {
        Cor c = new Cor();
        c.setId(id);
        c.setNome(nome);
        return c;
    }

    private Material material(Long id, String nome) {
        Material m = new Material();
        m.setId(id);
        m.setNome(nome);
        return m;
    }
}