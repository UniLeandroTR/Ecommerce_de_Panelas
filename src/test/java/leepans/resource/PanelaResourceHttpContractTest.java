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

import leepans.model.*;
import leepans.service.ecommerce.PanelaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

import leepans.exception.ValidationException;

@QuarkusTest
class PanelaResourceHttpContractTest {

    private static final String BASE_URL = "/panelas";

    @InjectMock
    PanelaService panelaService;

    @BeforeEach
    void setUp() {
        reset(panelaService);
    }

    @Test
    void deveListarPanelasComStatus200() {
        when(panelaService.findAll()).thenReturn(List.of(
                panela(1L, "Modelo A", 100L, 1.5, 2.0, "Panela A", true, Tamanho.PEQUENA, colecao(1L, "Colecao A"),
                        categoria(1L, "Cat A"), fornecedor(1L, "Forn A"), tampa(1L, 0.5, true), sustentacao(1L, 2),
                        fundo(1L, 0.5, 2.0, true)),
                panela(2L, "Modelo B", 200L, 2.5, 4.0, "Panela B", false, Tamanho.MEDIA, colecao(2L, "Colecao B"),
                        categoria(2L, "Cat B"), fornecedor(2L, "Forn B"), tampa(2L, 0.6, false), sustentacao(2L, 2),
                        fundo(2L, 0.6, 2.5, false))));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].modelo", equalTo("Modelo A"))
                .body("[0].tamanho.id", equalTo(1))
                .body("[0].tamanho.nome", equalTo("Pequena"))
                .body("[0].colecao.id", equalTo(1))
                .body("[0].categoria.id", equalTo(1))
                .body("[0].fornecedor.id", equalTo(1))
                .body("[0].tampa.id", equalTo(1))
                .body("[0].fundo.id", equalTo(1))
                .body("[0].sustentacao.id", equalTo(1));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(panelaService.findById(1L)).thenReturn(panela(1L, "Modelo A", 100L, 1.5, 2.0, "Panela A", true,
                Tamanho.PEQUENA, colecao(1L, "Colecao A"), categoria(1L, "Cat A"), fornecedor(1L, "Forn A"),
                tampa(1L, 0.5, true), sustentacao(1L, 2), fundo(1L, 0.5, 2.0, true)));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("modelo", equalTo("Modelo A"))
                .body("preco", equalTo(100))
                .body("tamanho.id", equalTo(1));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(panelaService.findById(999L)).thenThrow(new NotFoundException("Panela nao encontrada"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarPanelaComStatus201() {
        when(panelaService.create(any()))
                .thenReturn(panela(10L, "Modelo X", 150L, 2.0, 3.0, "Panela X", true, Tamanho.GRANDE,
                        colecao(1L, "Colecao X"), categoria(1L, "Cat X"), fornecedor(1L, "Forn X"),
                        tampa(1L, 0.5, true), sustentacao(1L, 2), fundo(1L, 0.5, 2.0, true)));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"modelo\":\"Modelo X\",\"preco\":150,\"peso\":2.0,\"capacidadeLitros\":3.0,\"descricaco\":\"Panela X\",\"isInducao\":true,\"idTamanho\":3,\"idColecao\":1,\"idCategoria\":1,\"idFornecedor\":1,\"idTampa\":1,\"idSustentacao\":1,\"idFundo\":1}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(10))
                .body("modelo", equalTo("Modelo X"))
                .body("tamanho.id", equalTo(3));
    }

    @Test
    void deveAtualizarPanelaComStatus204() {
        doNothing().when(panelaService).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"modelo\":\"Modelo Novo\",\"preco\":200,\"peso\":2.5,\"capacidadeLitros\":4.0,\"descricaco\":\"Panela Nova\",\"isInducao\":false,\"idTamanho\":2,\"idColecao\":1,\"idCategoria\":1,\"idFornecedor\":1,\"idTampa\":1,\"idSustentacao\":1,\"idFundo\":1}")
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    void deveRemoverPanelaComStatus204() {
        doNothing().when(panelaService).delete(1L);

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
                .body("{\"modelo\":\"\"}")
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

        verify(panelaService, never()).create(any());
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"modelo\":\"Modelo X\"")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(panelaService.findAll()).thenReturn(List.of());

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
                .body("modelo=Teste")
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

        when(panelaService.findAll()).thenReturn(List.of());

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
        doThrow(new ValidationException("Já existe uma panela cadastrada com esse modelo", "modelo"))
                .when(panelaService)
                .update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"modelo\":\"Modelo Novo\",\"preco\":200,\"peso\":2.5,\"capacidadeLitros\":4.0,\"descricaco\":\"Panela Nova\",\"isInducao\":false,\"idTamanho\":2,\"idColecao\":1,\"idCategoria\":1,\"idFornecedor\":1,\"idTampa\":1,\"idSustentacao\":1,\"idFundo\":1}")
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(422)
                .contentType(ContentType.JSON)
                .body("type", equalTo("http://localhost:8080/errors/validation-error"))
                .body("title", equalTo("Erro de validação"))
                .body("status", equalTo(422))
                .body("detail", containsString("Já existe uma panela cadastrada com esse modelo"))
                .body("instance", equalTo("/panelas/1"))
                .body("errors[0].field", equalTo("modelo"))
                .body("errors[0].message", containsString("Já existe uma panela cadastrada com esse modelo"))
                .body("timestamp", notNullValue());
    }

    private Panela panela(Long id, String modelo, Long preco, Double peso, Double capacidadeLitros,
            String descricao, Boolean isInducao, Tamanho tamanho, Colecao colecao,
            Categoria categoria, Fornecedor fornecedor, Tampa tampa,
            Sustentacao sustentacao, Fundo fundo) {
        Panela p = new Panela();
        p.setId(id);
        p.setModelo(modelo);
        p.setPreco(preco);
        p.setPeso(peso);
        p.setCapacidadeLitros(capacidadeLitros);
        p.setDescricaco(descricao);
        p.setIsInducao(isInducao);
        p.setTamanho(tamanho);
        p.setColecao(colecao);
        p.setCategoria(categoria);
        p.setFornecedor(fornecedor);
        p.setTampa(tampa);
        p.setFundo(fundo);
        p.setSustentacao(sustentacao);
        return p;
    }

    private Colecao colecao(Long id, String nome) {
        Colecao c = new Colecao();
        c.setId(id);
        c.setNome(nome);
        return c;
    }

    private Categoria categoria(Long id, String nome) {
        Categoria c = new Categoria();
        c.setId(id);
        c.setTipo(nome);
        return c;
    }

    private Fornecedor fornecedor(Long id, String nome) {
        Fornecedor f = new Fornecedor();
        f.setId(id);
        f.setNome(nome);
        return f;
    }

    private Tampa tampa(Long id, Double peso, Boolean isDePressao) {
        Tampa t = new Tampa();
        t.setId(id);
        t.setPeso(peso);
        t.setIsDePressao(isDePressao);
        return t;
    }

    private Fundo fundo(Long id, Double peso, Double espessura, Boolean isAntiaderente) {
        Fundo f = new Fundo();
        f.setId(id);
        f.setPeso(peso);
        f.setEspessura(espessura);
        f.setIsAntiaderente(isAntiaderente);
        return f;
    }

    private Sustentacao sustentacao(Long id, Integer quantidade) {
        Sustentacao s = new Sustentacao();
        s.setId(id);
        s.setQuantidade(quantidade);
        return s;
    }
}