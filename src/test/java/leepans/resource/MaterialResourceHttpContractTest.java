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
import leepans.model.Material;
import leepans.service.MaterialService;

@QuarkusTest
class MaterialResourceHttpContractTest {

    private static final String BASE_URL = "/materiais";

    @InjectMock
    MaterialService materialService;

    @BeforeEach
    void setUp() {
        reset(materialService);
    }

    @Test
    void deveListarMateriaisComStatus200() {
        when(materialService.findAll()).thenReturn(List.of(
                material(1L, "Aco", List.of("Resistente", "Brilhante")),
                material(2L, "Aluminio", List.of("Leve"))));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Aco"))
                .body("[0].qualidades", hasSize(2))
                .body("[0].qualidades[0]", equalTo("Resistente"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(materialService.findById(1L)).thenReturn(material(1L, "Aco", List.of("Resistente", "Brilhante")));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Aco"))
                .body("qualidades", hasSize(2));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(materialService.findById(999L)).thenThrow(new NotFoundException("Material nao encontrado"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarMaterialComStatus201() {
        when(materialService.create(any()))
                .thenReturn(material(10L, "Aco", List.of("Resistente")));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Aco\",\"qualidades\":[\"Resistente\"]}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(10))
                .body("nome", equalTo("Aco"))
                .body("qualidades[0]", equalTo("Resistente"));
    }

    @Test
    void deveAtualizarMaterialComStatus204() {
        when(materialService.findById(1L)).thenReturn(material(1L, "Aco", List.of("Resistente")));
        doNothing().when(materialService).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Aco Atualizado\",\"qualidades\":[\"Resistente\"]}")
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    void deveRemoverMaterialComStatus204() {
        doNothing().when(materialService).delete(1L);

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
                .body("{\"nome\":\"\"}")
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

        verify(materialService, never()).create(any());
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Aco\",\"qualidades\":[\"Resistente\"")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(materialService.findAll()).thenReturn(List.of());

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
                .body("nome=Aco")
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

        when(materialService.findAll()).thenReturn(List.of());

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
        when(materialService.findById(1L)).thenReturn(material(1L, "Aco", List.of("Resistente")));
        doThrow(new ValidationException("Já existe um material cadastrado com o nome: Aco", "nome"))
                .when(materialService)
                .update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Aco\",\"qualidades\":[\"Resistente\"]}")
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(422)
                .contentType(ContentType.JSON)
                .body("type", equalTo("http://localhost:8080/errors/validation-error"))
                .body("title", equalTo("Erro de validação"))
                .body("status", equalTo(422))
                .body("detail", containsString("Já existe um material cadastrado com o nome: Aco"))
                .body("instance", equalTo("/materiais/1"))
                .body("errors[0].field", equalTo("nome"))
                .body("errors[0].message", containsString("Já existe um material cadastrado com o nome: Aco"))
                .body("timestamp", notNullValue());
    }

    private Material material(Long id, String nome, List<String> qualidades) {
        Material m = new Material();
        m.setId(id);
        m.setNome(nome);
        m.setQualidades(qualidades);
        return m;
    }
}