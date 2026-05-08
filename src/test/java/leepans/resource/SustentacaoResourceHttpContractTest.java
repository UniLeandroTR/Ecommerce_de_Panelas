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

import leepans.model.Cor;
import leepans.model.Material;
import leepans.model.TipoSustentacao;
import leepans.service.ecommerce.SustentacaoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

import leepans.exception.ValidationException;
import leepans.model.Sustentacao;

@QuarkusTest
public class SustentacaoResourceHttpContractTest {

        private static final String BASE_URL = "/sustentacoes";

        @InjectMock
        SustentacaoService service;

        @BeforeEach
        void setUp() {
                reset(service);
        }

        @Test
        void deveListarSustentacoesComStatus200() {
                when(service.findAll()).thenReturn(List.of(
                                sustentacao(1L, 10.0, null, List.of(), null, null, TipoSustentacao.CABO),
                                sustentacao(2L, 15.0, null, List.of(), null, null, TipoSustentacao.CABO)));

                given()
                                .accept(ContentType.JSON)
                                .when()
                                .get(BASE_URL)
                                .then()
                                .statusCode(200)
                                .contentType(ContentType.JSON)
                                .body("size()", is(2))
                                .body("[0].id", equalTo(1))
                                .body("[0].peso", equalTo(10.0f));
        }

        @Test
        void deveBuscarPorIdComStatus200() {
                when(service.findById(1L))
                                .thenReturn(sustentacao(1L, 10.0, null, List.of(), null, null, TipoSustentacao.CABO));

                given()
                                .accept(ContentType.JSON)
                                .when()
                                .get(BASE_URL + "/1")
                                .then()
                                .statusCode(200)
                                .contentType(ContentType.JSON)
                                .body("id", equalTo(1))
                                .body("peso", equalTo(10.0f));
        }

        @Test
        void deveRetornar404QuandoBuscarPorIdInexistente() {
                when(service.findById(999L)).thenThrow(new NotFoundException("Sustentacao nao encontrada"));

                given()
                                .accept(ContentType.JSON)
                                .when()
                                .get(BASE_URL + "/999")
                                .then()
                                .statusCode(404);
        }

        @Test
        void deveCriarSustentacaoComStatus201() {
                when(service.create(any(Sustentacao.class)))
                                .thenReturn(sustentacao(10L, 5.5, null, List.of(), 10, 2, TipoSustentacao.CABO));

                given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body("{\"peso\":5.5,\"idsMateriais\":[1],\"idCor\":1,\"tamanhoEmCm\":10,\"quantidade\":2}")
                                .when()
                                .post(BASE_URL)
                                .then()
                                .statusCode(201)
                                .contentType(ContentType.JSON)
                                .body("id", equalTo(10))
                                .body("peso", equalTo(5.5f));
        }

        @Test
        void deveAtualizarSustentacaoComStatus204() {
                doNothing().when(service).update(any(Long.class), any());
                when(service.findById(1L)).thenReturn(sustentacao(1L, 6.6, null, List.of(), null, null, TipoSustentacao.CABO));

                given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body("{\"peso\":6.6,\"idsMateriais\":[1],\"idCor\":1}")
                                .when()
                                .put(BASE_URL + "/1")
                                .then()
                                .statusCode(204);
        }

        @Test
        void deveRemoverSustentacaoComStatus204() {
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
                                .body("{\"idsMateriais\":null,\"idCor\":null}")
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

                verify(service, never()).create(any(Sustentacao.class));
        }

        @Test
        void deveRetornar400QuandoJsonForMalformado() {
                given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body("{\"peso\":5.5,\"idsMateriais\":[1],\"idCor\":1")
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
                                .body("idsMateriais=1&idCor=1")
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
                doThrow(new ValidationException("Já existe uma sustentacao cadastrada", "peso"))
                                .when(service)
                                .update(any(Long.class), any());

                given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body("{\"peso\":6.6,\"idsMateriais\":[1],\"idCor\":1}")
                                .when()
                                .put(BASE_URL + "/1")
                                .then()
                                .statusCode(422)
                                .contentType(ContentType.JSON)
                                .body("type", equalTo("http://localhost:8080/errors/validation-error"))
                                .body("title", equalTo("Erro de validação"))
                                .body("status", equalTo(422))
                                .body("detail", containsString("Já existe uma sustentacao cadastrada"))
                                .body("instance", equalTo("/sustentacoes/1"))
                                .body("errors[0].field", equalTo("peso"))
                                .body("errors[0].message", containsString("Já existe uma sustentacao cadastrada"))
                                .body("timestamp", notNullValue());
        }

        private Sustentacao sustentacao(Long id, Double peso, Cor cor, List<Material> materiais, Integer tamanhoEmCm,
                        Integer quantidade, TipoSustentacao tipoSustentacao) {
                Sustentacao s = new Sustentacao();
                s.setId(id);
                s.setPeso(peso);
                s.setCor(cor);
                s.setMateriais(materiais);
                s.setTamanhoEmCm(tamanhoEmCm);
                s.setQuantidade(quantidade);
                s.setTipoSustentacao(tipoSustentacao);
                return s;
        }
}
