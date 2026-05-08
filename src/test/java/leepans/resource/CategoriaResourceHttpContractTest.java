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
import leepans.model.Categoria;
import leepans.service.ecommerce.CategoriaService;

@QuarkusTest
public class CategoriaResourceHttpContractTest {

    private static final String BASE_URL = "/categorias";

    @InjectMock
    CategoriaService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void deveListarCategoriasComStatus200() {
        when(service.findAll()).thenReturn(List.of(
            categoria(1L, "Panelas"),
            categoria(2L, "Utensilios")
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
            .body("[0].tipo", equalTo("Panelas"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(service.findById(1L)).thenReturn(categoria(1L, "Panelas"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("tipo", equalTo("Panelas"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(service.findById(999L)).thenThrow(new NotFoundException("Categoria nao encontrada"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/999")
        .then()
            .statusCode(404);
    }

    @Test
    void deveCriarCategoriaComStatus201() {
        when(service.create(any(Categoria.class)))
            .thenReturn(categoria(10L, "Panelas"));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"tipo\":\"Panelas\"}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", equalTo(10))
            .body("tipo", equalTo("Panelas"));
    }

    @Test
    void deveAtualizarCategoriaComStatus204() {
        doNothing().when(service).update(any(Long.class), any());

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"tipo\":\"Nova Categoria\"}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(204);
    }

    @Test
    void deveRemoverCategoriaComStatus204() {
        doNothing().when(service).delete(1L);

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
            .body("{\"tipo\":\"\"}")
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

        verify(service, never()).create(any(Categoria.class));
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"tipo\":\"Panelas\"")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(service.findAll()).thenReturn(List.of());

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
            .body("tipo=Panelas")
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

        when(service.findAll()).thenReturn(List.of());

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
        doThrow(new ValidationException("Já existe uma categoria cadastrada com o tipo: Panelas", "tipo"))
            .when(service)
            .update(any(Long.class), any());

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"tipo\":\"Panelas\"}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(422)
            .contentType(ContentType.JSON)
            .body("type", equalTo("http://localhost:8080/errors/validation-error"))
            .body("title", equalTo("Erro de validação"))
            .body("status", equalTo(422))
            .body("detail", containsString("Já existe uma categoria cadastrada com o tipo: Panelas"))
            .body("instance", equalTo("/categorias/1"))
            .body("errors[0].field", equalTo("tipo"))
            .body("errors[0].message", containsString("Já existe uma categoria cadastrada com o tipo: Panelas"))
            .body("timestamp", notNullValue());
    }

    private Categoria categoria(Long id, String tipo) {
        Categoria c = new Categoria();
        c.setId(id);
        c.setTipo(tipo);
        return c;
    }
}

