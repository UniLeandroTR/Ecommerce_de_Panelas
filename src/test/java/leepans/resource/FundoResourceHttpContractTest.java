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
import leepans.model.Fundo;
import leepans.model.Material;
import leepans.service.ecommerce.FundoService;

@QuarkusTest
class FundoResourceHttpContractTest {

    private static final String BASE_URL = "/fundos";

    @InjectMock
    FundoService fundoService;

    @BeforeEach
    void setUp() {
        reset(fundoService);
    }

    @Test
    void deveListarFundosComStatus200() {
        when(fundoService.findAll()).thenReturn(List.of(
            fundo(1L, 1.5, cor(1L, "Preta"), List.of(material(1L, "Inox")), 2.0, true),
            fundo(2L, 2.0, cor(2L, "Prata"), List.of(material(2L, "Alumínio")), 3.0, false)
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
            .body("[0].espessura", equalTo(2.0f))
            .body("[0].isAntiaderente", is(true));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(fundoService.findById(1L)).thenReturn(fundo(1L, 1.5, cor(1L, "Preta"), List.of(material(1L, "Inox")), 2.0, true));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("peso", equalTo(1.5f))
            .body("espessura", equalTo(2.0f))
            .body("isAntiaderente", is(true));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(fundoService.findById(999L)).thenThrow(new NotFoundException("Fundo nao encontrado"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/999")
        .then()
            .statusCode(404);
    }

    @Test
    void deveCriarFundoComStatus201() {
        when(fundoService.create(any()))
            .thenReturn(fundo(10L, 4.0, cor(1L, "Preta"), List.of(material(1L, "Inox")), 2.0, true));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":4.0,\"idsMateriais\":[1],\"idCor\":1,\"espessura\":2.0,\"isAntiaderente\":true}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", equalTo(10))
            .body("peso", equalTo(4.0f))
            .body("espessura", equalTo(2.0f))
            .body("isAntiaderente", is(true));
    }

    @Test
    void deveAtualizarFundoComStatus204() {
        doNothing().when(fundoService).update(any(Long.class), any());

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":5.0,\"idsMateriais\":[1],\"idCor\":1,\"espessura\":2.5,\"isAntiaderente\":false}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(204);
    }

    @Test
    void deveRemoverFundoComStatus204() {
        doNothing().when(fundoService).delete(1L);

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
            .body("{\"peso\":null,\"idsMateriais\":[],\"idCor\":null,\"espessura\":null,\"isAntiaderente\":null}")
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

        verify(fundoService, never()).create(any());
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":4.0,\"idsMateriais\":[1],\"idCor\":1,\"espessura\":2.0,\"isAntiaderente\":true")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(fundoService.findAll()).thenReturn(List.of());

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
            .body("peso=4.0&espessura=2.0")
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

        when(fundoService.findAll()).thenReturn(List.of());

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
        doThrow(new ValidationException("Já existe um fundo com estas especificações", "peso"))
            .when(fundoService)
            .update(any(Long.class), any());

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"peso\":4.0,\"idsMateriais\":[1],\"idCor\":1,\"espessura\":2.0,\"isAntiaderente\":true}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(422)
            .contentType(ContentType.JSON)
            .body("type", equalTo("http://localhost:8080/errors/validation-error"))
            .body("title", equalTo("Erro de validação"))
            .body("status", equalTo(422))
            .body("detail", containsString("Já existe um fundo com estas especificações"))
            .body("instance", equalTo("/fundos/1"))
            .body("errors[0].field", equalTo("peso"))
            .body("errors[0].message", containsString("Já existe um fundo com estas especificações"))
            .body("timestamp", notNullValue());
    }

    private Fundo fundo(Long id, Double peso, Cor cor, List<Material> materiais, Double espessura, Boolean isAntiaderente) {
        Fundo fundo = new Fundo();
        fundo.setId(id);
        fundo.setPeso(peso);
        fundo.setCor(cor);
        fundo.setMateriais(materiais);
        fundo.setEspessura(espessura);
        fundo.setIsAntiaderente(isAntiaderente);
        return fundo;
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